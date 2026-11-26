package com.example.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Update
import com.example.data.local.entity.CampaignEntity
import com.example.data.local.entity.CampaignSaveEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface CampaignDao {
    @Query("SELECT * FROM campaigns ORDER BY isActive DESC, updatedAt DESC")
    fun observeAllCampaigns(): Flow<List<CampaignEntity>>

    @Query("SELECT * FROM campaigns WHERE isActive = 1 LIMIT 1")
    fun observeActiveCampaign(): Flow<CampaignEntity?>

    @Query("SELECT * FROM campaigns WHERE id = :id LIMIT 1")
    suspend fun getCampaignById(id: Long): CampaignEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCampaign(campaign: CampaignEntity): Long

    @Update
    suspend fun updateCampaign(campaign: CampaignEntity)

    @Query("UPDATE campaigns SET isActive = 0")
    suspend fun clearAllActiveFlags()

    @Query("UPDATE campaigns SET isActive = 1, updatedAt = :timestamp WHERE id = :campaignId")
    suspend fun markActive(campaignId: Long, timestamp: Long = System.currentTimeMillis())

    @Transaction
    suspend fun setActiveCampaign(campaignId: Long) {
        clearAllActiveFlags()
        markActive(campaignId)
    }

    @Query("DELETE FROM campaigns WHERE id = :campaignId")
    suspend fun deleteCampaignById(campaignId: Long)

    @Query("SELECT id FROM campaigns ORDER BY updatedAt DESC LIMIT 1")
    suspend fun getMostRecentCampaignId(): Long?

    @Query("SELECT * FROM campaign_saves WHERE campaignId = :campaignId ORDER BY createdAt DESC")
    fun observeSavesForCampaign(campaignId: Long): Flow<List<CampaignSaveEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertSaveSnapshot(save: CampaignSaveEntity): Long

    @Query("DELETE FROM campaign_saves WHERE id = :saveId")
    suspend fun deleteSaveById(saveId: Long)
}
