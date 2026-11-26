package com.example.data.local.entity

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey

enum class JournalEntryType(val labelPt: String) {
    PLAYER_ACTION("Ação"),
    NARRATOR_GM("Mestre"),
    DETERMINISTIC_ROLL("Rolagem"),
    SYSTEM_EVENT("Sistema")
}

@Entity(
    tableName = "journal_entries",
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
data class JournalEntryEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val campaignId: Long,
    val entryType: JournalEntryType,
    val speakerLabel: String,
    val bodyText: String,
    val deterministicDetail: String? = null,
    val inGameTimestamp: String,
    val createdAt: Long = System.currentTimeMillis()
)
