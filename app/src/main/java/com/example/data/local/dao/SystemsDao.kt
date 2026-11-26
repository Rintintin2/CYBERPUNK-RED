package com.example.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.data.local.entity.CombatParticipantEntity
import com.example.data.local.entity.InventoryItemEntity
import com.example.data.local.entity.JournalEntryEntity
import com.example.data.local.entity.MissionEntity
import com.example.data.local.entity.NetNodeEntity
import com.example.data.local.entity.NpcFactionEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface SystemsDao {
    // Inventory & Economy
    @Query("SELECT * FROM inventory_items WHERE campaignId = :campaignId ORDER BY isEquipped DESC, itemType ASC, name ASC")
    fun observeInventory(campaignId: Long): Flow<List<InventoryItemEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertInventoryItem(item: InventoryItemEntity): Long

    @Update
    suspend fun updateInventoryItem(item: InventoryItemEntity)

    @Query("DELETE FROM inventory_items WHERE id = :itemId")
    suspend fun deleteInventoryItemById(itemId: Long)

    // Netrunning
    @Query("SELECT * FROM net_nodes WHERE campaignId = :campaignId ORDER BY architectureName ASC, floorNumber ASC")
    fun observeNetNodes(campaignId: Long): Flow<List<NetNodeEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertNetNode(node: NetNodeEntity): Long

    @Update
    suspend fun updateNetNode(node: NetNodeEntity)

    @Query("DELETE FROM net_nodes WHERE id = :nodeId")
    suspend fun deleteNetNodeById(nodeId: Long)

    // NPCs & Factions
    @Query("SELECT * FROM npcs_factions WHERE campaignId = :campaignId ORDER BY isFaction ASC, name ASC")
    fun observeNpcsAndFactions(campaignId: Long): Flow<List<NpcFactionEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertNpcFaction(entity: NpcFactionEntity): Long

    @Update
    suspend fun updateNpcFaction(entity: NpcFactionEntity)

    @Query("DELETE FROM npcs_factions WHERE id = :id")
    suspend fun deleteNpcFactionById(id: Long)

    // Missions
    @Query("SELECT * FROM missions WHERE campaignId = :campaignId ORDER BY status ASC, id DESC")
    fun observeMissions(campaignId: Long): Flow<List<MissionEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMission(mission: MissionEntity): Long

    @Update
    suspend fun updateMission(mission: MissionEntity)

    @Query("DELETE FROM missions WHERE id = :id")
    suspend fun deleteMissionById(id: Long)

    // Combat Encounter
    @Query("SELECT * FROM combat_participants WHERE campaignId = :campaignId ORDER BY initiativeTotal DESC, reflexes DESC")
    fun observeCombatParticipants(campaignId: Long): Flow<List<CombatParticipantEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCombatParticipant(participant: CombatParticipantEntity): Long

    @Update
    suspend fun updateCombatParticipant(participant: CombatParticipantEntity)

    @Update
    suspend fun updateCombatParticipants(participants: List<CombatParticipantEntity>)

    @Query("DELETE FROM combat_participants WHERE id = :id")
    suspend fun deleteCombatParticipantById(id: Long)

    @Query("DELETE FROM combat_participants WHERE campaignId = :campaignId")
    suspend fun clearCombatForCampaign(campaignId: Long)

    // Journal
    @Query("SELECT * FROM journal_entries WHERE campaignId = :campaignId ORDER BY createdAt DESC LIMIT 100")
    fun observeJournal(campaignId: Long): Flow<List<JournalEntryEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertJournalEntry(entry: JournalEntryEntity): Long
}
