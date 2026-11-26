package com.example.data.local.entity

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey

enum class MissionStatus(val labelPt: String) {
    ACTIVE("Ativa"),
    COMPLETED("Concluída"),
    FAILED("Falhou")
}

@Entity(
    tableName = "missions",
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
data class MissionEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val campaignId: Long,
    val title: String,
    val fixerName: String = "",
    val rewardEb: Int = 500,
    val status: MissionStatus = MissionStatus.ACTIVE,
    val objectivesSummary: String = "",
    val notes: String = ""
)
