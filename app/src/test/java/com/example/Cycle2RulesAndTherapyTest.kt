package com.example

import com.example.core.rules.CharacterCalculator
import com.example.core.rules.CharacterStats
import com.example.core.rules.CyberwareValidationResult
import com.example.core.rules.CyberwareValidator
import com.example.core.rules.DiceEngine
import com.example.core.rules.NetrunningEngine
import com.example.core.rules.OfficialSinglePlayerPlusCatalog
import com.example.core.rules.RedStat
import com.example.core.rules.ScriptedDiceRoller
import com.example.core.rules.TherapyEngine
import com.example.core.rules.TherapyType
import com.example.core.rules.WoundState
import com.example.data.local.entity.CyberwareEntity
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Test

class Cycle2RulesAndTherapyTest {

    @Test
    fun `solo oracle uses strictly greater than DV so DV5 is true 50-50`() {
        val tieFail = OfficialSinglePlayerPlusCatalog.askSoloOracleYesNo(
            questionPt = "Tem câmera?",
            oddsLabelPt = "50/50 (DV5)",
            targetDv = 5,
            diceEngine = DiceEngine(ScriptedDiceRoller(listOf(5)))
        )
        assertEquals("NÃO", tieFail.answerPt)

        val beatPass = OfficialSinglePlayerPlusCatalog.askSoloOracleYesNo(
            questionPt = "Tem câmera?",
            oddsLabelPt = "50/50 (DV5)",
            targetDv = 5,
            diceEngine = DiceEngine(ScriptedDiceRoller(listOf(6)))
        )
        assertEquals("SIM", beatPass.answerPt)
    }

    @Test
    fun `going quiet watcher active search vs passive encounter tie handling`() {
        // Runner total = 4+2+4 = 10; Watcher total = 4+2+4 = 10 (TIE)
        val searchTie = NetrunningEngine(DiceEngine(ScriptedDiceRoller(listOf(4, 4))))
            .contestWatcherVsStealth(
                interfaceRank = 4,
                cloakBonus = 2,
                watcherInterface = 4,
                watcherPathfinder = 2,
                isWatcherActiveSearch = true
            )
        assertTrue("Active Watcher search must strictly beat runner; tie keeps stealth", searchTie.runnerStealthMaintained)

        val encounterTie = NetrunningEngine(DiceEngine(ScriptedDiceRoller(listOf(4, 4))))
            .contestWatcherVsStealth(
                interfaceRank = 4,
                cloakBonus = 2,
                watcherInterface = 4,
                watcherPathfinder = 2,
                isWatcherActiveSearch = false
            )
        assertFalse("Passive encounter requires runner to beat watcher", encounterTie.runnerStealthMaintained)
    }

    @Test
    fun `armor penalty applies to REF DEX MOVE and Pain Editor negates seriously wounded penalty`() {
        val baseStats = CharacterStats(
            intelligence = 6, reflexes = 8, dexterity = 7, technique = 6,
            cool = 6, willpower = 6, luckMax = 6, move = 6, body = 6, empathyMax = 6
        ) // Max HP = 40, Seriously Wounded <= 20
        val painEditor = CyberwareEntity(
            characterId = 1L,
            name = "Editor de Dor (Pain Editor)",
            slotArea = "Neuralware",
            humanityCost = 14,
            installed = true
        )
        val derived = CharacterCalculator.computeDerivedMetrics(
            stats = baseStats,
            currentHp = 18, // Seriously Wounded (-2 base wound penalty)
            totalHumanityLoss = 14,
            headArmorSp = 11,
            bodyArmorSp = 15, // Flak -> -4 penalty to REF, DEX, MOVE
            installedCyberware = listOf(painEditor)
        )

        assertEquals(WoundState.SERIOUSLY_WOUNDED, derived.woundState)
        assertTrue(derived.painEditorActive)
        assertEquals(0, derived.effectiveWoundPenalty)
        assertEquals(-4, derived.armorPenalty)
        assertEquals(-4, derived.totalCheckPenalty(RedStat.REF))
        assertEquals(-4, derived.totalCheckPenalty(RedStat.DEX))
        assertEquals(-4, derived.totalCheckPenalty(RedStat.MOVE))
        assertEquals(0, derived.totalCheckPenalty(RedStat.INT))
        assertEquals(0, derived.totalCheckPenalty(RedStat.TECH))
    }

    @Test
    fun `cyberware validator enforces foundational pieces speedware cap and scales BODY`() {
        val noNeural = CyberwareValidator.validateInstallation(
            baseBody = 6,
            existingCyberware = emptyList(),
            name = "Kerenzikov",
            slotArea = "Neuralware"
        )
        assertTrue(noNeural is CyberwareValidationResult.Blocked)

        val neuralLink = CyberwareEntity(characterId = 1L, name = "Neural Link", slotArea = "Neuralware", humanityCost = 7, installed = true)
        val kerenzikov = CyberwareEntity(characterId = 1L, name = "Kerenzikov", slotArea = "Neuralware", humanityCost = 14, installed = true)

        val doubleSpeedware = CyberwareValidator.validateInstallation(
            baseBody = 6,
            existingCyberware = listOf(neuralLink, kerenzikov),
            name = "Sandevistan",
            slotArea = "Neuralware"
        )
        assertTrue(doubleSpeedware is CyberwareValidationResult.Blocked)

        val frameBlocked = CyberwareValidator.validateInstallation(
            baseBody = 6,
            existingCyberware = listOf(neuralLink),
            name = "Linear Frame Σ (Sigma)",
            slotArea = "Borgware",
        )
        assertTrue(frameBlocked is CyberwareValidationResult.Blocked)

        val graftedMuscle = CyberwareEntity(
            characterId = 1L,
            name = "Músculos e Ossos Enxertados",
            slotArea = "Cyberware Interno",
            humanityCost = 14,
            installed = true
        )
        val frameAllowed = CyberwareValidator.validateInstallation(
            baseBody = 6,
            existingCyberware = listOf(graftedMuscle),
            name = "Linear Frame Σ (Sigma)",
            slotArea = "Borgware"
        )
        assertTrue(frameAllowed is CyberwareValidationResult.Valid)

        val linearSigma = CyberwareEntity(
            characterId = 1L,
            name = "Linear Frame Σ (Sigma)",
            slotArea = "Borgware",
            humanityCost = 14,
            installed = true
        )
        val derived = CharacterCalculator.computeDerivedMetrics(
            stats = CharacterStats(6, 7, 6, 5, 6, 6, 6, 6, body = 6, empathyMax = 6),
            currentHp = 55,
            totalHumanityLoss = 28,
            installedCyberware = listOf(graftedMuscle, linearSigma)
        )
        assertEquals(12, derived.effectiveBody)
        assertEquals(12, derived.deathSaveBase)
        assertEquals(55, derived.maxHp) // 10 + 5 * ceil((12+6)/2) = 55
    }

    @Test
    fun `therapy engine restores humanity respecting permanent max reduction floor and brainwasher delta`() {
        val engine = TherapyEngine(DiceEngine(ScriptedDiceRoller(listOf(6, 6, 5))))
        // EMP 6 => raw max 60; 2 non-borg cyberware => permanent reduction 4 => effective max 56
        // Humanity loss = 16 -> current 44. Standard (2d6) + Brainwasher (+1d6) = 3d6 = 17.
        // Loss cannot drop below permanent reduction 4 => lossAfter = 4, recovered = 12, current = 56.
        val outcome = engine.performTherapy(
            therapyType = TherapyType.STANDARD_HUMANITY,
            empathyMax = 6,
            currentHumanityLoss = 16,
            maxHumanityReduction = 4,
            brainwasherDiceDelta = 1,
            useClinicService = true
        )
        assertTrue(outcome.checkSucceeded)
        assertEquals(3, outcome.diceCountRolled)
        assertEquals(4, outcome.humanityLossAfter)
        assertEquals(12, outcome.humanityRecovered)
        assertEquals(56, outcome.newCurrentHumanity)
        assertEquals(56, outcome.maxEffectiveHumanity)
    }
}
