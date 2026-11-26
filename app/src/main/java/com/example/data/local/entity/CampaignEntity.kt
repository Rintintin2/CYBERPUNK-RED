package com.example.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "campaigns")
data class CampaignEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val title: String,
    val settingNotes: String = "Night City — Era do Vermelho (2045)",
    val inGameDay: Int = 1,
    val inGameHour: Int = 20,
    val lifestyleLabel: String = "Kibble",
    val rentDueInDays: Int = 30,
    val eurodollars: Int = 2550,
    val isActive: Boolean = false,
    val updatedAt: Long = System.currentTimeMillis()
)
