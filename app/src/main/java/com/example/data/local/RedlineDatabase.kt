package com.example.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.data.local.dao.CampaignDao
import com.example.data.local.dao.CharacterDao
import com.example.data.local.dao.RuleLibraryDao
import com.example.data.local.dao.SystemsDao
import com.example.data.local.entity.CampaignEntity
import com.example.data.local.entity.CampaignSaveEntity
import com.example.data.local.entity.CharacterEntity
import com.example.data.local.entity.CharacterSkillEntity
import com.example.data.local.entity.CombatParticipantEntity
import com.example.data.local.entity.CyberwareEntity
import com.example.data.local.entity.InventoryItemEntity
import com.example.data.local.entity.JournalEntryEntity
import com.example.data.local.entity.MissionEntity
import com.example.data.local.entity.NetNodeEntity
import com.example.data.local.entity.NpcFactionEntity
import com.example.data.local.entity.RuleDocumentEntity
import com.example.data.local.entity.RuleSectionEntity

@Database(
    entities = [
        CampaignEntity::class,
        CampaignSaveEntity::class,
        CharacterEntity::class,
        CharacterSkillEntity::class,
        InventoryItemEntity::class,
        CyberwareEntity::class,
        NetNodeEntity::class,
        NpcFactionEntity::class,
        MissionEntity::class,
        CombatParticipantEntity::class,
        JournalEntryEntity::class,
        RuleDocumentEntity::class,
        RuleSectionEntity::class
    ],
    version = 4,
    exportSchema = false
)
@TypeConverters(Converters::class)
abstract class RedlineDatabase : RoomDatabase() {
    abstract fun campaignDao(): CampaignDao
    abstract fun characterDao(): CharacterDao
    abstract fun systemsDao(): SystemsDao
    abstract fun ruleLibraryDao(): RuleLibraryDao

    companion object {
        @Volatile
        private var INSTANCE: RedlineDatabase? = null

        private val MIGRATION_1_2 = object : Migration(1, 2) {
            override fun migrate(db: SupportSQLiteDatabase) {
                db.execSQL("ALTER TABLE characters ADD COLUMN improvementPoints INTEGER NOT NULL DEFAULT 0")
            }
        }

        private val MIGRATION_2_3 = object : Migration(2, 3) {
            override fun migrate(db: SupportSQLiteDatabase) {
                db.execSQL("ALTER TABLE campaigns ADD COLUMN currentDistrictCode TEXT NOT NULL DEFAULT 'E'")
                db.execSQL("ALTER TABLE campaigns ADD COLUMN flashOfLuckMissionUses INTEGER NOT NULL DEFAULT 0")
                db.execSQL("ALTER TABLE campaigns ADD COLUMN flashOfLuckBeatUsed INTEGER NOT NULL DEFAULT 0")
            }
        }

        private val MIGRATION_3_4 = object : Migration(3, 4) {
            override fun migrate(db: SupportSQLiteDatabase) {
                db.execSQL("ALTER TABLE characters ADD COLUMN pendingTherapyDiceDelta INTEGER NOT NULL DEFAULT 0")
            }
        }

        fun getInstance(context: Context): RedlineDatabase {
            return INSTANCE ?: synchronized(this) {
                INSTANCE ?: Room.databaseBuilder(
                    context.applicationContext,
                    RedlineDatabase::class.java,
                    "redline_solo.db"
                ).addMigrations(MIGRATION_1_2, MIGRATION_2_3, MIGRATION_3_4)
                    .build().also { INSTANCE = it }
            }
        }
    }
}
