package com.example.data.local.entity

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey
import com.example.core.rules.CharacterStats
import com.example.core.rules.RedRole

@Entity(
    tableName = "characters",
    foreignKeys = [
        ForeignKey(
            entity = CampaignEntity::class,
            parentColumns = ["id"],
            childColumns = ["campaignId"],
            onDelete = ForeignKey.CASCADE
        )
    ],
    indices = [Index("campaignId")]
)
data class CharacterEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val campaignId: Long,
    val handle: String,
    val role: RedRole,
    val roleRank: Int = 4,
    val intelligence: Int = 6,
    val reflexes: Int = 7,
    val dexterity: Int = 6,
    val technique: Int = 5,
    val cool: Int = 6,
    val willpower: Int = 6,
    val luckMax: Int = 6,
    val luckCurrent: Int = 6,
    val move: Int = 6,
    val body: Int = 6,
    val empathyMax: Int = 6,
    val currentHp: Int = 40,
    val humanityLoss: Int = 0,
    val deathSaveMod: Int = 0,
    val headArmorSp: Int = 11,
    val bodyArmorSp: Int = 11,
    val reputation: Int = 0,
    val improvementPoints: Int = 0,
    val isPrimary: Boolean = true,
    val notes: String = "",
    val updatedAt: Long = System.currentTimeMillis()
) {
    fun toCharacterStats(): CharacterStats = CharacterStats(
        intelligence = intelligence,
        reflexes = reflexes,
        dexterity = dexterity,
        technique = technique,
        cool = cool,
        willpower = willpower,
        luckMax = luckMax,
        move = move,
        body = body,
        empathyMax = empathyMax
    )
}
