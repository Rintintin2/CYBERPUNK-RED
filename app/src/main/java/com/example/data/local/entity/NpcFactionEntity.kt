package com.example.data.local.entity

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "npcs_factions",
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
data class NpcFactionEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val campaignId: Long,
    val name: String,
    val isFaction: Boolean = false,
    val affiliation: String = "Independente",
    val attitude: String = "Neutro",
    val isContact: Boolean = true,
    val notes: String = ""
)
