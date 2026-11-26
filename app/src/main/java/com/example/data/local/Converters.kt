package com.example.data.local

import androidx.room.TypeConverter
import com.example.core.rules.PriceCategory
import com.example.core.rules.RedRole
import com.example.core.rules.RedStat
import com.example.data.local.entity.InventoryItemType
import com.example.data.local.entity.JournalEntryType
import com.example.data.local.entity.MissionStatus
import com.example.data.local.entity.NetNodeType

class Converters {
    @TypeConverter fun fromRedRole(value: RedRole): String = value.name
    @TypeConverter fun toRedRole(value: String): RedRole = RedRole.valueOf(value)

    @TypeConverter fun fromRedStat(value: RedStat): String = value.name
    @TypeConverter fun toRedStat(value: String): RedStat = RedStat.valueOf(value)

    @TypeConverter fun fromInventoryType(value: InventoryItemType): String = value.name
    @TypeConverter fun toInventoryType(value: String): InventoryItemType = InventoryItemType.valueOf(value)

    @TypeConverter fun fromPriceCategory(value: PriceCategory): String = value.name
    @TypeConverter fun toPriceCategory(value: String): PriceCategory = PriceCategory.valueOf(value)

    @TypeConverter fun fromNetNodeType(value: NetNodeType): String = value.name
    @TypeConverter fun toNetNodeType(value: String): NetNodeType = NetNodeType.valueOf(value)

    @TypeConverter fun fromMissionStatus(value: MissionStatus): String = value.name
    @TypeConverter fun toMissionStatus(value: String): MissionStatus = MissionStatus.valueOf(value)

    @TypeConverter fun fromJournalEntryType(value: JournalEntryType): String = value.name
    @TypeConverter fun toJournalEntryType(value: String): JournalEntryType = JournalEntryType.valueOf(value)
}
