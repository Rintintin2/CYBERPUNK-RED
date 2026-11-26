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
        assertEquals("Redline Solo", appName)
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
}
