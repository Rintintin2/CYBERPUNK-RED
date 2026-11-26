package com.example

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import com.example.core.rules.RedRole
import com.example.data.local.RedlineDatabase
import com.example.data.repository.CampaignRepository
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

@RunWith(RobolectricTestRunner::class)
@Config(sdk = [36])
class ExampleRobolectricTest {

    private lateinit var db: RedlineDatabase
    private lateinit var campaignRepository: CampaignRepository

    @Before
    fun setup() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        db = Room.inMemoryDatabaseBuilder(context, RedlineDatabase::class.java)
            .allowMainThreadQueries()
            .build()
        campaignRepository = CampaignRepository(
            campaignDao = db.campaignDao(),
            characterDao = db.characterDao(),
            systemsDao = db.systemsDao()
        )
    }

    @After
    fun teardown() {
        db.close()
    }

    @Test
    fun `read app name from context`() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        val appName = context.getString(R.string.app_name)
        assertEquals("CYBERPUNK RED", appName)
    }

    @Test
    fun `create campaign creates edgerunner and snapshot restores state`() = runTest {
        val campaignId = campaignRepository.createCampaignWithEdgerunner(
            campaignTitle = "Operação Kabuki",
            settingNotes = "Night City",
            edgerunnerHandle = "Kira",
            role = RedRole.NETRUNNER,
            startingEurodollars = 2550
        )

        val active = campaignRepository.activeCampaign.first()
        assertNotNull(active)
        assertEquals(campaignId, active?.id)
        assertEquals(2550, active?.eurodollars)

        val primaryChar = db.characterDao().observePrimaryCharacter(campaignId).first()
        assertNotNull(primaryChar)
        assertEquals("Kira", primaryChar?.handle)

        campaignRepository.createSaveSnapshot(active!!, primaryChar, "Antes da Invasão")
        campaignRepository.advanceTime(active, 6)

        val afterAdvance = db.campaignDao().getCampaignById(campaignId)!!
        assertEquals(2, afterAdvance.inGameDay)
        assertEquals(2, afterAdvance.inGameHour)

        val saves = campaignRepository.observeSaves(campaignId).first()
        assertTrue(saves.isNotEmpty())
        val restored = campaignRepository.restoreSaveSnapshot(saves.first())
        assertTrue(restored)

        val restoredCampaign = db.campaignDao().getCampaignById(campaignId)!!
        assertEquals(1, restoredCampaign.inGameDay)
        assertEquals(20, restoredCampaign.inGameHour)
    }

    @Test
    fun `pregen campaign creation atlas district incident deploy and dgd faction import persist in room`() = runTest {
        val pregen = com.example.core.rules.OfficialStreetratPregensCatalog.allPregens.first()
        val campaignId = campaignRepository.createCampaignFromPregen(
            campaignTitle = "Incident em Japantown",
            settingNotes = "Night City",
            pregen = pregen
        )
        val campaign = db.campaignDao().getCampaignById(campaignId)!!
        assertEquals("E", campaign.currentDistrictCode)
        assertEquals(pregen.startingCashEb, campaign.eurodollars)

        val skills = db.characterDao().observeSkills(
            db.characterDao().observePrimaryCharacter(campaignId).first()!!.id
        ).first()
        assertEquals(20, skills.size)

        val diceEngine = com.example.core.rules.DiceEngine()
        val sessionRepo = com.example.data.repository.SessionRepository(
            systemsDao = db.systemsDao(),
            campaignDao = db.campaignDao(),
            skillCheckEngine = com.example.core.rules.SkillCheckEngine(diceEngine),
            netrunningEngine = com.example.core.rules.NetrunningEngine(diceEngine)
        )
        sessionRepo.setCurrentDistrict(campaign, "H")
        assertEquals("H", db.campaignDao().getCampaignById(campaignId)!!.currentDistrictCode)

        sessionRepo.deployTheIncidentAdventure(campaign)
        assertTrue(db.systemsDao().observeMissions(campaignId).first().any { it.title.contains("The Incident") })
        assertEquals(5, db.systemsDao().observeNetNodes(campaignId).first().size)

        val importedNpcs = sessionRepo.importDgdFaction(campaign, "danger_gal")
        assertTrue(importedNpcs > 0)
        assertTrue(db.systemsDao().observeNpcsAndFactions(campaignId).first().any { it.isFaction })
    }
}
