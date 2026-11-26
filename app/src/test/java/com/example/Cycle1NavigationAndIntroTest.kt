package com.example

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performScrollTo
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

@RunWith(RobolectricTestRunner::class)
@Config(sdk = [36])
class Cycle1NavigationAndIntroTest {

    @get:Rule
    val composeRule = createAndroidComposeRule<MainActivity>()

    @Test
    fun `intro screen enters home and hybrid navigation switches tabs and right drawer`() {
        composeRule.waitForIdle()
        composeRule.onNodeWithTag("intro_splash_screen").assertIsDisplayed()
        composeRule.mainClock.advanceTimeBy(1_200)
        composeRule.waitForIdle()
        composeRule.onNodeWithText("BEM-VINDO A NIGHT CITY", useUnmergedTree = true).assertExists()
        composeRule.onNodeWithTag("intro_tap_prompt", useUnmergedTree = true).assertExists()

        // Tap anywhere on intro screen to enter app immediately
        composeRule.onNodeWithTag("intro_splash_screen").performClick()
        composeRule.waitForIdle()

        // Verify 4 bottom bar areas are displayed
        composeRule.onNodeWithTag("nav_home").assertIsDisplayed()
        composeRule.onNodeWithTag("nav_campaigns").assertIsDisplayed()
        composeRule.onNodeWithTag("nav_character").assertIsDisplayed()
        composeRule.onNodeWithTag("nav_play").assertIsDisplayed()

        // Switch primary tabs
        composeRule.onNodeWithTag("nav_campaigns").performClick()
        composeRule.waitForIdle()
        composeRule.onNodeWithText("Nenhuma campanha ativa").assertExists()

        // Open right drawer and navigate to AI settings and Journal
        composeRule.onNodeWithTag("open_right_drawer_button").performClick()
        composeRule.waitForIdle()
        composeRule.onNodeWithTag("drawer_nav_ai").performScrollTo().assertIsDisplayed().performClick()
        composeRule.waitForIdle()
        composeRule.onNodeWithTag("ai_settings_screen").assertIsDisplayed()

        composeRule.onNodeWithTag("open_right_drawer_button").performClick()
        composeRule.waitForIdle()
        composeRule.onNodeWithTag("drawer_nav_journal").performScrollTo().assertIsDisplayed().performClick()
        composeRule.waitForIdle()
        composeRule.onNodeWithTag("journal_history_screen").assertIsDisplayed()
    }
}
