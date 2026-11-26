package com.example

import com.example.core.rules.CharacterCalculator
import com.example.core.rules.CharacterStats
import com.example.core.rules.CombatEngine
import com.example.core.rules.CriticalMarker
import com.example.core.rules.DiceEngine
import com.example.core.rules.EconomyEngine
import com.example.core.rules.EncounterTimeOfDay
import com.example.core.rules.NetArchDifficulty
import com.example.core.rules.NetrunningEngine
import com.example.core.rules.NpcThreatTier
import com.example.core.rules.OfficialBlackChromeCyberwareCatalog
import com.example.core.rules.OfficialBlackChromeGearCatalog
import com.example.core.rules.OfficialCombatTables
import com.example.core.rules.OfficialCyberwareCatalog
import com.example.core.rules.OfficialDlcCyberwareAndFbc
import com.example.core.rules.OfficialDlcGearCatalog
import com.example.core.rules.OfficialDlcNpcsAndNets
import com.example.core.rules.OfficialDlcSoloSystems
import com.example.core.rules.OfficialEconomyAndProgression
import com.example.core.rules.OfficialEncountersAndLoreCatalog
import com.example.core.rules.OfficialGearCatalog
import com.example.core.rules.OfficialHookBookData
import com.example.core.rules.OfficialNetArchitectures
import com.example.core.rules.OfficialNpcCatalog
import com.example.core.rules.OfficialSkillCatalog
import com.example.core.rules.OfficialStreetGenerators
import com.example.core.rules.OfficialTalesAndHookBookCatalog
import com.example.core.rules.OfficialWeaponsAndDefensesCatalog
import com.example.core.rules.PriceCategory
import com.example.core.rules.PurchaseOutcome
import com.example.core.rules.RangeBracket
import com.example.core.rules.RangeWeaponCategory
import com.example.core.rules.RedRole
import com.example.core.rules.RedStat
import com.example.core.rules.SalvageCategory
import com.example.core.rules.SalvageWorthTier
import com.example.core.rules.ScriptedDiceRoller
import com.example.core.rules.SkillCheckEngine
import com.example.core.rules.SkillCheckRequest
import com.example.core.rules.WeaponQuality
import com.example.core.rules.WeatherSeason
import com.example.core.rules.WoundState
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertNull
import org.junit.Assert.assertTrue
import org.junit.Test

class RedlineRulesEngineTest {

    @Test
    fun `d10 explodes on 10 and implodes on 1`() {
        val critEngine = DiceEngine(ScriptedDiceRoller(listOf(10, 7)))
        val critRoll = critEngine.rollD10Exploding()
        assertEquals(CriticalMarker.CRITICAL_SUCCESS, critRoll.marker)
        assertEquals(17, critRoll.netDieTotal)

        val fumbleEngine = DiceEngine(ScriptedDiceRoller(listOf(1, 6)))
        val fumbleRoll = fumbleEngine.rollD10Exploding()
        assertEquals(CriticalMarker.CRITICAL_FAILURE, fumbleRoll.marker)
        assertEquals(-5, fumbleRoll.netDieTotal)
    }

    @Test
    fun `character calculator computes canonical cyberpunk red metrics and max humanity loss`() {
        // BODY 7 + WILL 6 = 13 -> ceil(6.5) = 7 -> 10 + 35 = 45 HP
        // Seriously Wounded = ceil(45 / 2.0) = 23
        val stats = CharacterStats(
            intelligence = 6, reflexes = 8, dexterity = 7, technique = 5,
            cool = 6, willpower = 6, luckMax = 6, move = 6, body = 7, empathyMax = 6
        )
        val reduction = 2 * 2 + 1 * 4 // 2 cyberware (-4) + 1 borgware (-4) = -8 max Humanity
        val metrics = CharacterCalculator.computeDerivedMetrics(
            stats = stats,
            currentHp = 23,
            totalHumanityLoss = 14,
            maxHumanityReduction = reduction
        )
        assertEquals(45, metrics.maxHp)
        assertEquals(23, metrics.seriouslyWoundedThreshold)
        assertEquals(52, metrics.maxHumanity)
        assertEquals(46, metrics.currentHumanity)
        assertEquals(4, metrics.currentEmpathy)
        assertEquals(WoundState.SERIOUSLY_WOUNDED, metrics.woundState)
        assertEquals(-2, metrics.woundPenalty)

        val effectiveMax = CharacterCalculator.calculateEffectiveMaxHumanity(
            empathyMax = 6,
            cyberwareWithHlCount = 2,
            borgwareWithHlCount = 1
        )
        assertEquals(52, effectiveMax)
    }

    @Test
    fun `skill check requires strictly beating DV`() {
        val roller = ScriptedDiceRoller(listOf(5, 6))
        val engine = SkillCheckEngine(DiceEngine(roller))

        // Stat 6 + Skill 2 + Die 5 = 13 vs DV 13 -> FAIL (must beat DV)
        val tieResult = engine.performSkillCheck(
            SkillCheckRequest(
                stat = RedStat.REF,
                statValue = 6,
                skillName = "Armas Curtas",
                skillLevel = 2,
                difficultyValue = 13
            )
        )
        assertEquals(13, tieResult.total)
        assertFalse(tieResult.succeeded)

        // Stat 6 + Skill 2 + Die 6 = 14 vs DV 13 -> SUCCEED
        val beatResult = engine.performSkillCheck(
            SkillCheckRequest(
                stat = RedStat.REF,
                statValue = 6,
                skillName = "Armas Curtas",
                skillLevel = 2,
                difficultyValue = 13
            )
        )
        assertEquals(14, beatResult.total)
        assertTrue(beatResult.succeeded)
    }

    @Test
    fun `combat damage ablates armor and triggers critical injury on double six`() {
        // Damage: 6, 6, 4 (=16), Crit injury 2d6: 2, 4 (=6 Broken Arm)
        val combat = CombatEngine(DiceEngine(ScriptedDiceRoller(listOf(6, 6, 4, 2, 4))))
        val result = combat.applyWeaponDamage(
            damageDiceCount = 3,
            targetCurrentHp = 40,
            targetMaxHp = 40,
            targetArmorSp = 11,
            isHeadshot = false
        )
        // Raw damage = 16 > SP 11 -> base through = 5 + 5 crit bonus = 10 HP loss; SP 11 -> 10
        assertTrue(result.penetratedArmor)
        assertTrue(result.criticalInjuryTriggered)
        assertEquals("Braço Quebrado", result.criticalInjury?.namePt)
        assertEquals(10, result.hpDamageDealt)
        assertEquals(30, result.hpAfter)
        assertEquals(10, result.stoppingPowerAfter)
    }

    @Test
    fun `critical injury deals +5 bonus damage even without armor penetration and does not ablate`() {
        // Damage: 6, 6, 1 (=13) vs SP 15; Crit injury 2d6: 4, 3 (=7 Foreign Object)
        val combat = CombatEngine(DiceEngine(ScriptedDiceRoller(listOf(6, 6, 1, 4, 3))))
        val result = combat.applyWeaponDamage(
            damageDiceCount = 3,
            targetCurrentHp = 40,
            targetMaxHp = 40,
            targetArmorSp = 15,
            isHeadshot = false
        )
        assertFalse(result.penetratedArmor)
        assertTrue(result.criticalInjuryTriggered)
        assertNotNull(result.criticalInjury)
        assertEquals(5, result.hpDamageDealt)
        assertEquals(35, result.hpAfter)
        assertEquals(15, result.stoppingPowerAfter)
    }

    @Test
    fun `melee damage halves defender SP rounded up and ablates original SP by 1`() {
        // Damage: 4, 5 (=9) vs SP 11 -> effective SP = ceil(11/2.0) = 6 -> 9 - 6 = 3 HP damage, SP 11 -> 10
        val combat = CombatEngine(DiceEngine(ScriptedDiceRoller(listOf(4, 5))))
        val result = combat.applyWeaponDamage(
            damageDiceCount = 2,
            targetCurrentHp = 40,
            targetMaxHp = 40,
            targetArmorSp = 11,
            isHeadshot = false,
            ignoreHalfArmor = true
        )
        assertEquals(6, result.effectiveStoppingPower)
        assertTrue(result.penetratedArmor)
        assertEquals(3, result.hpDamageDealt)
        assertEquals(37, result.hpAfter)
        assertEquals(10, result.stoppingPowerAfter)
    }

    @Test
    fun `official range DV and skill catalog tables match core rulebook`() {
        assertEquals(13, OfficialCombatTables.getSingleShotDv(RangeWeaponCategory.PISTOL, RangeBracket.M0_6))
        assertNull(OfficialCombatTables.getSingleShotDv(RangeWeaponCategory.PISTOL, RangeBracket.M201_400))
        assertEquals(13, OfficialCombatTables.getSingleShotDv(RangeWeaponCategory.ASSAULT_RIFLE, RangeBracket.M26_50))
        assertEquals(17 to 4, OfficialCombatTables.getAutofireDvAndMultiplier(RangeWeaponCategory.ASSAULT_RIFLE, RangeBracket.M13_25))
        assertEquals(20 to 3, OfficialCombatTables.getAutofireDvAndMultiplier(RangeWeaponCategory.SMG, RangeBracket.M0_6))

        assertEquals(67, OfficialSkillCatalog.allSkills.size)
        assertEquals(67, OfficialSkillCatalog.allSkills.map { it.nameEn }.distinct().size)
        RedRole.entries.forEach { role ->
            val stats = OfficialSkillCatalog.streetratStats(role, 1)
            assertTrue(stats.reflexes in 1..10)
            assertTrue(OfficialSkillCatalog.streetratSkillTemplate(role).isNotEmpty())
        }
    }

    @Test
    fun `net architecture generator builds lobby and body floors within official 3d6 bounds`() {
        // 3d6 floors = (1, 1, 2) -> 4 floors
        // Floor 1 lobby = 2 (Password DV6), Floor 2 lobby = 4 (Skunk)
        // Floor 3 body 3d6 = (3, 3, 4) -> 10 (File), Floor 4 body 3d6 = (3, 4, 4) -> 11 (Control Node)
        val rolls = listOf(1, 1, 2, 2, 4, 3, 3, 4, 3, 4, 4)
        val floors = OfficialNetArchitectures.generateArchitecture(
            diceEngine = DiceEngine(ScriptedDiceRoller(rolls)),
            difficulty = NetArchDifficulty.STANDARD
        )
        assertEquals(4, floors.size)
        assertEquals(8, floors[2].dvOrRez)
        assertEquals(8, floors[3].dvOrRez)
    }

    @Test
    fun `netrunning rez damage reduces ice to zero and derezzes`() {
        val netEngine = NetrunningEngine(DiceEngine(ScriptedDiceRoller(listOf(5, 4))))
        val rezResult = netEngine.applyRezDamage(damageDiceCount = 2, currentRez = 8)
        assertEquals(0, rezResult.rezAfter)
        assertTrue(rezResult.derezzed)
    }

    @Test
    fun `economy engine enforces deterministic eurodollar balance`() {
        val ok = EconomyEngine.attemptPurchase(currentEurodollars = 500, itemCostEb = 100)
        assertEquals(PurchaseOutcome.Approved(400), ok)

        val denied = EconomyEngine.attemptPurchase(currentEurodollars = 50, itemCostEb = 100)
        assertEquals(PurchaseOutcome.InsufficientFunds(50, 100), denied)
    }

    @Test
    fun `weapon quality pricing and special ammo mechanics follow core rulebook part 2`() {
        assertEquals(50, WeaponQuality.POOR.adjustedPriceEb(100))
        assertEquals(500, WeaponQuality.EXCELLENT.adjustedPriceEb(100))
        assertEquals(1, WeaponQuality.EXCELLENT.attackModifier)
        assertTrue(WeaponQuality.POOR.jamsOnNaturalOne)

        // Armor-Piercing ablates SP by 2 instead of 1 when penetrating
        val apCombat = CombatEngine(DiceEngine(ScriptedDiceRoller(listOf(5, 5, 5))))
        val apResult = apCombat.applyWeaponDamage(
            damageDiceCount = 3,
            targetCurrentHp = 35,
            targetMaxHp = 35,
            targetArmorSp = 11,
            ablateAmount = 2
        )
        assertEquals(9, apResult.stoppingPowerAfter)
        assertEquals(31, apResult.hpAfter)

        // Rubber ammo: no crit, no ablation, min 1 HP
        val rubberCombat = CombatEngine(DiceEngine(ScriptedDiceRoller(listOf(6, 6, 6))))
        val rubberResult = rubberCombat.applyWeaponDamage(
            damageDiceCount = 3,
            targetCurrentHp = 5,
            targetMaxHp = 35,
            targetArmorSp = 7,
            ablateAmount = 0,
            canTriggerCritical = false,
            nonLethalMinOneHp = true
        )
        assertFalse(rubberResult.criticalInjuryTriggered)
        assertEquals(7, rubberResult.stoppingPowerAfter)
        assertEquals(1, rubberResult.hpAfter)

        // Smart ammo second chance reroll: 10 + LUCK + 1d10 > DV
        val smartCombat = CombatEngine(DiceEngine(ScriptedDiceRoller(listOf(5))))
        val smartResult = smartCombat.resolveSmartAmmoSecondChance(luckStat = 6, targetDv = 20)
        assertEquals(21, smartResult.total)
        assertTrue(smartResult.hit)

        assertEquals(8, OfficialWeaponsAndDefensesCatalog.weaponAttachments.size)
        assertEquals(10, OfficialWeaponsAndDefensesCatalog.clipChart.size)
        assertEquals(13, OfficialWeaponsAndDefensesCatalog.specialAmmunition.size)
        assertEquals(18, OfficialWeaponsAndDefensesCatalog.netDefenses.size)
    }

    @Test
    fun `improvement points formulas and 10-role hustle payouts match official tables`() {
        assertEquals(20, OfficialEconomyAndProgression.skillIpCostForNextLevel(1, isDoubleCost = false))
        assertEquals(200, OfficialEconomyAndProgression.skillIpCostForNextLevel(10, isDoubleCost = false))
        assertEquals(400, OfficialEconomyAndProgression.skillIpCostForNextLevel(10, isDoubleCost = true))
        assertEquals(300, OfficialEconomyAndProgression.roleAbilityIpCostForNextRank(5))
        assertEquals(600, OfficialEconomyAndProgression.roleAbilityIpCostForNextRank(10))

        // Solo roll=2 pays 200eb at Rank 4, 300eb at Rank 5, 600eb at Rank 8
        assertEquals(200, OfficialEconomyAndProgression.rollHustle(RedRole.SOLO, 4, DiceEngine(ScriptedDiceRoller(listOf(2)))).earnedEb)
        assertEquals(300, OfficialEconomyAndProgression.rollHustle(RedRole.SOLO, 5, DiceEngine(ScriptedDiceRoller(listOf(2)))).earnedEb)
        assertEquals(600, OfficialEconomyAndProgression.rollHustle(RedRole.SOLO, 8, DiceEngine(ScriptedDiceRoller(listOf(2)))).earnedEb)
        assertEquals(10, OfficialEconomyAndProgression.hustleTables.size)
        assertEquals(10, OfficialEconomyAndProgression.fashionStyles.size)
    }

    @Test
    fun `street generators produce valid night markets screamsheets vendits and bodegas`() {
        // Categories: 1 (Food), 3 (Weapons), 2 offers in cat1 (rolls 5, 10), 1 offer in cat3 (roll 25)
        val nmRolls = listOf(1, 3, 2, 5, 10, 1, 25)
        val market = OfficialStreetGenerators.generateNightMarket(DiceEngine(ScriptedDiceRoller(nmRolls)))
        assertEquals(2, market.categories.size)
        assertEquals(3, market.offers.size)

        val headline = OfficialStreetGenerators.rollScreamsheetHeadline(DiceEngine(ScriptedDiceRoller(listOf(1, 1, 1, 1))))
        assertTrue(headline.headlineTextPt.contains("Megacorp"))

        val vendit = OfficialStreetGenerators.rollVendit(DiceEngine(ScriptedDiceRoller(listOf(6, 1))))
        assertEquals("Vendit Bizarro", vendit.typePt)

        val bodega = OfficialStreetGenerators.rollBodegaScene(DiceEngine(ScriptedDiceRoller(listOf(4))))
        assertEquals(4, bodega.roll1d6)
    }

    @Test
    fun `all 10 official NPCs and contiguous 1 to 100 encounter tables resolve cleanly`() {
        assertEquals(10, OfficialNpcCatalog.allNpcs.size)
        assertEquals(2, OfficialNpcCatalog.recommendedCount(NpcThreatTier.MOOK, partySize = 1))
        assertEquals(4, OfficialNpcCatalog.recommendedCount(NpcThreatTier.MOOK, partySize = 2))
        assertEquals(1, OfficialNpcCatalog.recommendedCount(NpcThreatTier.BOSS, partySize = 4))

        for (roll in 1..100) {
            EncounterTimeOfDay.entries.forEach { tod ->
                val res = OfficialEncountersAndLoreCatalog.rollEncounter(tod, DiceEngine(ScriptedDiceRoller(listOf(roll))))
                assertEquals(roll, res.d100Roll)
                assertTrue(res.entry.titlePt.isNotBlank())
            }
        }
        val bossMidnight = OfficialEncountersAndLoreCatalog.rollEncounter(
            EncounterTimeOfDay.MIDNIGHT,
            DiceEngine(ScriptedDiceRoller(listOf(100)))
        )
        assertEquals("Cyberpsycho", bossMidnight.resolvedNpc?.nameEn)
    }

    @Test
    fun `interface red vol 1 to 3 catalogs fbc hp weather salvaging datepath bicycles and hardened npcs work`() {
        // Catalog counts from Interface RED Vol 1-3
        assertEquals(12, OfficialDlcGearCatalog.gunmasWeapons.size)
        assertEquals(12, OfficialDlcGearCatalog.woodchipperWeapons.size)
        assertEquals(15, OfficialDlcGearCatalog.dlcCyberdecks.size)
        assertEquals(11, OfficialDlcGearCatalog.dlcCyberdeckHardware.size)
        assertEquals(17, OfficialDlcCyberwareAndFbc.bioexoticPackages.size)
        assertEquals(13, OfficialDlcCyberwareAndFbc.fbcBodies.size)
        assertEquals(16, OfficialDlcNpcsAndNets.hardenedNpcs.size)
        assertEquals(9, OfficialDlcNpcsAndNets.premadeNetArchitectures.size)
        assertEquals(5, PriceCategory.DIRT_CHEAP.referenceCostEb)
        assertTrue(OfficialGearCatalog.quickPurchaseCatalog.any { it.nameEn == "Malorian Arms Sub-Flechette Gun" })
        assertTrue(OfficialCyberwareCatalog.allCyberware.any { it.nameEn == "Reflex Co-Processor" })

        // Going Metal: Linear Frame Omega Hit Point Chart (BODY 16 -> 55..75, BODY 17 -> 60..80)
        assertEquals(55, CharacterCalculator.calculateMaxHp(16, 2))
        assertEquals(65, CharacterCalculator.calculateMaxHp(16, 6))
        assertEquals(75, CharacterCalculator.calculateMaxHp(16, 10))
        assertEquals(60, CharacterCalculator.calculateMaxHp(17, 2))
        assertEquals(80, CharacterCalculator.calculateMaxHp(17, 10))

        // Weather: 1d6 == 1 rolls Strange Weather 1d10
        val strangeWeather = OfficialDlcSoloSystems.rollWeather(
            WeatherSeason.WINTER_DEC_FEB,
            DiceEngine(ScriptedDiceRoller(listOf(1, 5)))
        )
        assertEquals(5, strangeWeather.strangeRoll1d10)
        assertTrue(strangeWeather.strangeConditionNamePt?.contains("Acid Rain") == true)

        // Salvaging: Critical Failure triggers Peril 1d6
        val salvageFail = OfficialDlcSoloSystems.rollSalvageAttempt(
            basicTechBase = 12,
            worthTier = SalvageWorthTier.EB_100,
            specificCategory = SalvageCategory.CYBERWARE,
            diceEngine = DiceEngine(ScriptedDiceRoller(listOf(1, 6, 3)))
        )
        assertTrue(salvageFail.isCriticalFailure)
        assertFalse(salvageFail.succeeded)
        assertNotNull(salvageFail.perilTextPt)

        val salvageSuccess = OfficialDlcSoloSystems.rollSalvageAttempt(
            basicTechBase = 14,
            worthTier = SalvageWorthTier.EB_100,
            specificCategory = null,
            diceEngine = DiceEngine(ScriptedDiceRoller(listOf(8, 2)))
        )
        assertTrue(salvageSuccess.succeeded)
        assertEquals(17, salvageSuccess.dv)
        assertEquals(SalvageCategory.WEAPONS, salvageSuccess.category)

        // Datepath generator
        val dateRes = OfficialDlcSoloSystems.rollDatepath(DiceEngine(ScriptedDiceRoller(listOf(3, 4, 5, 6, 2, 2, 7, 1))))
        assertTrue(dateRes.locationNamePt.isNotBlank())
        assertTrue(dateRes.outcomeLabelPt.contains("Bom Encontro"))

        // Spinning Your Wheels: Bicycles speed by BODY
        assertEquals(8 to 12, OfficialDlcSoloSystems.bicycleSpeedForBody(3))
        assertEquals(10 to 20, OfficialDlcSoloSystems.bicycleSpeedForBody(6))
        assertEquals(15 to 30, OfficialDlcSoloSystems.bicycleSpeedForBody(10))

        // Hardened Scaling & Detection
        assertTrue(
            OfficialDlcNpcsAndNets.isHardenedEdgerunner(
                reflexes = 8, evasionSkill = 6, dexterity = 6, move = 6,
                willpower = 5, body = 5, highestAttackBase = 12,
                autofireOrMartialArtsSkill = 2, soloRank = 0, hasLuxuryOrBetterWeapon = false
            )
        )
        assertEquals(2, OfficialNpcCatalog.hardenedRecommendedCount(NpcThreatTier.MOOK, 2))
        assertEquals(1, OfficialNpcCatalog.hardenedRecommendedCount(NpcThreatTier.MINI_BOSS, 3))
    }

    @Test
    fun `black chrome hook book and tales of the red plus catalogs and generators work`() {
        // Black Chrome Gear & Vehicles
        assertEquals(5, OfficialBlackChromeGearCatalog.blackChromeApps.size)
        assertEquals(16, OfficialBlackChromeGearCatalog.blackChromeFashionAndArmor.size)
        assertEquals(25, OfficialBlackChromeGearCatalog.blackChromeGeneralGearAndFrames.size)
        assertEquals(24, OfficialBlackChromeGearCatalog.blackChromeExplosivesAndMelee.size)
        assertEquals(29, OfficialBlackChromeGearCatalog.blackChromeFirearms.size)
        assertEquals(31, OfficialBlackChromeGearCatalog.blackChromeVehicles.size)
        assertEquals(45, OfficialGearCatalog.allVehicles.size)
        assertTrue(OfficialGearCatalog.quickPurchaseCatalog.any { it.nameEn == "Militech Perseus" })
        assertTrue(OfficialGearCatalog.quickPurchaseCatalog.any { it.nameEn == "Arasaka Prototype Variable Assault Rifle" })

        // Black Chrome Cyberware & Cyberfingers
        assertEquals(18, OfficialBlackChromeCyberwareCatalog.blackChromeCyberware.size)
        assertEquals(16, OfficialBlackChromeCyberwareCatalog.blackChromeCyberfingers.size)
        assertEquals(34, OfficialBlackChromeCyberwareCatalog.allBlackChromeCyberware.size)
        assertEquals(6, OfficialBlackChromeCyberwareCatalog.sixNightMarketsPt.size)
        assertTrue(OfficialCyberwareCatalog.allCyberware.any { it.nameEn == "Budget Chipware Socket" })
        assertTrue(OfficialCyberwareCatalog.allCyberware.any { it.nameEn == "LF-001 SWAT Linear Frame (Internal)" })

        // Night City Hook Book (130 Hooks across 26 districts/attractions)
        assertEquals(130, OfficialHookBookData.allPlotHooks.size)
        assertEquals(26, OfficialHookBookData.districtsPt.size)
        assertTrue(OfficialHookBookData.districtsPt.contains("Playland by the Sea"))

        // Tales of the RED+ & Named NPCs
        assertEquals(12, OfficialTalesAndHookBookCatalog.namedDlcNpcs.size)
        assertEquals(38, OfficialNpcCatalog.allIncludingDlcNpcs.size)
        assertNotNull(OfficialNpcCatalog.findByEnglishName("Trace Santiago"))
        assertNotNull(OfficialNpcCatalog.findByEnglishName("Woodchipper"))
        assertEquals(6, OfficialTalesAndHookBookCatalog.rcNightMarketVendors1d6.size)
        assertEquals(5, OfficialTalesAndHookBookCatalog.totrScreamsheetsSummaryPt.size)

        // Hook Book & RC Vendor deterministic rolls
        val unfilteredHook = OfficialTalesAndHookBookCatalog.rollPlotHook(
            districtFilterPt = null,
            diceEngine = DiceEngine(ScriptedDiceRoller(listOf(100, 3)))
        )
        assertEquals(OfficialHookBookData.allPlotHooks[119], unfilteredHook)

        val filteredHook = OfficialTalesAndHookBookCatalog.rollPlotHook(
            districtFilterPt = "Kabuki",
            diceEngine = DiceEngine(ScriptedDiceRoller(listOf(2)))
        )
        assertEquals("Kabuki", filteredHook.districtPt)
        assertEquals("Grand Theft Automated", filteredHook.titlePt)

        val rcVendor = OfficialTalesAndHookBookCatalog.rollRcNightMarketVendor(
            diceEngine = DiceEngine(ScriptedDiceRoller(listOf(6)))
        )
        assertTrue(rcVendor.contains("Carl's Car Rentals"))
    }
}
