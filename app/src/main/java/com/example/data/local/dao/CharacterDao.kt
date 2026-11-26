package com.example.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.data.local.entity.CharacterEntity
import com.example.data.local.entity.CharacterSkillEntity
import com.example.data.local.entity.CyberwareEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface CharacterDao {
    @Query("SELECT * FROM characters WHERE campaignId = :campaignId ORDER BY isPrimary DESC, updatedAt DESC")
    fun observeCharactersForCampaign(campaignId: Long): Flow<List<CharacterEntity>>

    @Query("SELECT * FROM characters WHERE campaignId = :campaignId ORDER BY isPrimary DESC, id ASC LIMIT 1")
    fun observePrimaryCharacter(campaignId: Long): Flow<CharacterEntity?>

    @Query("SELECT * FROM characters WHERE id = :id LIMIT 1")
    suspend fun getCharacterById(id: Long): CharacterEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCharacter(character: CharacterEntity): Long

    @Update
    suspend fun updateCharacter(character: CharacterEntity)

    @Query("DELETE FROM characters WHERE id = :characterId")
    suspend fun deleteCharacterById(characterId: Long)

    @Query("SELECT * FROM character_skills WHERE characterId = :characterId ORDER BY category ASC, name ASC")
    fun observeSkills(characterId: Long): Flow<List<CharacterSkillEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertSkill(skill: CharacterSkillEntity): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertSkills(skills: List<CharacterSkillEntity>)

    @Update
    suspend fun updateSkill(skill: CharacterSkillEntity)

    @Query("DELETE FROM character_skills WHERE id = :skillId")
    suspend fun deleteSkillById(skillId: Long)

    @Query("SELECT * FROM cyberware WHERE characterId = :characterId ORDER BY slotArea ASC, name ASC")
    fun observeCyberware(characterId: Long): Flow<List<CyberwareEntity>>

    @Query("SELECT * FROM cyberware WHERE characterId = :characterId ORDER BY slotArea ASC, name ASC")
    suspend fun getCyberwareForCharacter(characterId: Long): List<CyberwareEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCyberware(cyberware: CyberwareEntity): Long

    @Update
    suspend fun updateCyberware(cyberware: CyberwareEntity)

    @Query("DELETE FROM cyberware WHERE id = :cyberwareId")
    suspend fun deleteCyberwareById(cyberwareId: Long)
}
