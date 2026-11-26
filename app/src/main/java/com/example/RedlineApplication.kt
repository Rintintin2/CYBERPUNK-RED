package com.example

import android.app.Application
import com.example.core.ai.GameMasterGateway
import com.example.core.ai.LocalSoloOracleGateway
import com.example.core.rules.CombatEngine
import com.example.core.rules.DiceEngine
import com.example.core.rules.NetrunningEngine
import com.example.core.rules.RandomDiceRoller
import com.example.core.rules.SkillCheckEngine
import com.example.data.local.RedlineDatabase
import com.example.data.repository.CampaignRepository
import com.example.data.repository.CharacterRepository
import com.example.data.repository.RuleLibraryRepository
import com.example.data.repository.SessionRepository

class RedlineApplication : Application() {
    val database: RedlineDatabase by lazy { RedlineDatabase.getInstance(this) }

    val diceEngine: DiceEngine by lazy { DiceEngine(RandomDiceRoller()) }
    val skillCheckEngine: SkillCheckEngine by lazy { SkillCheckEngine(diceEngine) }
    val combatEngine: CombatEngine by lazy { CombatEngine(diceEngine) }
    val netrunningEngine: NetrunningEngine by lazy { NetrunningEngine(diceEngine) }

    val gameMasterGateway: GameMasterGateway by lazy { LocalSoloOracleGateway() }

    val campaignRepository: CampaignRepository by lazy {
        CampaignRepository(
            campaignDao = database.campaignDao(),
            characterDao = database.characterDao(),
            systemsDao = database.systemsDao()
        )
    }

    val characterRepository: CharacterRepository by lazy {
        CharacterRepository(
            characterDao = database.characterDao(),
            combatEngine = combatEngine
        )
    }

    val sessionRepository: SessionRepository by lazy {
        SessionRepository(
            systemsDao = database.systemsDao(),
            campaignDao = database.campaignDao(),
            skillCheckEngine = skillCheckEngine,
            netrunningEngine = netrunningEngine
        )
    }

    val ruleLibraryRepository: RuleLibraryRepository by lazy {
        RuleLibraryRepository(
            context = this,
            ruleLibraryDao = database.ruleLibraryDao()
        )
    }
}
