package com.example.data.local.entity

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey
import com.example.core.rules.PriceCategory

enum class InventoryItemType(val labelPt: String) {
    WEAPON("Arma"),
    AMMO("Munição"),
    ARMOR("Armadura"),
    GEAR("Equipamento"),
    CYBERDECK("Cyberdeck"),
    PROGRAM("Programa")
}

@Entity(
    tableName = "inventory_items",
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
data class InventoryItemEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val campaignId: Long,
    val characterId: Long? = null,
    val name: String,
    val itemType: InventoryItemType,
    val priceCategory: PriceCategory = PriceCategory.PREMIUM,
    val costEb: Int = 100,
    val quantity: Int = 1,
    val damageDice: Int = 0,
    val stoppingPower: Int = 0,
    val isEquipped: Boolean = false,
    val notes: String = ""
)
