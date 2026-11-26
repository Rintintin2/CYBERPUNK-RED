package com.example.ui.navigation

import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Badge
import androidx.compose.material.icons.filled.Campaign
import androidx.compose.material.icons.filled.Forum
import androidx.compose.material.icons.filled.MenuBook
import androidx.compose.material.icons.filled.Terminal
import androidx.compose.material.icons.outlined.Badge
import androidx.compose.material.icons.outlined.Campaign
import androidx.compose.material.icons.outlined.Forum
import androidx.compose.material.icons.outlined.MenuBook
import androidx.compose.material.icons.outlined.Terminal
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationRail
import androidx.compose.material3.NavigationRailItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.R
import com.example.core.rules.RedStat
import com.example.ui.components.DiceRollerModal
import com.example.ui.screens.CampaignHubScreen
import com.example.ui.screens.CharacterSheetScreen
import com.example.ui.screens.PlaySessionScreen
import com.example.ui.screens.RuleLibraryScreen
import com.example.ui.screens.SystemsTerminalScreen
import com.example.ui.theme.CyberSurface
import com.example.ui.viewmodel.MainViewModel

object RedlineRoutes {
    const val CAMPAIGNS = "campaigns"
    const val CHARACTER = "character"
    const val SESSION = "session"
    const val SYSTEMS = "systems"
    const val RULES = "rules"
}

private data class NavDestinationItem(
    val route: String,
    val labelRes: Int,
    val selectedIcon: ImageVector,
    val unselectedIcon: ImageVector,
    val testTag: String
)

private data class DiceModalParams(
    val stat: RedStat,
    val skillName: String,
    val skillLevel: Int
)

@Composable
fun RedlineAppShell(
    viewModel: MainViewModel
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val navController = rememberNavController()
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route ?: RedlineRoutes.CAMPAIGNS
    val snackbarHostState = remember { SnackbarHostState() }

    var diceModalParams by remember { mutableStateOf<DiceModalParams?>(null) }

    LaunchedEffect(uiState.statusBannerMessage) {
        val msg = uiState.statusBannerMessage
        if (!msg.isNullOrBlank()) {
            snackbarHostState.showSnackbar(msg)
            viewModel.clearBannerMessage()
        }
    }

    val destinations = remember {
        listOf(
            NavDestinationItem(
                route = RedlineRoutes.CAMPAIGNS,
                labelRes = R.string.nav_campaigns,
                selectedIcon = Icons.Filled.Campaign,
                unselectedIcon = Icons.Outlined.Campaign,
                testTag = "nav_campaigns"
            ),
            NavDestinationItem(
                route = RedlineRoutes.CHARACTER,
                labelRes = R.string.nav_character,
                selectedIcon = Icons.Filled.Badge,
                unselectedIcon = Icons.Outlined.Badge,
                testTag = "nav_character"
            ),
            NavDestinationItem(
                route = RedlineRoutes.SESSION,
                labelRes = R.string.nav_session,
                selectedIcon = Icons.Filled.Forum,
                unselectedIcon = Icons.Outlined.Forum,
                testTag = "nav_session"
            ),
            NavDestinationItem(
                route = RedlineRoutes.SYSTEMS,
                labelRes = R.string.nav_systems,
                selectedIcon = Icons.Filled.Terminal,
                unselectedIcon = Icons.Outlined.Terminal,
                testTag = "nav_systems"
            ),
            NavDestinationItem(
                route = RedlineRoutes.RULES,
                labelRes = R.string.nav_rules,
                selectedIcon = Icons.Filled.MenuBook,
                unselectedIcon = Icons.Outlined.MenuBook,
                testTag = "nav_rules"
            )
        )
    }

    fun navigateTo(route: String) {
        navController.navigate(route) {
            popUpTo(navController.graph.findStartDestination().id) {
                saveState = true
            }
            launchSingleTop = true
            restoreState = true
        }
    }

    BoxWithConstraints(modifier = Modifier.fillMaxSize()) {
        val useNavigationRail = maxWidth >= 600.dp

        Scaffold(
            contentWindowInsets = WindowInsets.safeDrawing,
            containerColor = MaterialTheme.colorScheme.background,
            snackbarHost = { SnackbarHost(hostState = snackbarHostState) },
            bottomBar = {
                if (!useNavigationRail) {
                    NavigationBar(containerColor = CyberSurface) {
                        destinations.forEach { item ->
                            val selected = currentRoute == item.route
                            val label = stringResource(id = item.labelRes)
                            NavigationBarItem(
                                selected = selected,
                                onClick = { navigateTo(item.route) },
                                icon = {
                                    Icon(
                                        imageVector = if (selected) item.selectedIcon else item.unselectedIcon,
                                        contentDescription = label
                                    )
                                },
                                label = { Text(label, maxLines = 1) },
                                modifier = Modifier.testTag(item.testTag)
                            )
                        }
                    }
                }
            }
        ) { innerPadding ->
            Row(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding)
            ) {
                if (useNavigationRail) {
                    NavigationRail(containerColor = CyberSurface) {
                        destinations.forEach { item ->
                            val selected = currentRoute == item.route
                            val label = stringResource(id = item.labelRes)
                            NavigationRailItem(
                                selected = selected,
                                onClick = { navigateTo(item.route) },
                                icon = {
                                    Icon(
                                        imageVector = if (selected) item.selectedIcon else item.unselectedIcon,
                                        contentDescription = label
                                    )
                                },
                                label = { Text(label, maxLines = 1) },
                                modifier = Modifier.testTag(item.testTag)
                            )
                        }
                    }
                }

                NavHost(
                    navController = navController,
                    startDestination = RedlineRoutes.CAMPAIGNS,
                    modifier = Modifier.weight(1f)
                ) {
                    composable(RedlineRoutes.CAMPAIGNS) {
                        CampaignHubScreen(
                            uiState = uiState,
                            onCreateCampaign = viewModel::createCampaign,
                            onSelectCampaign = viewModel::selectCampaign,
                            onDeleteCampaign = viewModel::deleteCampaign,
                            onAdvanceTime = viewModel::advanceTime,
                            onCreateSave = viewModel::createSaveSnapshot,
                            onRestoreSave = viewModel::restoreSaveSnapshot,
                            onDeleteSave = viewModel::deleteSaveSnapshot
                        )
                    }
                    composable(RedlineRoutes.CHARACTER) {
                        CharacterSheetScreen(
                            uiState = uiState,
                            onAdjustStat = viewModel::adjustStat,
                            onAdjustHp = viewModel::adjustHp,
                            onAdjustArmorSp = viewModel::adjustArmorSp,
                            onRestoreLuck = viewModel::restoreLuck,
                            onRollDeathSave = viewModel::rollDeathSave,
                            onAddSkill = viewModel::addOrUpdateSkill,
                            onUpdateSkillLevel = viewModel::updateSkillLevel,
                            onDeleteSkill = viewModel::deleteSkill,
                            onInstallCyberware = viewModel::installCyberware,
                            onToggleCyberware = viewModel::toggleCyberware,
                            onDeleteCyberware = viewModel::deleteCyberware,
                            onOpenDiceRoller = { stat, skillName, skillLevel ->
                                diceModalParams = DiceModalParams(stat, skillName, skillLevel)
                            },
                            onAwardIp = viewModel::awardImprovementPoints,
                            onUpgradeSkillWithIp = viewModel::upgradeSkillWithIp,
                            onUpgradeRoleRankWithIp = viewModel::upgradeRoleRankWithIp
                        )
                    }
                    composable(RedlineRoutes.SESSION) {
                        PlaySessionScreen(
                            uiState = uiState,
                            onSubmitAction = viewModel::submitPlayerAction,
                            onOpenDiceRoller = { stat, skillName, skillLevel ->
                                diceModalParams = DiceModalParams(stat, skillName, skillLevel)
                            },
                            onApplyDamage = viewModel::applyDamageToPrimary,
                            onAddCombatant = viewModel::addCombatant,
                            onNextTurn = viewModel::nextCombatTurn,
                            onAdjustCombatantHp = viewModel::adjustCombatantHp,
                            onClearCombat = viewModel::clearCombat,
                            onRollEncounter = viewModel::rollRandomEncounter,
                            onSpawnNpc = viewModel::spawnOfficialNpcToCombat,
                            onRollStreetPrompt = viewModel::rollStreetPrompt,
                            onRollWeather = viewModel::rollWeather,
                            onRunSalvaging = viewModel::runSalvagingWeek,
                            onRollDatepath = viewModel::rollDatepath,
                            onRollPlotHook = viewModel::rollPlotHook,
                            onRollRcVendor = viewModel::rollRcNightMarketVendor
                        )
                    }
                    composable(RedlineRoutes.SYSTEMS) {
                        SystemsTerminalScreen(
                            uiState = uiState,
                            onBuyOrAddItem = viewModel::buyOrAddItem,
                            onToggleEquipItem = viewModel::toggleItemEquipped,
                            onAdjustItemQuantity = viewModel::adjustItemQuantity,
                            onAddNetNode = viewModel::addNetNode,
                            onGenerateNetArchitecture = viewModel::generateOfficialNetArchitecture,
                            onDeployPremadeNetArchitecture = viewModel::deployPremadeNetArchitecture,
                            onRunInterfaceCheck = viewModel::runInterfaceCheck,
                            onDamageIce = viewModel::damageIceNode,
                            onToggleNetNodeCleared = viewModel::toggleNetNodeCleared,
                            onDeleteNetNode = viewModel::deleteNetNode,
                            onAddNpcFaction = viewModel::addNpcOrFaction,
                            onDeleteNpcFaction = viewModel::deleteNpcFaction,
                            onAddMission = viewModel::addMission,
                            onSetMissionStatus = viewModel::setMissionStatus,
                            onDeleteMission = viewModel::deleteMission,
                            onRunHustle = viewModel::runHustle,
                            onPayLifestyleOrHousing = viewModel::payLifestyleOrHousing,
                            onGenerateNightMarket = viewModel::generateNightMarket,
                            onBuyNightMarketOffer = viewModel::buyNightMarketOffer
                        )
                    }
                    composable(RedlineRoutes.RULES) {
                        RuleLibraryScreen(
                            uiState = uiState,
                            onImportPdfUri = viewModel::registerPdf,
                            onAddRuleExcerpt = viewModel::addRuleExcerpt,
                            onDeleteDocument = viewModel::deleteRuleDocument,
                            onSearchQueryChange = viewModel::updateRuleSearchQuery
                        )
                    }
                }
            }
        }
    }

    diceModalParams?.let { params ->
        DiceRollerModal(
            initialStat = params.stat,
            initialSkillName = params.skillName,
            initialSkillLevel = params.skillLevel,
            maxAvailableLuck = uiState.primaryCharacter?.luckCurrent ?: 0,
            woundPenalty = uiState.derivedMetrics?.woundPenalty ?: 0,
            skills = uiState.skills,
            onDismiss = { diceModalParams = null },
            onExecuteRoll = { stat, skillName, skillLevel, luckSpent, modifier, dv ->
                viewModel.performSkillCheck(
                    stat = stat,
                    skillName = skillName,
                    skillLevel = skillLevel,
                    luckToSpend = luckSpent,
                    modifier = modifier,
                    difficultyValue = dv
                )
            }
        )
    }
}
