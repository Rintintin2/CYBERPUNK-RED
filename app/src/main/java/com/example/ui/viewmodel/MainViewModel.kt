package com.example.ui.viewmodel

import android.net.Uri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.core.ai.AiProviderMode
import com.example.core.ai.GameMasterGateway
import com.example.core.ai.GmContextSnapshot
import com.example.core.rules.AgentAccessMode
import com.example.core.rules.AgentHackResult
import com.example.core.rules.AgentQualityTier
import com.example.core.rules.BrainwasherResult
import com.example.core.rules.CharacterCalculator
import com.example.core.rules.CharacterStats
import com.example.core.rules.CriticalMarker
import com.example.core.rules.CyberwareSlotSummary
import com.example.core.rules.CyberwareValidator
import com.example.core.rules.DamageApplicationResult
import com.example.core.rules.DeathSaveResult
import com.example.core.rules.DatepathRollResult
import com.example.core.rules.DerivedCharacterMetrics
import com.example.core.rules.DiceEngine
import com.example.core.rules.EconomyEngine
import com.example.core.rules.EncounterRollResult
import com.example.core.rules.EncounterTimeOfDay
import com.example.core.rules.FlashOfLuckTier
import com.example.core.rules.GeneratedNightMarket
import com.example.core.rules.HustleRollResult
import com.example.core.rules.HusToolResult
import com.example.core.rules.InterfaceCheckResult
import com.example.core.rules.NetArchDifficulty
import com.example.core.rules.NetrunningEngine
import com.example.core.rules.NightMarketOffer
import com.example.core.rules.OfficialDlcSoloSystems
import com.example.core.rules.OfficialEncountersAndLoreCatalog
import com.example.core.rules.OfficialNightCityAtlasCatalog
import com.example.core.rules.OfficialNpcSpec
import com.example.core.rules.OfficialSinglePlayerPlusCatalog
import com.example.core.rules.OfficialSkillCatalog
import com.example.core.rules.OfficialStreetGenerators
import com.example.core.rules.OfficialTalesAndHookBookCatalog
import com.example.core.rules.PlotHookSpec
import com.example.core.rules.PremadeNetArchSpec
import com.example.core.rules.PriceCategory
import com.example.core.rules.PurchaseOutcome
import com.example.core.rules.RedRole
import com.example.core.rules.RedStat
import com.example.core.rules.SalvageAttemptResult
import com.example.core.rules.SalvageCategory
import com.example.core.rules.SalvageWorthTier
import com.example.core.rules.SkillCheckEngine
import com.example.core.rules.SkillCheckRequest
import com.example.core.rules.SkillCheckResult
import com.example.core.rules.SoloNpcPersonalityBundle
import com.example.core.rules.SoloOracleYesNoResult
import com.example.core.rules.SoloPlusRollResult
import com.example.core.rules.StealthNetContestResult
import com.example.core.rules.StreetratPregenSpec
import com.example.core.rules.TherapyOutcome
import com.example.core.rules.TherapyType
import com.example.core.rules.WeatherRollResult
import com.example.core.rules.WeatherSeason
import com.example.data.repository.CyberwareInstallOutcome
import com.example.data.local.entity.CampaignEntity
import com.example.data.local.entity.CampaignSaveEntity
import com.example.data.local.entity.CharacterEntity
import com.example.data.local.entity.CharacterSkillEntity
import com.example.data.local.entity.CombatParticipantEntity
import com.example.data.local.entity.CyberwareEntity
import com.example.data.local.entity.InventoryItemEntity
import com.example.data.local.entity.InventoryItemType
import com.example.data.local.entity.JournalEntryEntity
import com.example.data.local.entity.JournalEntryType
import com.example.data.local.entity.MissionEntity
import com.example.data.local.entity.MissionStatus
import com.example.data.local.entity.NetNodeEntity
import com.example.data.local.entity.NetNodeType
import com.example.data.local.entity.NpcFactionEntity
import com.example.data.local.entity.RuleDocumentEntity
import com.example.data.local.entity.RuleSectionEntity
import com.example.data.repository.CampaignRepository
import com.example.data.repository.CharacterRepository
import com.example.data.repository.IpSpendOutcome
import com.example.data.repository.RuleLibraryRepository
import com.example.data.repository.SessionRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

data class MainUiState(
    val campaigns: List<CampaignEntity> = emptyList(),
    val activeCampaign: CampaignEntity? = null,
    val saves: List<CampaignSaveEntity> = emptyList(),
    val characters: List<CharacterEntity> = emptyList(),
    val primaryCharacter: CharacterEntity? = null,
    val derivedMetrics: DerivedCharacterMetrics? = null,
    val skills: List<CharacterSkillEntity> = emptyList(),
    val cyberware: List<CyberwareEntity> = emptyList(),
    val inventory: List<InventoryItemEntity> = emptyList(),
    val netNodes: List<NetNodeEntity> = emptyList(),
    val npcsAndFactions: List<NpcFactionEntity> = emptyList(),
    val missions: List<MissionEntity> = emptyList(),
    val combatParticipants: List<CombatParticipantEntity> = emptyList(),
    val journal: List<JournalEntryEntity> = emptyList(),
    val ruleDocuments: List<RuleDocumentEntity> = emptyList(),
    val ruleSearchQuery: String = "",
    val ruleSections: List<RuleSectionEntity> = emptyList(),
    val lastSkillCheckResult: SkillCheckResult? = null,
    val lastInterfaceCheckResult: InterfaceCheckResult? = null,
    val lastDeathSaveResult: DeathSaveResult? = null,
    val lastDamageResult: DamageApplicationResult? = null,
    val lastNightMarket: GeneratedNightMarket? = null,
    val lastEncounter: EncounterRollResult? = null,
    val lastStreetPrompt: String? = null,
    val lastHustleResult: HustleRollResult? = null,
    val lastWeatherResult: WeatherRollResult? = null,
    val lastSalvageResult: SalvageAttemptResult? = null,
    val lastDatepathResult: DatepathRollResult? = null,
    val lastPlotHook: PlotHookSpec? = null,
    val lastRcVendorPrompt: String? = null,
    val lastSoloPlusRoll: SoloPlusRollResult? = null,
    val lastNpcPersonalityBundle: SoloNpcPersonalityBundle? = null,
    val lastSoloOracleResult: SoloOracleYesNoResult? = null,
    val lastStealthNetResult: StealthNetContestResult? = null,
    val lastAgentHackResult: AgentHackResult? = null,
    val lastHusToolResult: HusToolResult? = null,
    val cyberwareSlotSummary: CyberwareSlotSummary = CyberwareSlotSummary(),
    val lastBrainwasherResult: BrainwasherResult? = null,
    val lastTherapyOutcome: TherapyOutcome? = null,
    val statusBannerMessage: String? = null,
    val aiProviderMode: AiProviderMode = AiProviderMode.OFFLINE_DETERMINISTIC,
    val isProcessingAction: Boolean = false
)

private data class CampaignSystemsBundle(
    val saves: List<CampaignSaveEntity> = emptyList(),
    val characters: List<CharacterEntity> = emptyList(),
    val primaryCharacter: CharacterEntity? = null,
    val skills: List<CharacterSkillEntity> = emptyList(),
    val cyberware: List<CyberwareEntity> = emptyList(),
    val inventory: List<InventoryItemEntity> = emptyList(),
    val netNodes: List<NetNodeEntity> = emptyList(),
    val npcsAndFactions: List<NpcFactionEntity> = emptyList(),
    val missions: List<MissionEntity> = emptyList(),
    val combatParticipants: List<CombatParticipantEntity> = emptyList(),
    val journal: List<JournalEntryEntity> = emptyList()
)

private data class TransientResultBundle(
    val lastSkillCheckResult: SkillCheckResult? = null,
    val lastInterfaceCheckResult: InterfaceCheckResult? = null,
    val lastDeathSaveResult: DeathSaveResult? = null,
    val lastDamageResult: DamageApplicationResult? = null,
    val lastNightMarket: GeneratedNightMarket? = null,
    val lastEncounter: EncounterRollResult? = null,
    val lastStreetPrompt: String? = null,
    val lastHustleResult: HustleRollResult? = null,
    val lastWeatherResult: WeatherRollResult? = null,
    val lastSalvageResult: SalvageAttemptResult? = null,
    val lastDatepathResult: DatepathRollResult? = null,
    val lastPlotHook: PlotHookSpec? = null,
    val lastRcVendorPrompt: String? = null,
    val lastSoloPlusRoll: SoloPlusRollResult? = null,
    val lastNpcPersonalityBundle: SoloNpcPersonalityBundle? = null,
    val lastSoloOracleResult: SoloOracleYesNoResult? = null,
    val lastStealthNetResult: StealthNetContestResult? = null,
    val lastAgentHackResult: AgentHackResult? = null,
    val lastHusToolResult: HusToolResult? = null,
    val lastBrainwasherResult: BrainwasherResult? = null,
    val lastTherapyOutcome: TherapyOutcome? = null,
    val statusBannerMessage: String? = null,
    val isProcessingAction: Boolean = false
)

@OptIn(ExperimentalCoroutinesApi::class)
class MainViewModel(
    private val campaignRepository: CampaignRepository,
    private val characterRepository: CharacterRepository,
    private val sessionRepository: SessionRepository,
    private val ruleLibraryRepository: RuleLibraryRepository,
    private val skillCheckEngine: SkillCheckEngine,
    private val netrunningEngine: NetrunningEngine,
    private val gameMasterGateway: GameMasterGateway
) : ViewModel() {

    private val ruleQueryFlow = MutableStateFlow("")
    private val transientResultsFlow = MutableStateFlow(TransientResultBundle())

    private val activeCampaignFlow = campaignRepository.activeCampaign

    private val campaignSystemsFlow: Flow<CampaignSystemsBundle> =
        activeCampaignFlow.flatMapLatest { campaign ->
            if (campaign == null) {
                flowOf(CampaignSystemsBundle())
            } else {
                val primaryCharFlow = characterRepository.observePrimaryCharacter(campaign.id)
                val skillsFlow = primaryCharFlow.flatMapLatest { char ->
                    if (char == null) flowOf(emptyList()) else characterRepository.observeSkills(char.id)
                }
                val cyberwareFlow = primaryCharFlow.flatMapLatest { char ->
                    if (char == null) flowOf(emptyList()) else characterRepository.observeCyberware(char.id)
                }

                val charGroupFlow = combine(
                    campaignRepository.observeSaves(campaign.id),
                    characterRepository.observeCharacters(campaign.id),
                    primaryCharFlow,
                    skillsFlow,
                    cyberwareFlow
                ) { saves, chars, primary, skills, cyber ->
                    CampaignSystemsBundle(
                        saves = saves,
                        characters = chars,
                        primaryCharacter = primary,
                        skills = skills,
                        cyberware = cyber
                    )
                }

                val worldGroupFlow = combine(
                    sessionRepository.observeInventory(campaign.id),
                    sessionRepository.observeNetNodes(campaign.id),
                    sessionRepository.observeNpcsAndFactions(campaign.id),
                    sessionRepository.observeMissions(campaign.id),
                    sessionRepository.observeCombat(campaign.id),
                    sessionRepository.observeJournal(campaign.id)
                ) { values ->
                    @Suppress("UNCHECKED_CAST")
                    Triple(
                        values[0] as List<InventoryItemEntity>,
                        values[1] as List<NetNodeEntity>,
                        values[2] as List<NpcFactionEntity>
                    ) to Triple(
                        values[3] as List<MissionEntity>,
                        values[4] as List<CombatParticipantEntity>,
                        values[5] as List<JournalEntryEntity>
                    )
                }

                combine(charGroupFlow, worldGroupFlow) { charGroup, worldGroup ->
                    charGroup.copy(
                        inventory = worldGroup.first.first,
                        netNodes = worldGroup.first.second,
                        npcsAndFactions = worldGroup.first.third,
                        missions = worldGroup.second.first,
                        combatParticipants = worldGroup.second.second,
                        journal = worldGroup.second.third
                    )
                }
            }
        }

    private val ruleSectionResultsFlow = ruleQueryFlow.flatMapLatest { query ->
        ruleLibraryRepository.searchSections(query)
    }

    val uiState: StateFlow<MainUiState> = combine(
        campaignRepository.allCampaigns,
        activeCampaignFlow,
        campaignSystemsFlow,
        ruleLibraryRepository.documents,
        ruleQueryFlow,
        ruleSectionResultsFlow,
        transientResultsFlow
    ) { values ->
        @Suppress("UNCHECKED_CAST")
        val campaigns = values[0] as List<CampaignEntity>
        val activeCampaign = values[1] as CampaignEntity?
        val systems = values[2] as CampaignSystemsBundle
        @Suppress("UNCHECKED_CAST")
        val docs = values[3] as List<RuleDocumentEntity>
        val query = values[4] as String
        @Suppress("UNCHECKED_CAST")
        val sections = values[5] as List<RuleSectionEntity>
        val transient = values[6] as TransientResultBundle

        val primary = systems.primaryCharacter
        val installedWithHl = systems.cyberware.filter { it.installed && it.humanityCost > 0 }
        val maxHumanityReduction = installedWithHl.sumOf { cw ->
            if (CyberwareValidator.isBorgware(cw)) 4 else 2
        }
        val slotSummary = CyberwareValidator.computeSlotSummary(systems.cyberware)
        val derived = primary?.let {
            CharacterCalculator.computeDerivedMetrics(
                stats = it.toCharacterStats(),
                currentHp = it.currentHp,
                totalHumanityLoss = it.humanityLoss,
                maxHumanityReduction = maxHumanityReduction,
                headArmorSp = it.headArmorSp,
                bodyArmorSp = it.bodyArmorSp,
                installedCyberware = systems.cyberware
            )
        }

        MainUiState(
            campaigns = campaigns,
            activeCampaign = activeCampaign,
            saves = systems.saves,
            characters = systems.characters,
            primaryCharacter = primary,
            derivedMetrics = derived,
            skills = systems.skills,
            cyberware = systems.cyberware,
            inventory = systems.inventory,
            netNodes = systems.netNodes,
            npcsAndFactions = systems.npcsAndFactions,
            missions = systems.missions,
            combatParticipants = systems.combatParticipants,
            journal = systems.journal,
            ruleDocuments = docs,
            ruleSearchQuery = query,
            ruleSections = sections,
            lastSkillCheckResult = transient.lastSkillCheckResult,
            lastInterfaceCheckResult = transient.lastInterfaceCheckResult,
            lastDeathSaveResult = transient.lastDeathSaveResult,
            lastDamageResult = transient.lastDamageResult,
            lastNightMarket = transient.lastNightMarket,
            lastEncounter = transient.lastEncounter,
            lastStreetPrompt = transient.lastStreetPrompt,
            lastHustleResult = transient.lastHustleResult,
            lastWeatherResult = transient.lastWeatherResult,
            lastSalvageResult = transient.lastSalvageResult,
            lastDatepathResult = transient.lastDatepathResult,
            lastPlotHook = transient.lastPlotHook,
            lastRcVendorPrompt = transient.lastRcVendorPrompt,
            lastSoloPlusRoll = transient.lastSoloPlusRoll,
            lastNpcPersonalityBundle = transient.lastNpcPersonalityBundle,
            lastSoloOracleResult = transient.lastSoloOracleResult,
            lastStealthNetResult = transient.lastStealthNetResult,
            lastAgentHackResult = transient.lastAgentHackResult,
            lastHusToolResult = transient.lastHusToolResult,
            cyberwareSlotSummary = slotSummary,
            lastBrainwasherResult = transient.lastBrainwasherResult,
            lastTherapyOutcome = transient.lastTherapyOutcome,
            statusBannerMessage = transient.statusBannerMessage,
            aiProviderMode = gameMasterGateway.providerMode,
            isProcessingAction = transient.isProcessingAction
        )
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = MainUiState()
    )

    fun clearBannerMessage() {
        transientResultsFlow.value = transientResultsFlow.value.copy(statusBannerMessage = null)
    }

    // --- 1. Campaign & Time ---
    fun createCampaign(
        title: String,
        settingNotes: String,
        edgerunnerHandle: String,
        role: RedRole,
        startingEb: Int
    ) {
        if (title.isBlank() || edgerunnerHandle.isBlank()) return
        viewModelScope.launch {
            campaignRepository.createCampaignWithEdgerunner(
                campaignTitle = title,
                settingNotes = settingNotes,
                edgerunnerHandle = edgerunnerHandle,
                role = role,
                startingEurodollars = startingEb
            )
            setBanner("Campanha \"$title\" criada e ativada.")
        }
    }

    fun createCampaignFromPregen(
        title: String,
        settingNotes: String,
        pregen: StreetratPregenSpec
    ) {
        viewModelScope.launch {
            campaignRepository.createCampaignFromPregen(title, settingNotes, pregen)
            setBanner("Campanha com ${pregen.handle} (${pregen.role.displayNamePt}) criada!")
        }
    }

    fun selectCampaign(campaignId: Long) {
        viewModelScope.launch {
            campaignRepository.setActiveCampaign(campaignId)
        }
    }

    fun deleteCampaign(campaignId: Long) {
        viewModelScope.launch {
            campaignRepository.deleteCampaign(campaignId)
            setBanner("Campanha removida.")
        }
    }

    fun advanceTime(hours: Int) {
        val campaign = uiState.value.activeCampaign ?: return
        viewModelScope.launch {
            campaignRepository.advanceTime(campaign, hours)
        }
    }

    fun createSaveSnapshot(label: String) {
        val campaign = uiState.value.activeCampaign ?: return
        val character = uiState.value.primaryCharacter
        viewModelScope.launch {
            campaignRepository.createSaveSnapshot(campaign, character, label)
            setBanner("Snapshot salvo no banco local.")
        }
    }

    fun restoreSaveSnapshot(save: CampaignSaveEntity) {
        viewModelScope.launch {
            val ok = campaignRepository.restoreSaveSnapshot(save)
            setBanner(if (ok) "Snapshot \"${save.label}\" restaurado." else "Falha ao restaurar snapshot.")
        }
    }

    fun deleteSaveSnapshot(saveId: Long) {
        viewModelScope.launch {
            campaignRepository.deleteSave(saveId)
        }
    }

    // --- 2, 3, 4, 7. Character, Stats, Skills, Cyberware ---
    fun createAdditionalCharacter(
        handle: String,
        role: RedRole,
        stats: CharacterStats
    ) {
        val campaign = uiState.value.activeCampaign ?: return
        if (handle.isBlank()) return
        viewModelScope.launch {
            characterRepository.createCharacter(
                campaignId = campaign.id,
                handle = handle,
                role = role,
                stats = stats,
                isPrimary = uiState.value.primaryCharacter == null
            )
            setBanner("Personagem \"$handle\" criado.")
        }
    }

    fun adjustStat(stat: RedStat, delta: Int) {
        val char = uiState.value.primaryCharacter ?: return
        viewModelScope.launch {
            characterRepository.updateStat(char, stat, delta)
        }
    }

    fun adjustHp(delta: Int) {
        val char = uiState.value.primaryCharacter ?: return
        viewModelScope.launch {
            characterRepository.adjustHp(char, delta)
        }
    }

    fun adjustArmorSp(isHead: Boolean, delta: Int) {
        val char = uiState.value.primaryCharacter ?: return
        viewModelScope.launch {
            val updated = if (isHead) {
                char.copy(headArmorSp = (char.headArmorSp + delta).coerceIn(0, 25))
            } else {
                char.copy(bodyArmorSp = (char.bodyArmorSp + delta).coerceIn(0, 25))
            }
            characterRepository.updateCharacter(updated)
        }
    }

    fun restoreLuck() {
        val char = uiState.value.primaryCharacter ?: return
        viewModelScope.launch {
            characterRepository.updateCharacter(char.copy(luckCurrent = char.luckMax))
            setBanner("Sorte restaurada para ${char.luckMax}.")
        }
    }

    fun addOrUpdateSkill(name: String, stat: RedStat, level: Int, category: String) {
        val char = uiState.value.primaryCharacter ?: return
        if (name.isBlank()) return
        viewModelScope.launch {
            characterRepository.addOrUpdateSkill(
                CharacterSkillEntity(
                    characterId = char.id,
                    name = name.trim(),
                    stat = stat,
                    level = level.coerceIn(0, 10),
                    category = category.trim().ifEmpty { "Geral" }
                )
            )
        }
    }

    fun updateSkillLevel(skill: CharacterSkillEntity, delta: Int) {
        viewModelScope.launch {
            characterRepository.addOrUpdateSkill(
                skill.copy(level = (skill.level + delta).coerceIn(0, 10))
            )
        }
    }

    fun deleteSkill(skillId: Long) {
        viewModelScope.launch {
            characterRepository.deleteSkill(skillId)
        }
    }

    fun installCyberware(name: String, slotArea: String, humanityCost: Int, notes: String) {
        val campaign = uiState.value.activeCampaign ?: return
        val char = uiState.value.primaryCharacter ?: return
        if (name.isBlank()) return
        viewModelScope.launch {
            when (val res = characterRepository.installCyberware(char, name, slotArea, humanityCost, notes)) {
                is CyberwareInstallOutcome.Blocked -> setBanner(res.reasonPt)
                is CyberwareInstallOutcome.Installed -> {
                    sessionRepository.addJournalEntry(
                        campaign = campaign,
                        entryType = JournalEntryType.SYSTEM_EVENT,
                        speakerLabel = "Cyberware",
                        bodyText = "${char.handle} instalou \"$name\" ($slotArea, -$humanityCost HL). COR efetivo: ${res.effectiveBody}, PV Máx: ${res.newMaxHp}."
                    )
                    val warn = if (res.warningsPt.isNotEmpty()) " (${res.warningsPt.first()})" else ""
                    setBanner("Cyberware \"$name\" instalado (-$humanityCost HL)$warn.")
                }
            }
        }
    }

    fun toggleCyberware(cyberware: CyberwareEntity) {
        val char = uiState.value.primaryCharacter ?: return
        viewModelScope.launch {
            characterRepository.toggleCyberwareInstalled(char, cyberware, uiState.value.cyberware)
        }
    }

    fun deleteCyberware(cyberware: CyberwareEntity) {
        val char = uiState.value.primaryCharacter ?: return
        viewModelScope.launch {
            characterRepository.deleteCyberware(char, cyberware, uiState.value.cyberware)
        }
    }

    // --- 4 & 5. Deterministic Rolls & Combat ---
    fun performSkillCheck(
        stat: RedStat,
        skillName: String,
        skillLevel: Int,
        luckToSpend: Int,
        modifier: Int,
        difficultyValue: Int
    ) {
        val state = uiState.value
        val campaign = state.activeCampaign ?: return
        val char = state.primaryCharacter ?: return
        val derived = state.derivedMetrics ?: return

        val actualLuck = luckToSpend.coerceIn(0, char.luckCurrent)
        val statVal = if (stat == RedStat.BODY) {
            derived.effectiveBody
        } else {
            char.toCharacterStats().getStat(
                stat = stat,
                currentEmpathy = derived.currentEmpathy,
                currentLuck = char.luckCurrent
            )
        }
        val woundMod = derived.effectiveWoundPenalty
        val armorMod = derived.armorStatPenalty(stat)
        val effectiveMod = modifier + woundMod + armorMod

        val request = SkillCheckRequest(
            stat = stat,
            statValue = statVal,
            skillName = skillName,
            skillLevel = skillLevel,
            luckSpent = actualLuck,
            modifier = effectiveMod,
            difficultyValue = difficultyValue
        )
        val result = skillCheckEngine.performSkillCheck(request)

        viewModelScope.launch {
            if (actualLuck > 0) {
                characterRepository.updateCharacter(char.copy(luckCurrent = char.luckCurrent - actualLuck))
            }
            val gmProposal = gameMasterGateway.narrateCheckOutcome(
                actionDescription = skillName,
                outcome = result,
                context = buildContextSnapshot(state)
            )
            val rollBreakdown = buildString {
                append("${stat.code}($statVal) + $skillName($skillLevel)")
                if (actualLuck > 0) append(" + SOR($actualLuck)")
                if (modifier != 0) append(" + Mod($modifier)")
                if (woundMod != 0) append(" + Ferim($woundMod)")
                if (armorMod != 0) append(" + Armad($armorMod)")
                append(" + 1d10(${result.roll.netDieTotal})")
                when (result.roll.marker) {
                    CriticalMarker.CRITICAL_SUCCESS -> append(" [CRÍTICO 10+${result.roll.secondaryDie}]")
                    CriticalMarker.CRITICAL_FAILURE -> append(" [FALHA CRÍTICA 1-${result.roll.secondaryDie}]")
                    CriticalMarker.NONE -> Unit
                }
                append(" = ${result.total} vs DV $difficultyValue")
            }
            sessionRepository.addJournalEntry(
                campaign = campaign,
                entryType = JournalEntryType.DETERMINISTIC_ROLL,
                speakerLabel = gmProposal.speakerLabel,
                bodyText = if (result.succeeded) "SUCESSO em $skillName" else "FALHA em $skillName",
                deterministicDetail = rollBreakdown
            )
            transientResultsFlow.value = transientResultsFlow.value.copy(
                lastSkillCheckResult = result
            )
        }
    }

    fun applyDamageToPrimary(
        damageDiceCount: Int,
        isHeadshot: Boolean,
        ignoreHalfArmor: Boolean = false
    ) {
        val campaign = uiState.value.activeCampaign ?: return
        val char = uiState.value.primaryCharacter ?: return
        viewModelScope.launch {
            val outcome = characterRepository.applyDeterministicDamage(
                character = char,
                damageDiceCount = damageDiceCount,
                isHeadshot = isHeadshot,
                ignoreHalfArmor = ignoreHalfArmor
            )
            val spDesc = if (ignoreHalfArmor) {
                "PA efetiva ${outcome.effectiveStoppingPower} (½ PA ${outcome.stoppingPowerBefore})->${outcome.stoppingPowerAfter}"
            } else {
                "PA ${outcome.stoppingPowerBefore}->${outcome.stoppingPowerAfter}"
            }
            val critText = outcome.criticalInjury?.let {
                " | Crítico (2d6=${it.roll2d6}): ${it.namePt} — ${it.effectPt}"
            }.orEmpty()
            val detail = "${damageDiceCount}d6${outcome.damageDiceOutcome.dice} = ${outcome.damageDiceOutcome.total} | " +
                "$spDesc | Dano real: ${outcome.hpDamageDealt} | PV: ${outcome.hpAfter}$critText"
            sessionRepository.addJournalEntry(
                campaign = campaign,
                entryType = JournalEntryType.DETERMINISTIC_ROLL,
                speakerLabel = "Dano Recebido",
                bodyText = if (outcome.criticalInjuryTriggered) {
                    "FERIMENTO CRÍTICO (${outcome.criticalInjury?.namePt ?: "Crítico"})! ${char.handle} sofreu -${outcome.hpDamageDealt} PV (${outcome.woundStateAfter.labelPt})."
                } else {
                    "Impacto ${if (isHeadshot) "na Cabeça" else "no Corpo"}: -${outcome.hpDamageDealt} PV (${outcome.woundStateAfter.labelPt})."
                },
                deterministicDetail = detail
            )
            transientResultsFlow.value = transientResultsFlow.value.copy(lastDamageResult = outcome)
        }
    }

    fun rollDeathSave() {
        val campaign = uiState.value.activeCampaign ?: return
        val char = uiState.value.primaryCharacter ?: return
        viewModelScope.launch {
            val outcome = characterRepository.rollDeathSaveForCharacter(char)
            val status = if (outcome.survived) "SOBREVIVEU (Mod futuro +1)" else "FALHOU NO TESTE DE MORTE"
            sessionRepository.addJournalEntry(
                campaign = campaign,
                entryType = JournalEntryType.DETERMINISTIC_ROLL,
                speakerLabel = "Teste de Morte",
                bodyText = "${char.handle}: $status",
                deterministicDetail = "1d10(${outcome.dieRoll}) + Mod(${outcome.penaltyApplied}) = ${outcome.effectiveRoll} < COR(${outcome.bodyTarget})"
            )
            transientResultsFlow.value = transientResultsFlow.value.copy(lastDeathSaveResult = outcome)
        }
    }

    fun submitPlayerAction(actionText: String) {
        val trimmed = actionText.trim()
        if (trimmed.isEmpty()) return
        val state = uiState.value
        val campaign = state.activeCampaign ?: return

        viewModelScope.launch {
            transientResultsFlow.value = transientResultsFlow.value.copy(isProcessingAction = true)
            sessionRepository.addJournalEntry(
                campaign = campaign,
                entryType = JournalEntryType.PLAYER_ACTION,
                speakerLabel = state.primaryCharacter?.handle ?: "Jogador",
                bodyText = trimmed
            )
            val proposal = gameMasterGateway.processPlayerAction(
                playerInput = trimmed,
                context = buildContextSnapshot(state)
            )
            sessionRepository.addJournalEntry(
                campaign = campaign,
                entryType = JournalEntryType.NARRATOR_GM,
                speakerLabel = proposal.speakerLabel,
                bodyText = proposal.narrativeText
            )
            transientResultsFlow.value = transientResultsFlow.value.copy(isProcessingAction = false)
        }
    }

    fun addCombatant(name: String, isPlayer: Boolean, reflexes: Int, maxHp: Int, armorSp: Int) {
        val campaign = uiState.value.activeCampaign ?: return
        if (name.isBlank()) return
        viewModelScope.launch {
            val initTotal = sessionRepository.addCombatParticipantWithInitiative(
                campaignId = campaign.id,
                name = name,
                isPlayer = isPlayer,
                reflexes = reflexes,
                maxHp = maxHp,
                armorSp = armorSp,
                existingParticipants = uiState.value.combatParticipants
            )
            sessionRepository.addJournalEntry(
                campaign = campaign,
                entryType = JournalEntryType.DETERMINISTIC_ROLL,
                speakerLabel = "Iniciativa",
                bodyText = "$name entrou no combate com Iniciativa $initTotal (REF $reflexes + 1d10)."
            )
        }
    }

    fun nextCombatTurn() {
        viewModelScope.launch {
            sessionRepository.advanceCombatTurn(uiState.value.combatParticipants)
        }
    }

    fun adjustCombatantHp(participant: CombatParticipantEntity, delta: Int) {
        viewModelScope.launch {
            sessionRepository.updateCombatParticipant(
                participant.copy(currentHp = (participant.currentHp + delta).coerceIn(0, participant.maxHp))
            )
        }
    }

    fun removeCombatant(id: Long) {
        viewModelScope.launch {
            sessionRepository.removeCombatParticipant(id)
        }
    }

    fun clearCombat() {
        val campaign = uiState.value.activeCampaign ?: return
        viewModelScope.launch {
            sessionRepository.clearCombat(campaign.id)
        }
    }

    // --- 6, 8, 9, 10, 11. Inventory, Economy, Netrunning, NPCs, Missions ---
    fun buyOrAddItem(
        name: String,
        type: InventoryItemType,
        priceCategory: PriceCategory,
        costEb: Int,
        quantity: Int,
        damageDice: Int,
        stoppingPower: Int,
        payWithEb: Boolean,
        notes: String
    ) {
        val campaign = uiState.value.activeCampaign ?: return
        if (name.isBlank()) return
        viewModelScope.launch {
            when (val res = sessionRepository.purchaseOrAddItem(
                campaign = campaign,
                name = name,
                itemType = type,
                priceCategory = priceCategory,
                costEb = costEb,
                quantity = quantity,
                damageDice = damageDice,
                stoppingPower = stoppingPower,
                payWithEurodollars = payWithEb,
                notes = notes
            )) {
                is PurchaseOutcome.Approved -> setBanner("Item \"$name\" adicionado ao inventário.")
                is PurchaseOutcome.InsufficientFunds -> setBanner(
                    "Eurodólares insuficientes: precisa de ${res.requiredEurodollars} eb (saldo: ${res.currentEurodollars} eb)."
                )
            }
        }
    }

    fun toggleItemEquipped(item: InventoryItemEntity) {
        viewModelScope.launch { sessionRepository.toggleItemEquipped(item) }
    }

    fun adjustItemQuantity(item: InventoryItemEntity, delta: Int) {
        viewModelScope.launch { sessionRepository.adjustItemQuantity(item, delta) }
    }

    fun addNetNode(
        architectureName: String,
        floorNumber: Int,
        nodeType: NetNodeType,
        title: String,
        dvOrRez: Int
    ) {
        val campaign = uiState.value.activeCampaign ?: return
        if (architectureName.isBlank() || title.isBlank()) return
        viewModelScope.launch {
            sessionRepository.addNetNode(campaign.id, architectureName, floorNumber, nodeType, title, dvOrRez)
        }
    }

    fun generateOfficialNetArchitecture(architectureName: String, difficulty: NetArchDifficulty) {
        val campaign = uiState.value.activeCampaign ?: return
        val cleanName = architectureName.trim().ifEmpty { "Arquitetura ${difficulty.labelPt}" }
        viewModelScope.launch {
            val floorCount = sessionRepository.generateNetArchitecture(campaign.id, cleanName, difficulty)
            sessionRepository.addJournalEntry(
                campaign = campaign,
                entryType = JournalEntryType.SYSTEM_EVENT,
                speakerLabel = "Gerador NET",
                bodyText = "Arquitetura \"$cleanName\" (${difficulty.labelPt}) gerada com $floorCount andares oficiais."
            )
            setBanner("Arquitetura \"$cleanName\" ($floorCount andares) gerada.")
        }
    }

    fun runInterfaceCheck(node: NetNodeEntity) {
        val campaign = uiState.value.activeCampaign ?: return
        val char = uiState.value.primaryCharacter ?: return
        val interfaceRank = if (char.role == RedRole.NETRUNNER) char.roleRank else 1
        val result = netrunningEngine.performInterfaceCheck(
            interfaceRank = interfaceRank,
            difficultyValue = node.difficultyValue
        )
        viewModelScope.launch {
            if (result.succeeded && !node.isCleared) {
                sessionRepository.toggleNetNodeCleared(node)
            }
            sessionRepository.addJournalEntry(
                campaign = campaign,
                entryType = JournalEntryType.DETERMINISTIC_ROLL,
                speakerLabel = "Netrunning • Interface",
                bodyText = "${node.title}: ${if (result.succeeded) "ACESSO CONCEDIDO" else "BLOQUEADO"}",
                deterministicDetail = "Interface($interfaceRank) + 1d10(${result.roll.netDieTotal}) = ${result.total} vs DV ${node.difficultyValue}"
            )
            transientResultsFlow.value = transientResultsFlow.value.copy(lastInterfaceCheckResult = result)
        }
    }

    fun damageIceNode(node: NetNodeEntity, damageDice: Int) {
        val campaign = uiState.value.activeCampaign ?: return
        viewModelScope.launch {
            val result = sessionRepository.damageNetIce(node, damageDice)
            sessionRepository.addJournalEntry(
                campaign = campaign,
                entryType = JournalEntryType.DETERMINISTIC_ROLL,
                speakerLabel = "Ataque de Programa",
                bodyText = "${node.title}: -${result.damageDice.total} REZ (${if (result.derezzed) "DEREZZED" else "${result.rezAfter} REZ restante"})."
            )
        }
    }

    fun toggleNetNodeCleared(node: NetNodeEntity) {
        viewModelScope.launch { sessionRepository.toggleNetNodeCleared(node) }
    }

    fun deleteNetNode(id: Long) {
        viewModelScope.launch { sessionRepository.deleteNetNode(id) }
    }

    fun addNpcOrFaction(name: String, isFaction: Boolean, affiliation: String, attitude: String, notes: String) {
        val campaign = uiState.value.activeCampaign ?: return
        if (name.isBlank()) return
        viewModelScope.launch {
            sessionRepository.addNpcOrFaction(campaign.id, name, isFaction, affiliation, attitude, notes)
        }
    }

    fun deleteNpcFaction(id: Long) {
        viewModelScope.launch { sessionRepository.deleteNpcFaction(id) }
    }

    fun addMission(title: String, fixerName: String, rewardEb: Int, objectives: String) {
        val campaign = uiState.value.activeCampaign ?: return
        if (title.isBlank()) return
        viewModelScope.launch {
            sessionRepository.addMission(campaign.id, title, fixerName, rewardEb, objectives)
        }
    }

    fun setMissionStatus(mission: MissionEntity, status: MissionStatus) {
        val campaign = uiState.value.activeCampaign ?: return
        viewModelScope.launch {
            sessionRepository.updateMissionStatus(campaign, mission, status)
        }
    }

    fun deleteMission(id: Long) {
        viewModelScope.launch { sessionRepository.deleteMission(id) }
    }

    // --- Part 2: I.P. Progression, Hustle, Night Market, Encounters & Street Generators ---
    private val part2DiceEngine = DiceEngine()

    fun awardImprovementPoints(amount: Int) {
        val campaign = uiState.value.activeCampaign ?: return
        val char = uiState.value.primaryCharacter ?: return
        if (amount <= 0) return
        viewModelScope.launch {
            val updated = characterRepository.awardImprovementPoints(char, amount)
            sessionRepository.addJournalEntry(
                campaign = campaign,
                entryType = JournalEntryType.SYSTEM_EVENT,
                speakerLabel = "Evolução (I.P.)",
                bodyText = "${char.handle} recebeu +$amount Pontos de Melhoria (Total: ${updated.improvementPoints} I.P.)."
            )
            setBanner("+$amount I.P. creditado (${updated.improvementPoints} I.P. disponíveis).")
        }
    }

    fun upgradeSkillWithIp(skill: CharacterSkillEntity) {
        val campaign = uiState.value.activeCampaign ?: return
        val char = uiState.value.primaryCharacter ?: return
        val isDouble = OfficialSkillCatalog.allSkills
            .firstOrNull { it.namePt.equals(skill.name, ignoreCase = true) }
            ?.isDoubleCost == true
        viewModelScope.launch {
            when (val res = characterRepository.upgradeSkillWithIp(char, skill, isDouble)) {
                is IpSpendOutcome.Success -> {
                    sessionRepository.addJournalEntry(
                        campaign = campaign,
                        entryType = JournalEntryType.SYSTEM_EVENT,
                        speakerLabel = "Melhoria (I.P.)",
                        bodyText = "${skill.name} evoluiu para Nível ${res.newLevelOrRank} (-${res.spentIp} I.P.). Restante: ${res.remainingIp} I.P."
                    )
                    setBanner("${skill.name} -> Nv ${res.newLevelOrRank} (-${res.spentIp} I.P.).")
                }
                is IpSpendOutcome.InsufficientIp -> setBanner(
                    "I.P. insuficiente para ${skill.name}: exige ${res.requiredIp} I.P. (atual: ${res.currentIp})."
                )
                IpSpendOutcome.MaxLevelReached -> setBanner("${skill.name} já está no nível máximo (10).")
            }
        }
    }

    fun upgradeRoleRankWithIp() {
        val campaign = uiState.value.activeCampaign ?: return
        val char = uiState.value.primaryCharacter ?: return
        viewModelScope.launch {
            when (val res = characterRepository.upgradeRoleRankWithIp(char)) {
                is IpSpendOutcome.Success -> {
                    sessionRepository.addJournalEntry(
                        campaign = campaign,
                        entryType = JournalEntryType.SYSTEM_EVENT,
                        speakerLabel = "Habilidade de Função",
                        bodyText = "${char.role.roleAbilityPt} evoluiu para Rank ${res.newLevelOrRank} (-${res.spentIp} I.P.). Restante: ${res.remainingIp} I.P."
                    )
                    setBanner("${char.role.roleAbilityPt} -> Rank ${res.newLevelOrRank} (-${res.spentIp} I.P.).")
                }
                is IpSpendOutcome.InsufficientIp -> setBanner(
                    "I.P. insuficiente para Rank ${char.roleRank + 1}: exige ${res.requiredIp} I.P. (atual: ${res.currentIp})."
                )
                IpSpendOutcome.MaxLevelReached -> setBanner("Habilidade de Função já está no Rank máximo (10).")
            }
        }
    }

    fun runHustle(advanceSevenDays: Boolean) {
        val campaign = uiState.value.activeCampaign ?: return
        val char = uiState.value.primaryCharacter ?: return
        viewModelScope.launch {
            val result = sessionRepository.runRoleHustle(
                campaign = campaign,
                role = char.role,
                roleRank = char.roleRank,
                advanceSevenDays = advanceSevenDays,
                diceEngine = part2DiceEngine
            )
            transientResultsFlow.value = transientResultsFlow.value.copy(lastHustleResult = result)
            setBanner("Hustle [1d6=${result.roll1d6}]: +${result.earnedEb} eb!")
        }
    }

    fun payLifestyleOrHousing(
        description: String,
        costEb: Int,
        newLifestyleLabel: String? = null,
        resetRentCycle: Boolean = false
    ) {
        val campaign = uiState.value.activeCampaign ?: return
        viewModelScope.launch {
            when (val res = sessionRepository.payLifestyleOrHousing(campaign, description, costEb, newLifestyleLabel, resetRentCycle)) {
                is PurchaseOutcome.Approved -> setBanner("Pago $description (-$costEb eb). Saldo: ${res.remainingEurodollars} eb.")
                is PurchaseOutcome.InsufficientFunds -> setBanner(
                    "Eurodólares insuficientes para $description: precisa ${res.requiredEurodollars} eb (saldo: ${res.currentEurodollars} eb)."
                )
            }
        }
    }

    fun generateNightMarket() {
        val market = OfficialStreetGenerators.generateNightMarket(part2DiceEngine)
        transientResultsFlow.value = transientResultsFlow.value.copy(lastNightMarket = market)
        val cats = market.categories.joinToString(" & ") { it.labelPt }
        setBanner("Mercado Noturno gerado: $cats (${market.offers.size} ofertas).")
    }

    fun buyNightMarketOffer(offer: NightMarketOffer) {
        buyOrAddItem(
            name = offer.labelPt,
            type = offer.itemType,
            priceCategory = offer.priceCategory,
            costEb = offer.costEb,
            quantity = 1,
            damageDice = 0,
            stoppingPower = 0,
            payWithEb = true,
            notes = "Mercado Noturno (${offer.category.labelPt}, d100=${offer.d100Roll})"
        )
    }

    fun rollRandomEncounter(timeOfDay: EncounterTimeOfDay) {
        val campaign = uiState.value.activeCampaign ?: return
        val result = OfficialEncountersAndLoreCatalog.rollEncounter(timeOfDay, part2DiceEngine)
        viewModelScope.launch {
            sessionRepository.addJournalEntry(
                campaign = campaign,
                entryType = JournalEntryType.DETERMINISTIC_ROLL,
                speakerLabel = "Encontro (${timeOfDay.labelPt})",
                bodyText = "${result.entry.titlePt}: ${result.entry.descriptionPt}",
                deterministicDetail = "d100=${result.d100Roll}" +
                    (result.resolvedNpc?.let { " • Hostil sugerido: ${it.namePt} (×${result.entry.suggestedNpcCount})" } ?: "")
            )
            transientResultsFlow.value = transientResultsFlow.value.copy(lastEncounter = result)
        }
    }

    fun spawnOfficialNpcToCombat(npc: OfficialNpcSpec, count: Int) {
        val campaign = uiState.value.activeCampaign ?: return
        viewModelScope.launch {
            val inits = sessionRepository.spawnOfficialNpcToCombat(
                campaignId = campaign.id,
                npc = npc,
                count = count,
                existingParticipants = uiState.value.combatParticipants
            )
            sessionRepository.addJournalEntry(
                campaign = campaign,
                entryType = JournalEntryType.DETERMINISTIC_ROLL,
                speakerLabel = "Hostis em Combate",
                bodyText = "${inits.size}x ${npc.namePt} (${npc.armorLabelPt}, ${npc.hp} PV) entrou em combate.",
                deterministicDetail = "Iniciativas: ${inits.joinToString(", ")}"
            )
            setBanner("${inits.size}x ${npc.namePt} adicionado(s) ao combate.")
        }
    }

    fun rollStreetPrompt(kind: String) {
        val campaign = uiState.value.activeCampaign ?: return
        val promptText = when (kind) {
            "screamsheet" -> {
                val h = OfficialStreetGenerators.rollScreamsheetHeadline(part2DiceEngine)
                "Manchete Screamsheet: ${h.headlineTextPt}"
            }
            "vendit" -> {
                val v = OfficialStreetGenerators.rollVendit(part2DiceEngine)
                "${v.typePt}: ${v.contentsPt}"
            }
            else -> {
                val b = OfficialStreetGenerators.rollBodegaScene(part2DiceEngine)
                "Cena na Bodega (1d6=${b.roll1d6}): Caixa: ${b.cashierPt} | Cliente A: ${b.character1Pt} | Cliente B: ${b.character2Pt}"
            }
        }
        viewModelScope.launch {
            sessionRepository.addJournalEntry(
                campaign = campaign,
                entryType = JournalEntryType.SYSTEM_EVENT,
                speakerLabel = "Gerador de Rua",
                bodyText = promptText
            )
            transientResultsFlow.value = transientResultsFlow.value.copy(lastStreetPrompt = promptText)
        }
    }

    // --- Interface RED Vol 1-3: Weather, Salvaging, Datepath & Premade NETs ---
    fun rollWeather(season: WeatherSeason) {
        val campaign = uiState.value.activeCampaign ?: return
        val res = OfficialDlcSoloSystems.rollWeather(season, part2DiceEngine)
        viewModelScope.launch {
            val title = res.strangeConditionNamePt ?: res.conditionNamePt
            sessionRepository.addJournalEntry(
                campaign = campaign,
                entryType = JournalEntryType.DETERMINISTIC_ROLL,
                speakerLabel = "Clima (${season.labelPt})",
                bodyText = "$title — ${res.mechanicalEffectPt}",
                deterministicDetail = "1d6=${res.seasonRoll1d6}" + (res.strangeRoll1d10?.let { " | Clima Estranho 1d10=$it" } ?: "")
            )
            transientResultsFlow.value = transientResultsFlow.value.copy(lastWeatherResult = res)
            setBanner("Clima: $title")
        }
    }

    fun runSalvagingWeek(worthTier: SalvageWorthTier, specificCategory: SalvageCategory?) {
        val campaign = uiState.value.activeCampaign ?: return
        val char = uiState.value.primaryCharacter ?: return
        val basicTechLvl = uiState.value.skills.firstOrNull {
            it.name.contains("Tecnologia Básica", ignoreCase = true) || it.name.contains("Basic Tech", ignoreCase = true)
        }?.level ?: 0
        val base = char.technique + basicTechLvl
        val res = OfficialDlcSoloSystems.rollSalvageAttempt(
            basicTechBase = base,
            worthTier = worthTier,
            specificCategory = specificCategory,
            diceEngine = part2DiceEngine
        )
        viewModelScope.launch {
            campaignRepository.advanceTime(campaign, 7 * 24)
            val outcomeText = when {
                res.isCriticalFailure -> "FALHA CRÍTICA na sucata! ${res.perilTextPt}"
                res.succeeded -> "SUCESSO! Encontrou item [${res.category.labelPt}] (${res.worthTier.costEb}eb) [Destruído — exige reparo Tech]."
                else -> "Sem sucata aproveitável nesta semana (${res.rollTotal} vs DV ${res.dv})."
            }
            sessionRepository.addJournalEntry(
                campaign = campaign,
                entryType = JournalEntryType.DETERMINISTIC_ROLL,
                speakerLabel = "Salvaging (7 dias)",
                bodyText = outcomeText,
                deterministicDetail = "TEC+Téc.Básica($base) -> Total ${res.rollTotal} vs DV ${res.dv}"
            )
            if (res.succeeded) {
                val itemType = when (res.category) {
                    SalvageCategory.WEAPONS -> InventoryItemType.WEAPON
                    SalvageCategory.ARMOR -> InventoryItemType.ARMOR
                    SalvageCategory.CYBERDECKS_PROGRAMS -> InventoryItemType.CYBERDECK
                    else -> InventoryItemType.GEAR
                }
                sessionRepository.purchaseOrAddItem(
                    campaign = campaign,
                    name = "Sucata ${res.category.labelPt} (${res.worthTier.costEb}eb)",
                    itemType = itemType,
                    priceCategory = PriceCategory.entries.minByOrNull { kotlin.math.abs(it.referenceCostEb - res.worthTier.costEb) } ?: PriceCategory.COSTLY,
                    costEb = res.worthTier.costEb,
                    quantity = 1,
                    damageDice = 0,
                    stoppingPower = 0,
                    payWithEurodollars = false,
                    notes = "Salvaging Night City — Destruído (reparar com perícia Tech)"
                )
            }
            transientResultsFlow.value = transientResultsFlow.value.copy(lastSalvageResult = res)
            setBanner(outcomeText)
        }
    }

    fun rollDatepath() {
        val campaign = uiState.value.activeCampaign ?: return
        val res = OfficialDlcSoloSystems.rollDatepath(part2DiceEngine)
        viewModelScope.launch {
            sessionRepository.addJournalEntry(
                campaign = campaign,
                entryType = JournalEntryType.SYSTEM_EVENT,
                speakerLabel = "The Datepath",
                bodyText = "Match [${res.keyword1Pt} / ${res.keyword2Pt}] em ${res.locationNamePt}: ${res.activityPt}. Resultado: ${res.outcomeLabelPt}",
                deterministicDetail = "Reviravolta: ${res.twistPt} | Pós-encontro: ${res.aftermathPt}"
            )
            transientResultsFlow.value = transientResultsFlow.value.copy(lastDatepathResult = res)
            setBanner("Datepath: ${res.locationNamePt} — ${res.outcomeLabelPt}")
        }
    }

    fun deployPremadeNetArchitecture(spec: PremadeNetArchSpec) {
        val campaign = uiState.value.activeCampaign ?: return
        viewModelScope.launch {
            spec.floors.forEachIndexed { idx, floor ->
                sessionRepository.addNetNode(
                    campaignId = campaign.id,
                    architectureName = spec.namePt,
                    floorNumber = idx + 1,
                    nodeType = floor.nodeType,
                    title = "[${floor.floorLabel}] ${floor.title}",
                    dvOrRez = floor.dvOrRez
                )
            }
            sessionRepository.addJournalEntry(
                campaign = campaign,
                entryType = JournalEntryType.SYSTEM_EVENT,
                speakerLabel = "Arquitetura NET",
                bodyText = "Implantada arquitetura pronta \"${spec.namePt}\" (${spec.floors.size} nós, ${spec.totalCostEb}eb)."
            )
            setBanner("Arquitetura \"${spec.namePt}\" adicionada ao Netrunning.")
        }
    }

    // --- Black Chrome, Night City Hook Book & Tales of the RED+ ---
    fun rollPlotHook(districtFilterPt: String?) {
        val campaign = uiState.value.activeCampaign ?: return
        val hook = OfficialTalesAndHookBookCatalog.rollPlotHook(districtFilterPt, part2DiceEngine)
        viewModelScope.launch {
            sessionRepository.addJournalEntry(
                campaign = campaign,
                entryType = JournalEntryType.SYSTEM_EVENT,
                speakerLabel = "Hook Book (${hook.districtPt})",
                bodyText = "${hook.titlePt}: ${hook.summaryPt}"
            )
            transientResultsFlow.value = transientResultsFlow.value.copy(lastPlotHook = hook)
            setBanner("Gancho [${hook.districtPt}]: ${hook.titlePt}")
        }
    }

    fun rollRcNightMarketVendor() {
        val campaign = uiState.value.activeCampaign ?: return
        val vendor = OfficialTalesAndHookBookCatalog.rollRcNightMarketVendor(part2DiceEngine)
        viewModelScope.launch {
            sessionRepository.addJournalEntry(
                campaign = campaign,
                entryType = JournalEntryType.DETERMINISTIC_ROLL,
                speakerLabel = "RC Night Market (1d6)",
                bodyText = vendor
            )
            transientResultsFlow.value = transientResultsFlow.value.copy(lastRcVendorPrompt = vendor)
            setBanner("Vendedor RC rolado.")
        }
    }

    // --- Atlas, Going Quiet, Agents, Single Player+ & Danger Gal Dossier ---
    fun setCampaignDistrict(districtCode: String) {
        val campaign = uiState.value.activeCampaign ?: return
        val district = OfficialNightCityAtlasCatalog.findDistrictByCode(districtCode) ?: return
        viewModelScope.launch {
            sessionRepository.setCurrentDistrict(campaign, district.code)
            setBanner("Distrito atual: ${district.code} — ${district.namePt}")
        }
    }

    fun rollAtlasDistrictHook(districtCode: String) {
        val campaign = uiState.value.activeCampaign ?: return
        val district = OfficialNightCityAtlasCatalog.findDistrictByCode(districtCode) ?: return
        val location = district.locations.randomOrNull()
        val locText = location?.let {
            val extra = if (it.notePt.isNotBlank()) ": ${it.notePt}" else ""
            "${it.code} ${it.name}$extra"
        } ?: district.summaryPt
        viewModelScope.launch {
            sessionRepository.addJournalEntry(
                campaign = campaign,
                entryType = JournalEntryType.SYSTEM_EVENT,
                speakerLabel = "Atlas (${district.code} • ${district.namePt})",
                bodyText = "Local em foco: $locText",
                deterministicDetail = "Gangues ativas: ${district.gangsPresent.joinToString(", ")} | Segurança: ${district.securityProvider}"
            )
            setBanner("Gancho em ${district.code}: ${location?.name ?: district.namePt}")
        }
    }

    fun triggerFlashOfLuck(tier: FlashOfLuckTier, customReason: String) {
        val campaign = uiState.value.activeCampaign ?: return
        val char = uiState.value.primaryCharacter ?: return
        if (campaign.flashOfLuckBeatUsed) {
            setBanner("Limite de Beat atingido: já usou 1 Flash of Luck neste Beat.")
            return
        }
        if (campaign.flashOfLuckMissionUses >= 3) {
            setBanner("Limite de Missão atingido (3/3 Flashes usados).")
            return
        }
        if (char.luckCurrent < tier.luckCost) {
            setBanner("Sorte insuficiente: exige ${tier.luckCost} SOR (atual: ${char.luckCurrent}).")
            return
        }
        val reason = customReason.trim().ifEmpty { tier.labelPt }
        viewModelScope.launch {
            val newLuck = char.luckCurrent - tier.luckCost
            characterRepository.updateCharacter(char.copy(luckCurrent = newLuck))
            val updatedCamp = sessionRepository.updateCampaignFlashOfLuck(
                campaign = campaign,
                beatUsed = true,
                missionUses = campaign.flashOfLuckMissionUses + 1
            )
            sessionRepository.addJournalEntry(
                campaign = updatedCamp,
                entryType = JournalEntryType.DETERMINISTIC_ROLL,
                speakerLabel = "Flash of Luck (${tier.luckCost} SOR)",
                bodyText = "Going Quiet acionado (${tier.labelPt}): $reason",
                deterministicDetail = "SOR restante: $newLuck/${char.luckMax} • Beat: 1/1 • Missão: ${updatedCamp.flashOfLuckMissionUses}/3"
            )
            setBanner("Flash of Luck ativo (-${tier.luckCost} SOR). Beat 1/1 • Missão ${updatedCamp.flashOfLuckMissionUses}/3.")
        }
    }

    fun vetoRefundFlashOfLuck(tier: FlashOfLuckTier) {
        val campaign = uiState.value.activeCampaign ?: return
        val char = uiState.value.primaryCharacter ?: return
        if (!campaign.flashOfLuckBeatUsed && campaign.flashOfLuckMissionUses == 0) {
            setBanner("Nenhum Flash of Luck recente para reembolsar.")
            return
        }
        viewModelScope.launch {
            val refundedLuck = (char.luckCurrent + tier.luckCost).coerceAtMost(char.luckMax)
            characterRepository.updateCharacter(char.copy(luckCurrent = refundedLuck))
            val updatedCamp = sessionRepository.updateCampaignFlashOfLuck(
                campaign = campaign,
                beatUsed = false,
                missionUses = (campaign.flashOfLuckMissionUses - 1).coerceAtLeast(0)
            )
            sessionRepository.addJournalEntry(
                campaign = updatedCamp,
                entryType = JournalEntryType.SYSTEM_EVENT,
                speakerLabel = "Flash Vetado (Reembolso)",
                bodyText = "Oráculo/Mestre vetou Flash (${tier.labelPt}): +${tier.luckCost} SOR devolvida (atual: $refundedLuck)."
            )
            setBanner("Flash vetado: +${tier.luckCost} SOR reembolsada.")
        }
    }

    fun resetFlashOfLuckBeat() {
        val campaign = uiState.value.activeCampaign ?: return
        viewModelScope.launch {
            sessionRepository.updateCampaignFlashOfLuck(
                campaign = campaign,
                beatUsed = false,
                missionUses = campaign.flashOfLuckMissionUses
            )
            setBanner("Novo Beat iniciado: Flash of Luck liberado.")
        }
    }

    fun resetFlashOfLuckMission() {
        val campaign = uiState.value.activeCampaign ?: return
        viewModelScope.launch {
            sessionRepository.updateCampaignFlashOfLuck(
                campaign = campaign,
                beatUsed = false,
                missionUses = 0
            )
            setBanner("Nova Missão: contador Flash of Luck zerado (0/3).")
        }
    }

    fun runQuietlyJackIn(watcherInterface: Int) {
        val campaign = uiState.value.activeCampaign ?: return
        val char = uiState.value.primaryCharacter ?: return
        val interfaceRank = if (char.role == RedRole.NETRUNNER) char.roleRank else 1
        val res = netrunningEngine.quietlyJackIn(interfaceRank, watcherInterface)
        viewModelScope.launch {
            sessionRepository.addJournalEntry(
                campaign = campaign,
                entryType = JournalEntryType.DETERMINISTIC_ROLL,
                speakerLabel = "Stealth Netrunning",
                bodyText = "${res.actionLabelPt}: ${if (res.runnerStealthMaintained) "FURTIVO (Sem alerta)" else "DETECTADO pelo Watcher!"}",
                deterministicDetail = res.breakdownPt
            )
            transientResultsFlow.value = transientResultsFlow.value.copy(lastStealthNetResult = res)
        }
    }

    fun runStealthVsBlackIce(cloakBonus: Int, blackIcePerception: Int) {
        val campaign = uiState.value.activeCampaign ?: return
        val char = uiState.value.primaryCharacter ?: return
        val interfaceRank = if (char.role == RedRole.NETRUNNER) char.roleRank else 1
        val res = netrunningEngine.evadeBlackIceInStealth(interfaceRank, cloakBonus, blackIcePerception)
        viewModelScope.launch {
            sessionRepository.addJournalEntry(
                campaign = campaign,
                entryType = JournalEntryType.DETERMINISTIC_ROLL,
                speakerLabel = "Stealth Netrunning",
                bodyText = "${res.actionLabelPt}: ${if (res.runnerStealthMaintained) "IGNORADO pelo Black ICE" else "ICE ENTROU NA INICIATIVA!"}",
                deterministicDetail = res.breakdownPt
            )
            transientResultsFlow.value = transientResultsFlow.value.copy(lastStealthNetResult = res)
        }
    }

    fun runStealthVsWatcher(cloakBonus: Int, watcherInterface: Int, watcherPathfinder: Int) {
        val campaign = uiState.value.activeCampaign ?: return
        val char = uiState.value.primaryCharacter ?: return
        val interfaceRank = if (char.role == RedRole.NETRUNNER) char.roleRank else 1
        val res = netrunningEngine.contestWatcherVsStealth(interfaceRank, cloakBonus, watcherInterface, watcherPathfinder)
        viewModelScope.launch {
            sessionRepository.addJournalEntry(
                campaign = campaign,
                entryType = JournalEntryType.DETERMINISTIC_ROLL,
                speakerLabel = "Stealth Netrunning",
                bodyText = "${res.actionLabelPt}: ${if (res.runnerStealthMaintained) "OCULTO (Stealth mantido)" else "DESCOBERTO pelo Watcher!"}",
                deterministicDetail = res.breakdownPt
            )
            transientResultsFlow.value = transientResultsFlow.value.copy(lastStealthNetResult = res)
        }
    }

    fun runAgentHack(
        mode: AgentAccessMode,
        quality: AgentQualityTier,
        hasSocialBonus: Boolean,
        useCrunchWhistle: Boolean
    ) {
        val campaign = uiState.value.activeCampaign ?: return
        val char = uiState.value.primaryCharacter ?: return
        val elecSkill = uiState.value.skills.firstOrNull {
            it.name.contains("Eletrônica", ignoreCase = true) || it.name.contains("Electronics", ignoreCase = true)
        }?.level ?: 0
        val elecBase = char.technique + elecSkill
        val crunchBonus = if (useCrunchWhistle && char.role == RedRole.NETRUNNER) char.roleRank else 0
        val res = netrunningEngine.hackAgent(elecBase, mode, quality, hasSocialBonus, crunchBonus)
        viewModelScope.launch {
            sessionRepository.addJournalEntry(
                campaign = campaign,
                entryType = JournalEntryType.DETERMINISTIC_ROLL,
                speakerLabel = "Agent Hacking (${quality.labelPt})",
                bodyText = if (res.succeeded) "AGENT INVADIDO (1 comando por até 24h)" else "INVASÃO FALHOU (alvo alertado em 30s!)",
                deterministicDetail = res.breakdownPt
            )
            transientResultsFlow.value = transientResultsFlow.value.copy(lastAgentHackResult = res)
        }
    }

    fun runHusToolApp() {
        val campaign = uiState.value.activeCampaign ?: return
        val res = netrunningEngine.rollHusTool()
        viewModelScope.launch {
            if (res.earnedEb > 0) {
                campaignRepository.updateCampaign(campaign.copy(eurodollars = campaign.eurodollars + res.earnedEb))
            }
            sessionRepository.addJournalEntry(
                campaign = campaign,
                entryType = JournalEntryType.DETERMINISTIC_ROLL,
                speakerLabel = "App: HusTool",
                bodyText = res.summaryPt
            )
            transientResultsFlow.value = transientResultsFlow.value.copy(lastHusToolResult = res)
            setBanner(res.summaryPt)
        }
    }

    fun runBrainwasherApp() {
        val campaign = uiState.value.activeCampaign ?: return
        val char = uiState.value.primaryCharacter
        val res = netrunningEngine.rollBrainwasher()
        viewModelScope.launch {
            if (char != null) {
                characterRepository.updateCharacter(
                    char.copy(pendingTherapyDiceDelta = res.therapyDiceDelta)
                )
            }
            sessionRepository.addJournalEntry(
                campaign = campaign,
                entryType = JournalEntryType.DETERMINISTIC_ROLL,
                speakerLabel = "App: Brainwasher",
                bodyText = res.summaryPt
            )
            transientResultsFlow.value = transientResultsFlow.value.copy(lastBrainwasherResult = res)
            setBanner(res.summaryPt)
        }
    }

    fun runHumanityTherapy(
        therapyType: TherapyType,
        useClinicService: Boolean,
        payWithEb: Boolean
    ) {
        val campaign = uiState.value.activeCampaign ?: return
        val char = uiState.value.primaryCharacter ?: return
        val cost = if (useClinicService) therapyType.clinicCostEb else therapyType.selfMaterialsEb

        viewModelScope.launch {
            if (payWithEb && cost > 0) {
                when (val purchase = EconomyEngine.attemptPurchase(campaign.eurodollars, cost)) {
                    is PurchaseOutcome.InsufficientFunds -> {
                        setBanner("Eurodólares insuficientes para ${therapyType.labelPt}: precisa ${purchase.requiredEurodollars} eb (saldo: ${purchase.currentEurodollars} eb).")
                        return@launch
                    }
                    is PurchaseOutcome.Approved -> {
                        campaignRepository.updateCampaign(
                            campaign.copy(
                                eurodollars = purchase.remainingEurodollars,
                                updatedAt = System.currentTimeMillis()
                            )
                        )
                    }
                }
            }

            val medTechSkill = uiState.value.skills.firstOrNull {
                it.name.contains("Tecnologia Médica", ignoreCase = true) ||
                    it.name.contains("Medical Tech", ignoreCase = true)
            }?.level ?: 0
            val medTechBase = char.technique + medTechSkill

            val outcome = characterRepository.performTherapy(
                character = char,
                therapyType = therapyType,
                useClinicService = useClinicService,
                medTechBase = medTechBase,
                diceEngine = part2DiceEngine
            )

            campaignRepository.advanceTime(campaign.copy(eurodollars = if (payWithEb) (campaign.eurodollars - cost).coerceAtLeast(0) else campaign.eurodollars), 7 * 24)

            sessionRepository.addJournalEntry(
                campaign = campaign,
                entryType = JournalEntryType.DETERMINISTIC_ROLL,
                speakerLabel = "Terapia (${if (useClinicService) "Clínica" else "Medtech"})",
                bodyText = outcome.summaryPt,
                deterministicDetail = "Custo: ${if (payWithEb) "${cost}eb" else "0eb (cortesia)"} • 7 dias avançados"
            )
            transientResultsFlow.value = transientResultsFlow.value.copy(lastTherapyOutcome = outcome)
            setBanner(outcome.summaryPt)
        }
    }

    fun rollSoloPlusTable(tableId: String) {
        val campaign = uiState.value.activeCampaign ?: return
        val res = OfficialSinglePlayerPlusCatalog.rollTable(tableId, part2DiceEngine)
        viewModelScope.launch {
            sessionRepository.addJournalEntry(
                campaign = campaign,
                entryType = JournalEntryType.DETERMINISTIC_ROLL,
                speakerLabel = "Solo+ • ${res.tableTitlePt}",
                bodyText = res.resultTextPt,
                deterministicDetail = "${res.dieLabel} = ${res.rollValue}"
            )
            transientResultsFlow.value = transientResultsFlow.value.copy(lastSoloPlusRoll = res)
        }
    }

    fun rollSoloNpcPersonality() {
        val campaign = uiState.value.activeCampaign ?: return
        val bundle = OfficialSinglePlayerPlusCatalog.rollNpcPersonalityBundle(part2DiceEngine)
        viewModelScope.launch {
            sessionRepository.addJournalEntry(
                campaign = campaign,
                entryType = JournalEntryType.DETERMINISTIC_ROLL,
                speakerLabel = "Solo+ • Perfil NPC",
                bodyText = "Motivação (${bundle.motivationRoll}): ${bundle.motivationPt}\nCompetência (${bundle.competenceRoll}): ${bundle.competencePt}\nAgressividade (${bundle.aggressionRoll}): ${bundle.aggressionPt}"
            )
            transientResultsFlow.value = transientResultsFlow.value.copy(lastNpcPersonalityBundle = bundle)
        }
    }

    fun askSoloOracleYesNo(question: String, oddsLabel: String, targetDv: Int) {
        val campaign = uiState.value.activeCampaign ?: return
        val res = OfficialSinglePlayerPlusCatalog.askSoloOracleYesNo(question, oddsLabel, targetDv, part2DiceEngine)
        viewModelScope.launch {
            sessionRepository.addJournalEntry(
                campaign = campaign,
                entryType = JournalEntryType.DETERMINISTIC_ROLL,
                speakerLabel = "Oráculo Solo (${res.oddsLabelPt})",
                bodyText = "\"${res.questionPt}\" -> ${res.answerPt} (1d10=${res.roll1d10} vs DV ${res.targetDv})" +
                    (res.twistPt?.let { "\n$it" } ?: "")
            )
            transientResultsFlow.value = transientResultsFlow.value.copy(lastSoloOracleResult = res)
        }
    }

    fun deployTheIncidentGig() {
        val campaign = uiState.value.activeCampaign ?: return
        viewModelScope.launch {
            sessionRepository.deployTheIncidentAdventure(campaign)
            setBanner("Gig 'The Incident' implantada: 10 Beats, NPCs e Bodega NET!")
        }
    }

    fun importDgdFactionPack(factionId: String) {
        val campaign = uiState.value.activeCampaign ?: return
        viewModelScope.launch {
            val count = sessionRepository.importDgdFaction(campaign, factionId)
            setBanner("Facção DGD e $count NPCs importados para a campanha.")
        }
    }

    // --- 14. Rule PDF Library ---
    fun registerPdf(uri: Uri) {
        viewModelScope.launch {
            ruleLibraryRepository.registerPdfDocument(uri)
            setBanner("PDF oficial registrado na biblioteca local.")
        }
    }

    fun addRuleExcerpt(documentId: Long, title: String, pageRef: String, content: String) {
        if (title.isBlank() || content.isBlank()) return
        viewModelScope.launch {
            ruleLibraryRepository.addRuleExcerpt(documentId, title, pageRef, content)
            setBanner("Trecho de regra indexado para consulta.")
        }
    }

    fun deleteRuleDocument(docId: Long) {
        viewModelScope.launch { ruleLibraryRepository.deleteDocument(docId) }
    }

    fun updateRuleSearchQuery(query: String) {
        ruleQueryFlow.value = query
    }

    private fun setBanner(message: String) {
        transientResultsFlow.value = transientResultsFlow.value.copy(statusBannerMessage = message)
    }

    private fun buildContextSnapshot(state: MainUiState): GmContextSnapshot {
        val campaign = state.activeCampaign
        val char = state.primaryCharacter
        val derived = state.derivedMetrics
        return GmContextSnapshot(
            campaignTitle = campaign?.title ?: "Sem Campanha",
            inGameDay = campaign?.inGameDay ?: 1,
            inGameHour = campaign?.inGameHour ?: 20,
            edgerunnerHandle = char?.handle ?: "Edgerunner",
            edgerunnerRole = char?.role ?: RedRole.SOLO,
            currentHp = char?.currentHp ?: 0,
            maxHp = derived?.maxHp ?: 0,
            woundState = derived?.woundState ?: com.example.core.rules.WoundState.UNINJURED,
            currentHumanity = derived?.currentHumanity ?: 0,
            eurodollars = campaign?.eurodollars ?: 0,
            activeMissionTitle = state.missions.firstOrNull { it.status == MissionStatus.ACTIVE }?.title,
            recentJournalLines = state.journal.take(5).map { "${it.speakerLabel}: ${it.bodyText}" },
            referencedRuleSnippets = state.ruleSections.take(3).map { "${it.sectionTitle} (${it.pageRef})" }
        )
    }
}
