package com.example.ui.navigation

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Badge
import androidx.compose.material.icons.filled.Campaign
import androidx.compose.material.icons.filled.Forum
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.outlined.Badge
import androidx.compose.material.icons.outlined.Campaign
import androidx.compose.material.icons.outlined.Forum
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationRail
import androidx.compose.material3.NavigationRailItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.R
import com.example.core.rules.RedStat
import com.example.ui.components.CyberBadge
import com.example.ui.components.DiceRollerModal
import com.example.ui.components.DrawerRoutes
import com.example.ui.components.RightNavigationDrawerContent
import com.example.ui.screens.AiSettingsScreen
import com.example.ui.screens.AppSettingsScreen
import com.example.ui.screens.CampaignHubScreen
import com.example.ui.screens.CharacterSheetScreen
import com.example.ui.screens.HomeHubScreen
import com.example.ui.screens.IntroSplashScreen
import com.example.ui.screens.JournalHistoryScreen
import com.example.ui.screens.NightCityAtlasScreen
import com.example.ui.screens.PlaySessionScreen
import com.example.ui.screens.RuleLibraryScreen
import com.example.ui.screens.SystemsTerminalScreen
import com.example.ui.theme.CyberAmber
import com.example.ui.theme.CyberCyan
import com.example.ui.theme.CyberOutline
import com.example.ui.theme.CyberSurface
import com.example.ui.theme.CyberVoid
import com.example.ui.theme.RedlineCrimson
import com.example.ui.viewmodel.MainViewModel
import kotlinx.coroutines.launch

object RedlineRoutes {
    const val INTRO = "intro"
    const val HOME = "home"
    const val CAMPAIGNS = "campaigns"
    const val CHARACTER = "character"
    const val SESSION = "session"
    const val SYSTEMS = "systems"
    const val RULES = "rules"
    const val ATLAS = "atlas"
    const val JOURNAL = "journal"
    const val AI_SETTINGS = "ai_settings"
    const val SETTINGS = "settings"
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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RedlineAppShell(
    viewModel: MainViewModel,
    startOnIntro: Boolean = true
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val navController = rememberNavController()
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route
        ?: if (startOnIntro) RedlineRoutes.INTRO else RedlineRoutes.HOME
    val snackbarHostState = remember { SnackbarHostState() }
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val scope = rememberCoroutineScope()

    var skipIntroOnLaunch by rememberSaveable { mutableStateOf(!startOnIntro) }
    var diceModalParams by remember { mutableStateOf<DiceModalParams?>(null) }

    LaunchedEffect(uiState.statusBannerMessage) {
        val msg = uiState.statusBannerMessage
        if (!msg.isNullOrBlank()) {
            snackbarHostState.showSnackbar(msg)
            viewModel.clearBannerMessage()
        }
    }

    val primaryDestinations = remember {
        listOf(
            NavDestinationItem(
                route = RedlineRoutes.HOME,
                labelRes = R.string.nav_home,
                selectedIcon = Icons.Filled.Home,
                unselectedIcon = Icons.Outlined.Home,
                testTag = "nav_home"
            ),
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
                labelRes = R.string.nav_play,
                selectedIcon = Icons.Filled.Forum,
                unselectedIcon = Icons.Outlined.Forum,
                testTag = "nav_play"
            )
        )
    }

    val drawerRoutes = remember {
        DrawerRoutes(
            systems = RedlineRoutes.SYSTEMS,
            compendium = RedlineRoutes.RULES,
            atlas = RedlineRoutes.ATLAS,
            journal = RedlineRoutes.JOURNAL,
            aiSettings = RedlineRoutes.AI_SETTINGS,
            settings = RedlineRoutes.SETTINGS
        )
    }

    fun navigatePrimary(route: String) {
        navController.navigate(route) {
            popUpTo(RedlineRoutes.HOME) {
                saveState = true
            }
            launchSingleTop = true
            restoreState = true
        }
    }

    fun navigateSecondary(route: String) {
        if (currentRoute == route) return
        navController.navigate(route) {
            launchSingleTop = true
        }
    }

    val isIntro = currentRoute == RedlineRoutes.INTRO
    val isPrimaryTab = primaryDestinations.any { it.route == currentRoute }

    // Fecha a gaveta ao pressionar voltar quando estiver aberta
    if (drawerState.isOpen) {
        BackHandler {
            scope.launch { drawerState.close() }
        }
    }

    // RTL wrapper apenas no ModalNavigationDrawer para habilitar gesto nativo da borda direita;
    // LayoutDirection.Ltr é restaurado imediatamente dentro do conteúdo e da gaveta.
    CompositionLocalProvider(LocalLayoutDirection provides LayoutDirection.Rtl) {
        ModalNavigationDrawer(
            drawerState = drawerState,
            gesturesEnabled = !isIntro,
            drawerContent = {
                CompositionLocalProvider(LocalLayoutDirection provides LayoutDirection.Ltr) {
                    RightNavigationDrawerContent(
                        currentRoute = currentRoute,
                        activeCampaignTitle = uiState.activeCampaign?.title,
                        activeDistrictCode = uiState.activeCampaign?.currentDistrictCode,
                        routes = drawerRoutes,
                        onSelectRoute = { route -> navigateSecondary(route) },
                        onCloseDrawer = { scope.launch { drawerState.close() } }
                    )
                }
            }
        ) {
            CompositionLocalProvider(LocalLayoutDirection provides LayoutDirection.Ltr) {
                BoxWithConstraints(modifier = Modifier.fillMaxSize()) {
                    val useNavigationRail = maxWidth >= 600.dp

                    Scaffold(
                        contentWindowInsets = WindowInsets.safeDrawing,
                        containerColor = MaterialTheme.colorScheme.background,
                        snackbarHost = { SnackbarHost(hostState = snackbarHostState) },
                        topBar = {
                            if (!isIntro) {
                                Column {
                                    TopAppBar(
                                        colors = TopAppBarDefaults.topAppBarColors(
                                            containerColor = CyberVoid,
                                            titleContentColor = MaterialTheme.colorScheme.onBackground
                                        ),
                                        title = {
                                            Row(verticalAlignment = Alignment.CenterVertically) {
                                                Text(
                                                    text = "CYBERPUNK ",
                                                    style = MaterialTheme.typography.titleMedium,
                                                    fontWeight = FontWeight.Bold,
                                                    letterSpacing = 1.sp
                                                )
                                                Text(
                                                    text = "RED",
                                                    style = MaterialTheme.typography.titleMedium,
                                                    fontWeight = FontWeight.Bold,
                                                    color = RedlineCrimson,
                                                    letterSpacing = 1.2.sp
                                                )
                                                uiState.activeCampaign?.let { camp ->
                                                    Spacer(Modifier.width(10.dp))
                                                    CyberBadge(
                                                        text = "D${camp.inGameDay} • ${camp.eurodollars}eb",
                                                        color = CyberAmber
                                                    )
                                                }
                                            }
                                        },
                                        actions = {
                                            if (uiState.activeCampaign != null) {
                                                CyberBadge(
                                                    text = uiState.activeCampaign!!.currentDistrictCode,
                                                    color = CyberCyan
                                                )
                                                Spacer(Modifier.width(4.dp))
                                            }
                                            IconButton(
                                                onClick = { scope.launch { drawerState.open() } },
                                                modifier = Modifier.testTag("open_right_drawer_button")
                                            ) {
                                                Icon(
                                                    imageVector = Icons.Filled.Menu,
                                                    contentDescription = stringResource(id = R.string.cd_open_drawer),
                                                    tint = MaterialTheme.colorScheme.onBackground
                                                )
                                            }
                                        }
                                    )
                                    HorizontalDivider(color = CyberOutline.copy(alpha = 0.7f))
                                }
                            }
                        },
                        bottomBar = {
                            if (!isIntro && !useNavigationRail && isPrimaryTab) {
                                NavigationBar(containerColor = CyberSurface) {
                                    primaryDestinations.forEach { item ->
                                        val selected = currentRoute == item.route
                                        val label = stringResource(id = item.labelRes)
                                        NavigationBarItem(
                                            selected = selected,
                                            onClick = { navigatePrimary(item.route) },
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
                        val contentModifier = if (isIntro) {
                            Modifier.fillMaxSize()
                        } else {
                            Modifier
                                .fillMaxSize()
                                .padding(innerPadding)
                        }

                        Row(modifier = contentModifier) {
                            if (!isIntro && useNavigationRail) {
                                NavigationRail(containerColor = CyberSurface) {
                                    primaryDestinations.forEach { item ->
                                        val selected = currentRoute == item.route
                                        val label = stringResource(id = item.labelRes)
                                        NavigationRailItem(
                                            selected = selected,
                                            onClick = { navigatePrimary(item.route) },
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
                                startDestination = if (startOnIntro && !skipIntroOnLaunch) {
                                    RedlineRoutes.INTRO
                                } else {
                                    RedlineRoutes.HOME
                                },
                                modifier = Modifier.weight(1f)
                            ) {
                                composable(RedlineRoutes.INTRO) {
                                    IntroSplashScreen(
                                        onEnterApp = {
                                            navController.navigate(RedlineRoutes.HOME) {
                                                popUpTo(RedlineRoutes.INTRO) { inclusive = true }
                                                launchSingleTop = true
                                            }
                                        }
                                    )
                                }

                                composable(RedlineRoutes.HOME) {
                                    HomeHubScreen(
                                        uiState = uiState,
                                        onOpenCampaigns = { navigatePrimary(RedlineRoutes.CAMPAIGNS) },
                                        onOpenCharacter = { navigatePrimary(RedlineRoutes.CHARACTER) },
                                        onOpenPlay = { navigatePrimary(RedlineRoutes.SESSION) },
                                        onOpenSystems = { navigateSecondary(RedlineRoutes.SYSTEMS) },
                                        onOpenCompendium = { navigateSecondary(RedlineRoutes.RULES) },
                                        onOpenAtlas = { navigateSecondary(RedlineRoutes.ATLAS) },
                                        onOpenDiceRoller = { stat, skillName, skillLevel ->
                                            diceModalParams = DiceModalParams(stat, skillName, skillLevel)
                                        }
                                    )
                                }

                                composable(RedlineRoutes.CAMPAIGNS) {
                                    CampaignHubScreen(
                                        uiState = uiState,
                                        onCreateCampaign = viewModel::createCampaign,
                                        onSelectCampaign = viewModel::selectCampaign,
                                        onDeleteCampaign = viewModel::deleteCampaign,
                                        onAdvanceTime = viewModel::advanceTime,
                                        onCreateSave = viewModel::createSaveSnapshot,
                                        onRestoreSave = viewModel::restoreSaveSnapshot,
                                        onDeleteSave = viewModel::deleteSaveSnapshot,
                                        onCreateCampaignFromPregen = viewModel::createCampaignFromPregen,
                                        onOpenAtlas = { navigateSecondary(RedlineRoutes.ATLAS) }
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
                                        onUpgradeRoleRankWithIp = viewModel::upgradeRoleRankWithIp,
                                        onRunTherapy = viewModel::runHumanityTherapy
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
                                        onRollRcVendor = viewModel::rollRcNightMarketVendor,
                                        onOpenAtlas = { navigateSecondary(RedlineRoutes.ATLAS) },
                                        onTriggerFlashOfLuck = viewModel::triggerFlashOfLuck,
                                        onVetoRefundFlash = viewModel::vetoRefundFlashOfLuck,
                                        onResetFlashBeat = viewModel::resetFlashOfLuckBeat,
                                        onResetFlashMission = viewModel::resetFlashOfLuckMission,
                                        onRollSoloPlusTable = viewModel::rollSoloPlusTable,
                                        onRollNpcPersonality = viewModel::rollSoloNpcPersonality,
                                        onAskSoloOracle = viewModel::askSoloOracleYesNo
                                    )
                                }

                                composable(RedlineRoutes.SYSTEMS) {
                                    BackHandler { navController.popBackStack() }
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
                                        onBuyNightMarketOffer = viewModel::buyNightMarketOffer,
                                        onRunQuietlyJackIn = viewModel::runQuietlyJackIn,
                                        onRunStealthVsBlackIce = viewModel::runStealthVsBlackIce,
                                        onRunStealthVsWatcher = viewModel::runStealthVsWatcher,
                                        onRunAgentHack = viewModel::runAgentHack,
                                        onRunHusTool = viewModel::runHusToolApp,
                                        onRunBrainwasher = viewModel::runBrainwasherApp,
                                        onDeployTheIncident = viewModel::deployTheIncidentGig,
                                        onImportDgdFaction = viewModel::importDgdFactionPack
                                    )
                                }

                                composable(RedlineRoutes.RULES) {
                                    BackHandler { navController.popBackStack() }
                                    RuleLibraryScreen(
                                        uiState = uiState,
                                        onImportPdfUri = viewModel::registerPdf,
                                        onAddRuleExcerpt = viewModel::addRuleExcerpt,
                                        onDeleteDocument = viewModel::deleteRuleDocument,
                                        onSearchQueryChange = viewModel::updateRuleSearchQuery
                                    )
                                }

                                composable(RedlineRoutes.ATLAS) {
                                    BackHandler { navController.popBackStack() }
                                    NightCityAtlasScreen(
                                        uiState = uiState,
                                        onMoveCampaignToDistrict = viewModel::setCampaignDistrict,
                                        onRollDistrictHook = viewModel::rollAtlasDistrictHook,
                                        onNavigateBack = { navController.popBackStack() }
                                    )
                                }

                                composable(RedlineRoutes.JOURNAL) {
                                    JournalHistoryScreen(
                                        uiState = uiState,
                                        onNavigateBack = { navController.popBackStack() }
                                    )
                                }

                                composable(RedlineRoutes.AI_SETTINGS) {
                                    AiSettingsScreen(
                                        uiState = uiState,
                                        onNavigateBack = { navController.popBackStack() }
                                    )
                                }

                                composable(RedlineRoutes.SETTINGS) {
                                    AppSettingsScreen(
                                        uiState = uiState,
                                        skipIntroOnLaunch = skipIntroOnLaunch,
                                        onToggleSkipIntro = { skipIntroOnLaunch = it },
                                        onReplayIntro = {
                                            navController.navigate(RedlineRoutes.INTRO) {
                                                popUpTo(navController.graph.findStartDestination().id) {
                                                    inclusive = true
                                                }
                                                launchSingleTop = true
                                            }
                                        },
                                        onOpenAiSettings = { navigateSecondary(RedlineRoutes.AI_SETTINGS) },
                                        onNavigateBack = { navController.popBackStack() }
                                    )
                                }
                            }
                        }
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
            woundPenalty = uiState.derivedMetrics?.effectiveWoundPenalty ?: 0,
            armorPenalty = uiState.derivedMetrics?.armorPenalty ?: 0,
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
