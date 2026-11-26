package com.example.data.local.entity

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "combat_participants",
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
data class CombatParticipantEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val campaignId: Long,
    val name: String,
    val isPlayer: Boolean,
    val reflexes: Int,
    val initiativeTotal: Int,
    val currentHp: Int,
    val maxHp: Int,
    val armorSp: Int,
    val isActiveTurn: Boolean = false,
    val roundNumber: Int = 1
)
