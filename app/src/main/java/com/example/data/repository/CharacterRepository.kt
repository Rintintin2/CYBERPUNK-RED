package com.example.data.repository

import com.example.core.rules.CharacterCalculator
import com.example.core.rules.CombatEngine
import com.example.core.rules.DamageApplicationResult
import com.example.core.rules.DeathSaveResult
import com.example.core.rules.OfficialEconomyAndProgression
import com.example.core.rules.RedRole
import com.example.core.rules.RedStat
import com.example.data.local.dao.CharacterDao
import com.example.data.local.entity.CharacterEntity
import com.example.data.local.entity.CharacterSkillEntity
import com.example.data.local.entity.CyberwareEntity
import kotlinx.coroutines.flow.Flow

sealed class IpSpendOutcome {
    data class Success(val spentIp: Int, val remainingIp: Int, val newLevelOrRank: Int) : IpSpendOutcome()
    data class InsufficientIp(val requiredIp: Int, val currentIp: Int) : IpSpendOutcome()
    data object MaxLevelReached : IpSpendOutcome()
}

class CharacterRepository(
    private val characterDao: CharacterDao,
    private val combatEngine: CombatEngine
) {
    fun observeCharacters(campaignId: Long): Flow<List<CharacterEntity>> =
        characterDao.observeCharactersForCampaign(campaignId)

    fun observePrimaryCharacter(campaignId: Long): Flow<CharacterEntity?> =
        characterDao.observePrimaryCharacter(campaignId)

    fun observeSkills(characterId: Long): Flow<List<CharacterSkillEntity>> =
        characterDao.observeSkills(characterId)

    fun observeCyberware(characterId: Long): Flow<List<CyberwareEntity>> =
        characterDao.observeCyberware(characterId)

    suspend fun createCharacter(
        campaignId: Long,
        handle: String,
        role: RedRole,
        stats: com.example.core.rules.CharacterStats,
        isPrimary: Boolean = false
    ): Long {
        val maxHp = CharacterCalculator.calculateMaxHp(stats.body, stats.willpower)
        return characterDao.insertCharacter(
            CharacterEntity(
                campaignId = campaignId,
                handle = handle.trim(),
                role = role,
                intelligence = stats.intelligence,
                reflexes = stats.reflexes,
                dexterity = stats.dexterity,
                technique = stats.technique,
                cool = stats.cool,
                willpower = stats.willpower,
                luckMax = stats.luckMax,
                luckCurrent = stats.luckMax,
                move = stats.move,
                body = stats.body,
                empathyMax = stats.empathyMax,
                currentHp = maxHp,
                isPrimary = isPrimary
            )
        )
    }

    suspend fun updateCharacter(character: CharacterEntity) {
        val maxHp = CharacterCalculator.calculateMaxHp(character.body, character.willpower)
        val clampedHp = character.currentHp.coerceIn(0, maxHp)
        val clampedLuck = character.luckCurrent.coerceIn(0, character.luckMax)
        characterDao.updateCharacter(
            character.copy(
                currentHp = clampedHp,
                luckCurrent = clampedLuck,
                updatedAt = System.currentTimeMillis()
            )
        )
    }

    suspend fun updateStat(character: CharacterEntity, stat: RedStat, delta: Int) {
        val updated = when (stat) {
            RedStat.INT -> character.copy(intelligence = (character.intelligence + delta).coerceIn(1, 10))
            RedStat.REF -> character.copy(reflexes = (character.reflexes + delta).coerceIn(1, 10))
            RedStat.DEX -> character.copy(dexterity = (character.dexterity + delta).coerceIn(1, 10))
            RedStat.TECH -> character.copy(technique = (character.technique + delta).coerceIn(1, 10))
            RedStat.COOL -> character.copy(cool = (character.cool + delta).coerceIn(1, 10))
            RedStat.WILL -> character.copy(willpower = (character.willpower + delta).coerceIn(1, 10))
            RedStat.LUCK -> {
                val newMax = (character.luckMax + delta).coerceIn(1, 10)
                character.copy(luckMax = newMax, luckCurrent = character.luckCurrent.coerceIn(0, newMax))
            }
            RedStat.MOVE -> character.copy(move = (character.move + delta).coerceIn(1, 10))
            RedStat.BODY -> character.copy(body = (character.body + delta).coerceIn(1, 17))
            RedStat.EMP -> character.copy(empathyMax = (character.empathyMax + delta).coerceIn(1, 10))
        }
        updateCharacter(updated)
    }

    suspend fun adjustHp(character: CharacterEntity, delta: Int) {
        val maxHp = CharacterCalculator.calculateMaxHp(character.body, character.willpower)
        val newHp = (character.currentHp + delta).coerceIn(0, maxHp)
        characterDao.updateCharacter(
            character.copy(currentHp = newHp, updatedAt = System.currentTimeMillis())
        )
    }

    suspend fun applyDeterministicDamage(
        character: CharacterEntity,
        damageDiceCount: Int,
        isHeadshot: Boolean,
        ignoreHalfArmor: Boolean = false
    ): DamageApplicationResult {
        val maxHp = CharacterCalculator.calculateMaxHp(character.body, character.willpower)
        val targetSp = if (isHeadshot) character.headArmorSp else character.bodyArmorSp
        val result = combatEngine.applyWeaponDamage(
            damageDiceCount = damageDiceCount,
            targetCurrentHp = character.currentHp,
            targetMaxHp = maxHp,
            targetArmorSp = targetSp,
            isHeadshot = isHeadshot,
            ignoreHalfArmor = ignoreHalfArmor
        )
        val updated = if (isHeadshot) {
            character.copy(currentHp = result.hpAfter, headArmorSp = result.stoppingPowerAfter)
        } else {
            character.copy(currentHp = result.hpAfter, bodyArmorSp = result.stoppingPowerAfter)
        }
        characterDao.updateCharacter(updated.copy(updatedAt = System.currentTimeMillis()))
        return result
    }

    suspend fun rollDeathSaveForCharacter(character: CharacterEntity): DeathSaveResult {
        val result = combatEngine.rollDeathSave(
            bodyStat = character.body,
            currentDeathSavePenalty = character.deathSaveMod
        )
        if (result.survived) {
            characterDao.updateCharacter(
                character.copy(
                    deathSaveMod = character.deathSaveMod + 1,
                    updatedAt = System.currentTimeMillis()
                )
            )
        }
        return result
    }

    suspend fun addOrUpdateSkill(skill: CharacterSkillEntity) {
        characterDao.insertSkill(skill)
    }

    suspend fun deleteSkill(skillId: Long) {
        characterDao.deleteSkillById(skillId)
    }

    suspend fun awardImprovementPoints(character: CharacterEntity, amount: Int): CharacterEntity {
        val delta = amount.coerceAtLeast(0)
        val updated = character.copy(
            improvementPoints = character.improvementPoints + delta,
            updatedAt = System.currentTimeMillis()
        )
        characterDao.updateCharacter(updated)
        return updated
    }

    suspend fun upgradeSkillWithIp(
        character: CharacterEntity,
        skill: CharacterSkillEntity,
        isDoubleCost: Boolean
    ): IpSpendOutcome {
        if (skill.level >= 10) return IpSpendOutcome.MaxLevelReached
        val nextLevel = skill.level + 1
        val cost = OfficialEconomyAndProgression.skillIpCostForNextLevel(nextLevel, isDoubleCost)
        if (character.improvementPoints < cost) {
            return IpSpendOutcome.InsufficientIp(requiredIp = cost, currentIp = character.improvementPoints)
        }
        val remaining = character.improvementPoints - cost
        characterDao.updateCharacter(
            character.copy(improvementPoints = remaining, updatedAt = System.currentTimeMillis())
        )
        characterDao.insertSkill(skill.copy(level = nextLevel))
        return IpSpendOutcome.Success(spentIp = cost, remainingIp = remaining, newLevelOrRank = nextLevel)
    }

    suspend fun upgradeRoleRankWithIp(character: CharacterEntity): IpSpendOutcome {
        if (character.roleRank >= 10) return IpSpendOutcome.MaxLevelReached
        val nextRank = character.roleRank + 1
        val cost = OfficialEconomyAndProgression.roleAbilityIpCostForNextRank(nextRank)
        if (character.improvementPoints < cost) {
            return IpSpendOutcome.InsufficientIp(requiredIp = cost, currentIp = character.improvementPoints)
        }
        val remaining = character.improvementPoints - cost
        characterDao.updateCharacter(
            character.copy(
                roleRank = nextRank,
                improvementPoints = remaining,
                updatedAt = System.currentTimeMillis()
            )
        )
        return IpSpendOutcome.Success(spentIp = cost, remainingIp = remaining, newLevelOrRank = nextRank)
    }

    suspend fun installCyberware(
        character: CharacterEntity,
        name: String,
        slotArea: String,
        humanityCost: Int,
        notes: String
    ) {
        val cost = humanityCost.coerceAtLeast(0)
        characterDao.insertCyberware(
            CyberwareEntity(
                characterId = character.id,
                name = name.trim(),
                slotArea = slotArea.trim(),
                humanityCost = cost,
                installed = true,
                notes = notes.trim()
            )
        )
        characterDao.updateCharacter(
            character.copy(
                humanityLoss = character.humanityLoss + cost,
                updatedAt = System.currentTimeMillis()
            )
        )
    }

    suspend fun toggleCyberwareInstalled(
        character: CharacterEntity,
        cyberware: CyberwareEntity,
        allCyberware: List<CyberwareEntity>
    ) {
        val toggled = cyberware.copy(installed = !cyberware.installed)
        characterDao.updateCyberware(toggled)
        val totalLoss = allCyberware
            .map { if (it.id == cyberware.id) toggled else it }
            .filter { it.installed }
            .sumOf { it.humanityCost }
        characterDao.updateCharacter(
            character.copy(
                humanityLoss = totalLoss,
                updatedAt = System.currentTimeMillis()
            )
        )
    }

    suspend fun deleteCyberware(
        character: CharacterEntity,
        cyberware: CyberwareEntity,
        allCyberware: List<CyberwareEntity>
    ) {
        characterDao.deleteCyberwareById(cyberware.id)
        val totalLoss = allCyberware
            .filter { it.id != cyberware.id && it.installed }
            .sumOf { it.humanityCost }
        characterDao.updateCharacter(
            character.copy(
                humanityLoss = totalLoss,
                updatedAt = System.currentTimeMillis()
            )
        )
    }
}
