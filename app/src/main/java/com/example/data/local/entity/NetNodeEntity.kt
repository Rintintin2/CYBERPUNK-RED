package com.example.data.local.entity

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey

enum class NetNodeType(val labelPt: String) {
    PASSWORD("Senha"),
    FILE("Arquivo"),
    CONTROL_NODE("Nó de Controle"),
    BLACK_ICE("Black ICE"),
    DEMON("Demônio")
}

@Entity(
    tableName = "net_nodes",
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
data class NetNodeEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val campaignId: Long,
    val architectureName: String,
    val floorNumber: Int,
    val nodeType: NetNodeType,
    val title: String,
    val difficultyValue: Int = 6,
    val currentRez: Int = 0,
    val maxRez: Int = 0,
    val isCleared: Boolean = false
)
