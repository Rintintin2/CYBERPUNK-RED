package com.example.data.repository

import com.example.core.rules.DiceEngine
import com.example.core.rules.EconomyEngine
import com.example.core.rules.HustleRollResult
import com.example.core.rules.NetArchDifficulty
import com.example.core.rules.NetrunningEngine
import com.example.core.rules.OfficialEconomyAndProgression
import com.example.core.rules.OfficialNetArchitectures
import com.example.core.rules.OfficialNpcSpec
import com.example.core.rules.PriceCategory
import com.example.core.rules.PurchaseOutcome
import com.example.core.rules.RedRole
import com.example.core.rules.RezDamageResult
import com.example.core.rules.SkillCheckEngine
import com.example.data.local.dao.CampaignDao
import com.example.data.local.dao.SystemsDao
import com.example.data.local.entity.CampaignEntity
import com.example.data.local.entity.CombatParticipantEntity
import com.example.data.local.entity.InventoryItemEntity
import com.example.data.local.entity.InventoryItemType
import com.example.data.local.entity.JournalEntryEntity
import com.example.data.local.entity.JournalEntryType
import com.example.data.local.entity.MissionEntity
import com.example.data.local.entity.MissionStatus
import com.example.data.local.entity.NetNodeEntity
import com.example.data.local.entity.NetNodeType
import com.example.data.local.entity.NpcFactionEntity
import kotlinx.coroutines.flow.Flow

class SessionRepository(
    private val systemsDao: SystemsDao,
    private val campaignDao: CampaignDao,
    private val skillCheckEngine: SkillCheckEngine,
    private val netrunningEngine: NetrunningEngine
) {
    fun observeInventory(campaignId: Long): Flow<List<InventoryItemEntity>> =
        systemsDao.observeInventory(campaignId)

    fun observeNetNodes(campaignId: Long): Flow<List<NetNodeEntity>> =
        systemsDao.observeNetNodes(campaignId)

    fun observeNpcsAndFactions(campaignId: Long): Flow<List<NpcFactionEntity>> =
        systemsDao.observeNpcsAndFactions(campaignId)

    fun observeMissions(campaignId: Long): Flow<List<MissionEntity>> =
        systemsDao.observeMissions(campaignId)

    fun observeCombat(campaignId: Long): Flow<List<CombatParticipantEntity>> =
        systemsDao.observeCombatParticipants(campaignId)

    fun observeJournal(campaignId: Long): Flow<List<JournalEntryEntity>> =
        systemsDao.observeJournal(campaignId)

    suspend fun addJournalEntry(
        campaign: CampaignEntity,
        entryType: JournalEntryType,
        speakerLabel: String,
        bodyText: String,
        deterministicDetail: String? = null
    ) {
        val timestamp = "Dia ${campaign.inGameDay} • ${campaign.inGameHour.toString().padStart(2, '0')}:00"
        systemsDao.insertJournalEntry(
            JournalEntryEntity(
                campaignId = campaign.id,
                entryType = entryType,
                speakerLabel = speakerLabel,
                bodyText = bodyText,
                deterministicDetail = deterministicDetail,
                inGameTimestamp = timestamp
            )
        )
    }

    suspend fun purchaseOrAddItem(
        campaign: CampaignEntity,
        name: String,
        itemType: InventoryItemType,
        priceCategory: PriceCategory,
        costEb: Int,
        quantity: Int,
        damageDice: Int,
        stoppingPower: Int,
        payWithEurodollars: Boolean,
        notes: String
    ): PurchaseOutcome {
        val totalCost = (costEb * quantity.coerceAtLeast(1)).coerceAtLeast(0)
        if (payWithEurodollars) {
            when (val outcome = EconomyEngine.attemptPurchase(campaign.eurodollars, totalCost)) {
                is PurchaseOutcome.InsufficientFunds -> return outcome
                is PurchaseOutcome.Approved -> {
                    campaignDao.updateCampaign(
                        campaign.copy(
                            eurodollars = outcome.remainingEurodollars,
                            updatedAt = System.currentTimeMillis()
                        )
                    )
                    systemsDao.insertInventoryItem(
                        InventoryItemEntity(
                            campaignId = campaign.id,
                            name = name.trim(),
                            itemType = itemType,
                            priceCategory = priceCategory,
                            costEb = costEb,
                            quantity = quantity.coerceAtLeast(1),
                            damageDice = damageDice,
                            stoppingPower = stoppingPower,
                            notes = notes.trim()
                        )
                    )
                    addJournalEntry(
                        campaign = campaign,
                        entryType = JournalEntryType.SYSTEM_EVENT,
                        speakerLabel = "Mercado Noturno",
                        bodyText = "Comprado ${quantity}x ${name.trim()} por $totalCost eb. Saldo restante: ${outcome.remainingEurodollars} eb."
                    )
                    return outcome
                }
            }
        } else {
            systemsDao.insertInventoryItem(
                InventoryItemEntity(
                    campaignId = campaign.id,
                    name = name.trim(),
                    itemType = itemType,
                    priceCategory = priceCategory,
                    costEb = costEb,
                    quantity = quantity.coerceAtLeast(1),
                    damageDice = damageDice,
                    stoppingPower = stoppingPower,
                    notes = notes.trim()
                )
            )
            return PurchaseOutcome.Approved(campaign.eurodollars)
        }
    }

    suspend fun toggleItemEquipped(item: InventoryItemEntity) {
        systemsDao.updateInventoryItem(item.copy(isEquipped = !item.isEquipped))
    }

    suspend fun adjustItemQuantity(item: InventoryItemEntity, delta: Int) {
        val next = item.quantity + delta
        if (next <= 0) {
            systemsDao.deleteInventoryItemById(item.id)
        } else {
            systemsDao.updateInventoryItem(item.copy(quantity = next))
        }
    }

    suspend fun addNetNode(
        campaignId: Long,
        architectureName: String,
        floorNumber: Int,
        nodeType: NetNodeType,
        title: String,
        dvOrRez: Int
    ) {
        val isIce = nodeType == NetNodeType.BLACK_ICE || nodeType == NetNodeType.DEMON
        systemsDao.insertNetNode(
            NetNodeEntity(
                campaignId = campaignId,
                architectureName = architectureName.trim(),
                floorNumber = floorNumber.coerceAtLeast(1),
                nodeType = nodeType,
                title = title.trim(),
                difficultyValue = if (isIce) 0 else dvOrRez.coerceAtLeast(1),
                currentRez = if (isIce) dvOrRez.coerceAtLeast(1) else 0,
                maxRez = if (isIce) dvOrRez.coerceAtLeast(1) else 0
            )
        )
    }

    suspend fun damageNetIce(node: NetNodeEntity, damageDice: Int): RezDamageResult {
        val result = netrunningEngine.applyRezDamage(damageDice, node.currentRez)
        systemsDao.updateNetNode(
            node.copy(
                currentRez = result.rezAfter,
                isCleared = result.derezzed
            )
        )
        return result
    }

    suspend fun toggleNetNodeCleared(node: NetNodeEntity) {
        systemsDao.updateNetNode(node.copy(isCleared = !node.isCleared))
    }

    suspend fun deleteNetNode(nodeId: Long) {
        systemsDao.deleteNetNodeById(nodeId)
    }

    suspend fun generateNetArchitecture(
        campaignId: Long,
        architectureName: String,
        difficulty: NetArchDifficulty,
        diceEngine: DiceEngine = DiceEngine()
    ): Int {
        val floors = OfficialNetArchitectures.generateArchitecture(diceEngine, difficulty)
        floors.forEach { f ->
            addNetNode(
                campaignId = campaignId,
                architectureName = architectureName,
                floorNumber = f.floorNumber,
                nodeType = f.nodeType,
                title = f.title,
                dvOrRez = f.dvOrRez
            )
        }
        return floors.size
    }

    suspend fun addNpcOrFaction(
        campaignId: Long,
        name: String,
        isFaction: Boolean,
        affiliation: String,
        attitude: String,
        notes: String
    ) {
        systemsDao.insertNpcFaction(
            NpcFactionEntity(
                campaignId = campaignId,
                name = name.trim(),
                isFaction = isFaction,
                affiliation = affiliation.trim().ifEmpty { "Independente" },
                attitude = attitude.trim().ifEmpty { "Neutro" },
                notes = notes.trim()
            )
        )
    }

    suspend fun deleteNpcFaction(id: Long) {
        systemsDao.deleteNpcFactionById(id)
    }

    suspend fun addMission(
        campaignId: Long,
        title: String,
        fixerName: String,
        rewardEb: Int,
        objectivesSummary: String
    ) {
        systemsDao.insertMission(
            MissionEntity(
                campaignId = campaignId,
                title = title.trim(),
                fixerName = fixerName.trim(),
                rewardEb = rewardEb.coerceAtLeast(0),
                objectivesSummary = objectivesSummary.trim()
            )
        )
    }

    suspend fun updateMissionStatus(
        campaign: CampaignEntity,
        mission: MissionEntity,
        newStatus: MissionStatus
    ) {
        systemsDao.updateMission(mission.copy(status = newStatus))
        if (newStatus == MissionStatus.COMPLETED && mission.status != MissionStatus.COMPLETED && mission.rewardEb > 0) {
            campaignDao.updateCampaign(
                campaign.copy(
                    eurodollars = campaign.eurodollars + mission.rewardEb,
                    updatedAt = System.currentTimeMillis()
                )
            )
            addJournalEntry(
                campaign = campaign,
                entryType = JournalEntryType.SYSTEM_EVENT,
                speakerLabel = "Contrato Concluído",
                bodyText = "Missão \"${mission.title}\" concluída. Pagamento recebido: +${mission.rewardEb} eb."
            )
        }
    }

    suspend fun deleteMission(id: Long) {
        systemsDao.deleteMissionById(id)
    }

    suspend fun addCombatParticipantWithInitiative(
        campaignId: Long,
        name: String,
        isPlayer: Boolean,
        reflexes: Int,
        maxHp: Int,
        armorSp: Int,
        existingParticipants: List<CombatParticipantEntity>
    ): Int {
        val init = skillCheckEngine.rollInitiative(reflexes)
        val shouldActivate = existingParticipants.isEmpty()
        systemsDao.insertCombatParticipant(
            CombatParticipantEntity(
                campaignId = campaignId,
                name = name.trim(),
                isPlayer = isPlayer,
                reflexes = reflexes,
                initiativeTotal = init.total,
                currentHp = maxHp.coerceAtLeast(1),
                maxHp = maxHp.coerceAtLeast(1),
                armorSp = armorSp.coerceAtLeast(0),
                isActiveTurn = shouldActivate,
                roundNumber = existingParticipants.firstOrNull()?.roundNumber ?: 1
            )
        )
        return init.total
    }

    suspend fun advanceCombatTurn(participants: List<CombatParticipantEntity>) {
        if (participants.isEmpty()) return
        val sorted = participants.sortedWith(
            compareByDescending<CombatParticipantEntity> { it.initiativeTotal }
                .thenByDescending { it.reflexes }
        )
        val currentIdx = sorted.indexOfFirst { it.isActiveTurn }
        val nextIdx = if (currentIdx == -1 || currentIdx + 1 >= sorted.size) 0 else currentIdx + 1
        val nextRound = if (currentIdx != -1 && nextIdx == 0) sorted.first().roundNumber + 1 else sorted.first().roundNumber
        val updated = sorted.mapIndexed { idx, p ->
            p.copy(
                isActiveTurn = idx == nextIdx,
                roundNumber = nextRound
            )
        }
        systemsDao.updateCombatParticipants(updated)
    }

    suspend fun updateCombatParticipant(participant: CombatParticipantEntity) {
        systemsDao.updateCombatParticipant(participant)
    }

    suspend fun removeCombatParticipant(id: Long) {
        systemsDao.deleteCombatParticipantById(id)
    }

    suspend fun clearCombat(campaignId: Long) {
        systemsDao.clearCombatForCampaign(campaignId)
    }

    suspend fun runRoleHustle(
        campaign: CampaignEntity,
        role: RedRole,
        roleRank: Int,
        advanceSevenDays: Boolean,
        diceEngine: DiceEngine = DiceEngine()
    ): HustleRollResult {
        val result = OfficialEconomyAndProgression.rollHustle(role, roleRank, diceEngine)
        val newDay = if (advanceSevenDays) campaign.inGameDay + 7 else campaign.inGameDay
        val newRentDays = if (advanceSevenDays) (campaign.rentDueInDays - 7).coerceAtLeast(0) else campaign.rentDueInDays
        val updatedCampaign = campaign.copy(
            inGameDay = newDay,
            rentDueInDays = newRentDays,
            eurodollars = campaign.eurodollars + result.earnedEb,
            updatedAt = System.currentTimeMillis()
        )
        campaignDao.updateCampaign(updatedCampaign)
        addJournalEntry(
            campaign = updatedCampaign,
            entryType = JournalEntryType.DETERMINISTIC_ROLL,
            speakerLabel = "Hustle • ${role.displayNamePt} (Rank ${result.roleRank})",
            bodyText = "${result.activityPt} Ganhou +${result.earnedEb} eb" +
                if (advanceSevenDays) " (7 dias avançados)." else ".",
            deterministicDetail = "1d6(${result.roll1d6}) • Saldo atual: ${updatedCampaign.eurodollars} eb"
        )
        return result
    }

    suspend fun payLifestyleOrHousing(
        campaign: CampaignEntity,
        description: String,
        costEb: Int,
        newLifestyleLabel: String? = null,
        resetRentCycle: Boolean = false
    ): PurchaseOutcome {
        return when (val outcome = EconomyEngine.attemptPurchase(campaign.eurodollars, costEb.coerceAtLeast(0))) {
            is PurchaseOutcome.InsufficientFunds -> outcome
            is PurchaseOutcome.Approved -> {
                val updated = campaign.copy(
                    eurodollars = outcome.remainingEurodollars,
                    lifestyleLabel = newLifestyleLabel ?: campaign.lifestyleLabel,
                    rentDueInDays = if (resetRentCycle) 30 else campaign.rentDueInDays,
                    updatedAt = System.currentTimeMillis()
                )
                campaignDao.updateCampaign(updated)
                addJournalEntry(
                    campaign = updated,
                    entryType = JournalEntryType.SYSTEM_EVENT,
                    speakerLabel = "Economia & Estilo",
                    bodyText = "Pago \"$description\" por $costEb eb. Saldo restante: ${outcome.remainingEurodollars} eb."
                )
                outcome
            }
        }
    }

    suspend fun spawnOfficialNpcToCombat(
        campaignId: Long,
        npc: OfficialNpcSpec,
        count: Int,
        existingParticipants: List<CombatParticipantEntity>
    ): List<Int> {
        val safeCount = count.coerceIn(1, 10)
        val rolledInits = mutableListOf<Int>()
        var currentList = existingParticipants
        for (idx in 1..safeCount) {
            val label = if (safeCount > 1) "${npc.namePt} #$idx" else npc.namePt
            val init = addCombatParticipantWithInitiative(
                campaignId = campaignId,
                name = label,
                isPlayer = false,
                reflexes = npc.reflexes,
                maxHp = npc.hp,
                armorSp = npc.bodyArmorSp,
                existingParticipants = currentList
            )
            rolledInits.add(init)
            if (currentList.isEmpty()) {
                currentList = listOf(
                    CombatParticipantEntity(
                        campaignId = campaignId,
                        name = label,
                        isPlayer = false,
                        reflexes = npc.reflexes,
                        initiativeTotal = init,
                        currentHp = npc.hp,
                        maxHp = npc.hp,
                        armorSp = npc.bodyArmorSp,
                        isActiveTurn = true,
                        roundNumber = 1
                    )
                )
            }
        }
        return rolledInits
    }

    suspend fun setCurrentDistrict(campaign: CampaignEntity, districtCode: String) {
        val district = com.example.core.rules.OfficialNightCityAtlasCatalog.findDistrictByCode(districtCode) ?: return
        val updated = campaign.copy(
            currentDistrictCode = district.code,
            updatedAt = System.currentTimeMillis()
        )
        campaignDao.updateCampaign(updated)
        addJournalEntry(
            campaign = updated,
            entryType = JournalEntryType.SYSTEM_EVENT,
            speakerLabel = "Atlas de Night City",
            bodyText = "Deslocamento para ${district.code} — ${district.namePt} (${district.zone.labelPt}). Segurança: ${district.securityProvider}."
        )
    }

    suspend fun updateCampaignFlashOfLuck(
        campaign: CampaignEntity,
        beatUsed: Boolean,
        missionUses: Int
    ): CampaignEntity {
        val updated = campaign.copy(
            flashOfLuckBeatUsed = beatUsed,
            flashOfLuckMissionUses = missionUses.coerceIn(0, 3),
            updatedAt = System.currentTimeMillis()
        )
        campaignDao.updateCampaign(updated)
        return updated
    }

    suspend fun deployTheIncidentAdventure(campaign: CampaignEntity) {
        val summary = com.example.core.rules.OfficialDangerGalGearAndLore.theIncidentSummary
        addMission(
            campaignId = campaign.id,
            title = "The Incident (Danger Gal Dossier)",
            fixerName = "Mouse (Puma Squad)",
            rewardEb = 1000,
            objectivesSummary = summary
        )
        val incidentNpcs = listOf(
            Triple("Mouse (Puma Squad)", "Danger Gal", "Aliado/Contratante • Forlorn Hope (I3). Paga 500eb adiantado + 500eb final + Card 1.000eb."),
            Triple("Benny & Sra. Suzuki", "Old Japantown (H4)", "Neutro • Donos da Bodega da Sra. Suzuki (Social DV13 ou Bodega NET)."),
            Triple("Pa Saddlefoot & Sweetbriar", "The Shroomers", "Neutro • Guias dos túneis sob Old Japantown / Pequena Europa."),
            Triple("Darius (Arasaka)", "Arasaka Black Ops", "Hostil • Netrunner 4 com Cortex Bomb oculta nos túneis."),
            Triple("Nymph (Arasaka)", "Arasaka Black Ops", "Hostil • Assassina Solo 4 protegendo o cartão codificado Arasaka.")
        )
        incidentNpcs.forEach { (name, aff, notes) ->
            addNpcOrFaction(
                campaignId = campaign.id,
                name = name,
                isFaction = false,
                affiliation = aff,
                attitude = if (notes.startsWith("Hostil")) "Hostil" else if (notes.startsWith("Aliado")) "Aliado" else "Neutro",
                notes = notes
            )
        }
        val bodegaNet = com.example.core.rules.OfficialDlcNpcsAndNets.dgdNetArchitectures.first()
        bodegaNet.floors.forEachIndexed { idx, floor ->
            addNetNode(
                campaignId = campaign.id,
                architectureName = bodegaNet.namePt,
                floorNumber = idx + 1,
                nodeType = floor.nodeType,
                title = "[${floor.floorLabel}] ${floor.title}",
                dvOrRez = floor.dvOrRez
            )
        }
        addJournalEntry(
            campaign = campaign,
            entryType = JournalEntryType.SYSTEM_EVENT,
            speakerLabel = "Gig: The Incident",
            bodyText = "Aventura oficial 'The Incident' (10 Beats) implantada com NPCs-chave e Bodega NET (5 andares)."
        )
    }

    suspend fun importDgdFaction(campaign: CampaignEntity, factionId: String): Int {
        val faction = com.example.core.rules.OfficialDangerGalGearAndLore.dgdFactions.firstOrNull { it.id == factionId }
            ?: return 0
        addNpcOrFaction(
            campaignId = campaign.id,
            name = faction.namePt,
            isFaction = true,
            affiliation = faction.turfPt,
            attitude = faction.attitudePt,
            notes = "${faction.threatTierPt} — ${faction.overviewPt}"
        )
        val npcs = com.example.core.rules.OfficialDangerGalNpcs.byFaction(factionId)
        npcs.forEach { entry ->
            addNpcOrFaction(
                campaignId = campaign.id,
                name = "${entry.npc.namePt} (${entry.npc.tier.labelPt})",
                isFaction = false,
                affiliation = faction.namePt,
                attitude = faction.attitudePt,
                notes = "${entry.npc.armorLabelPt} • ${entry.npc.hp} PV • ${entry.npc.keySkillBasesPt}"
            )
        }
        addJournalEntry(
            campaign = campaign,
            entryType = JournalEntryType.SYSTEM_EVENT,
            speakerLabel = "Danger Gal Dossier",
            bodyText = "Facção \"${faction.namePt}\" + ${npcs.size} NPCs importados para a campanha."
        )
        return npcs.size
    }
}
