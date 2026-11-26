package com.example.data.repository

import com.example.core.rules.CharacterCalculator
import com.example.core.rules.OfficialSkillCatalog
import com.example.core.rules.RedRole
import com.example.core.rules.RedStat
import com.example.data.local.dao.CampaignDao
import com.example.data.local.dao.CharacterDao
import com.example.data.local.dao.SystemsDao
import com.example.data.local.entity.CampaignEntity
import com.example.data.local.entity.CampaignSaveEntity
import com.example.data.local.entity.CharacterEntity
import com.example.data.local.entity.CharacterSkillEntity
import com.example.data.local.entity.JournalEntryEntity
import com.example.data.local.entity.JournalEntryType
import com.squareup.moshi.JsonClass
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import kotlinx.coroutines.flow.Flow

@JsonClass(generateAdapter = true)
data class CampaignSnapshotPayload(
    val campaignTitle: String,
    val inGameDay: Int,
    val inGameHour: Int,
    val rentDueInDays: Int,
    val eurodollars: Int,
    val characterId: Long?,
    val characterHandle: String?,
    val currentHp: Int?,
    val luckCurrent: Int?,
    val humanityLoss: Int?,
    val headArmorSp: Int?,
    val bodyArmorSp: Int?,
    val improvementPoints: Int? = null
)

class CampaignRepository(
    private val campaignDao: CampaignDao,
    private val characterDao: CharacterDao,
    private val systemsDao: SystemsDao
) {
    private val moshi: Moshi = Moshi.Builder()
        .addLast(KotlinJsonAdapterFactory())
        .build()
    private val snapshotAdapter = moshi.adapter(CampaignSnapshotPayload::class.java)

    val allCampaigns: Flow<List<CampaignEntity>> = campaignDao.observeAllCampaigns()
    val activeCampaign: Flow<CampaignEntity?> = campaignDao.observeActiveCampaign()

    fun observeSaves(campaignId: Long): Flow<List<CampaignSaveEntity>> =
        campaignDao.observeSavesForCampaign(campaignId)

    suspend fun createCampaignWithEdgerunner(
        campaignTitle: String,
        settingNotes: String,
        edgerunnerHandle: String,
        role: RedRole,
        startingEurodollars: Int = 2550
    ): Long {
        campaignDao.clearAllActiveFlags()
        val campaignId = campaignDao.insertCampaign(
            CampaignEntity(
                title = campaignTitle.trim(),
                settingNotes = settingNotes.trim().ifEmpty { "Night City — Era do Vermelho (2045)" },
                inGameDay = 1,
                inGameHour = 20,
                eurodollars = startingEurodollars.coerceAtLeast(0),
                isActive = true
            )
        )

        val defaultStats = defaultStatsForRole(role)
        val maxHp = CharacterCalculator.calculateMaxHp(defaultStats.body, defaultStats.willpower)
        val characterId = characterDao.insertCharacter(
            CharacterEntity(
                campaignId = campaignId,
                handle = edgerunnerHandle.trim(),
                role = role,
                roleRank = 4,
                intelligence = defaultStats.intelligence,
                reflexes = defaultStats.reflexes,
                dexterity = defaultStats.dexterity,
                technique = defaultStats.technique,
                cool = defaultStats.cool,
                willpower = defaultStats.willpower,
                luckMax = defaultStats.luckMax,
                luckCurrent = defaultStats.luckMax,
                move = defaultStats.move,
                body = defaultStats.body,
                empathyMax = defaultStats.empathyMax,
                currentHp = maxHp,
                headArmorSp = 11,
                bodyArmorSp = 11,
                isPrimary = true
            )
        )

        characterDao.insertSkills(defaultCoreSkills(characterId, role))

        systemsDao.insertJournalEntry(
            JournalEntryEntity(
                campaignId = campaignId,
                entryType = JournalEntryType.SYSTEM_EVENT,
                speakerLabel = "Sistema",
                bodyText = "Campanha \"${campaignTitle.trim()}\" iniciada. Operativo ${edgerunnerHandle.trim()} (${role.displayNamePt}) registrado com $maxHp PV.",
                inGameTimestamp = "Dia 1 • 20:00"
            )
        )

        return campaignId
    }

    suspend fun setActiveCampaign(campaignId: Long) {
        campaignDao.setActiveCampaign(campaignId)
    }

    suspend fun deleteCampaign(campaignId: Long) {
        val wasActive = campaignDao.getCampaignById(campaignId)?.isActive == true
        campaignDao.deleteCampaignById(campaignId)
        if (wasActive) {
            campaignDao.getMostRecentCampaignId()?.let { nextId ->
                campaignDao.setActiveCampaign(nextId)
            }
        }
    }

    suspend fun updateCampaign(campaign: CampaignEntity) {
        campaignDao.updateCampaign(campaign.copy(updatedAt = System.currentTimeMillis()))
    }

    suspend fun advanceTime(campaign: CampaignEntity, hoursToAdd: Int) {
        if (hoursToAdd <= 0) return
        val totalHours = campaign.inGameHour + hoursToAdd
        val daysAdvanced = totalHours / 24
        val newHour = totalHours % 24
        val newDay = campaign.inGameDay + daysAdvanced
        val newRentDays = (campaign.rentDueInDays - daysAdvanced).coerceAtLeast(0)

        campaignDao.updateCampaign(
            campaign.copy(
                inGameDay = newDay,
                inGameHour = newHour,
                rentDueInDays = newRentDays,
                updatedAt = System.currentTimeMillis()
            )
        )

        systemsDao.insertJournalEntry(
            JournalEntryEntity(
                campaignId = campaign.id,
                entryType = JournalEntryType.SYSTEM_EVENT,
                speakerLabel = "Relógio",
                bodyText = "Tempo avançou +${hoursToAdd}h. Novo horário: Dia $newDay, ${newHour.toString().padStart(2, '0')}:00.",
                inGameTimestamp = "Dia $newDay • ${newHour.toString().padStart(2, '0')}:00"
            )
        )
    }

    suspend fun createSaveSnapshot(
        campaign: CampaignEntity,
        primaryCharacter: CharacterEntity?,
        label: String
    ) {
        val payload = CampaignSnapshotPayload(
            campaignTitle = campaign.title,
            inGameDay = campaign.inGameDay,
            inGameHour = campaign.inGameHour,
            rentDueInDays = campaign.rentDueInDays,
            eurodollars = campaign.eurodollars,
            characterId = primaryCharacter?.id,
            characterHandle = primaryCharacter?.handle,
            currentHp = primaryCharacter?.currentHp,
            luckCurrent = primaryCharacter?.luckCurrent,
            humanityLoss = primaryCharacter?.humanityLoss,
            headArmorSp = primaryCharacter?.headArmorSp,
            bodyArmorSp = primaryCharacter?.bodyArmorSp,
            improvementPoints = primaryCharacter?.improvementPoints
        )
        val json = snapshotAdapter.toJson(payload)
        campaignDao.insertSaveSnapshot(
            CampaignSaveEntity(
                campaignId = campaign.id,
                label = label.trim().ifEmpty { "Save Dia ${campaign.inGameDay}" },
                snapshotJson = json
            )
        )
    }

    suspend fun restoreSaveSnapshot(save: CampaignSaveEntity): Boolean {
        val payload = snapshotAdapter.fromJson(save.snapshotJson) ?: return false
        val campaign = campaignDao.getCampaignById(save.campaignId) ?: return false

        campaignDao.updateCampaign(
            campaign.copy(
                inGameDay = payload.inGameDay,
                inGameHour = payload.inGameHour,
                rentDueInDays = payload.rentDueInDays,
                eurodollars = payload.eurodollars,
                updatedAt = System.currentTimeMillis()
            )
        )

        if (payload.characterId != null) {
            val character = characterDao.getCharacterById(payload.characterId)
            if (character != null) {
                characterDao.updateCharacter(
                    character.copy(
                        currentHp = payload.currentHp ?: character.currentHp,
                        luckCurrent = payload.luckCurrent ?: character.luckCurrent,
                        humanityLoss = payload.humanityLoss ?: character.humanityLoss,
                        headArmorSp = payload.headArmorSp ?: character.headArmorSp,
                        bodyArmorSp = payload.bodyArmorSp ?: character.bodyArmorSp,
                        improvementPoints = payload.improvementPoints ?: character.improvementPoints,
                        updatedAt = System.currentTimeMillis()
                    )
                )
            }
        }

        systemsDao.insertJournalEntry(
            JournalEntryEntity(
                campaignId = campaign.id,
                entryType = JournalEntryType.SYSTEM_EVENT,
                speakerLabel = "Backup",
                bodyText = "Estado restaurado a partir do snapshot \"${save.label}\".",
                inGameTimestamp = "Dia ${payload.inGameDay} • ${payload.inGameHour.toString().padStart(2, '0')}:00"
            )
        )
        return true
    }

    suspend fun deleteSave(saveId: Long) {
        campaignDao.deleteSaveById(saveId)
    }

    private fun defaultStatsForRole(role: RedRole) =
        OfficialSkillCatalog.streetratStats(role, 1)

    private fun defaultCoreSkills(characterId: Long, role: RedRole): List<CharacterSkillEntity> {
        val byName = OfficialSkillCatalog.allSkills.associateBy { it.namePt }
        return OfficialSkillCatalog.streetratSkillTemplate(role).map { (skillName, level) ->
            val def = byName[skillName]
            CharacterSkillEntity(
                characterId = characterId,
                name = skillName,
                stat = def?.stat ?: RedStat.INT,
                level = level,
                category = def?.categoryPt ?: "Geral"
            )
        }
    }
}
