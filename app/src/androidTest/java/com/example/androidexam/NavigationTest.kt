package com.example.androidexam

import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onAllNodesWithTag
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performTextInput
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.ComposeNavigator
import androidx.navigation.testing.TestNavHostController
import com.example.androidexam.ui.navigation.NavComponent
import com.example.androidexam.ui.navigation.QuizScreenRoute
import com.example.androidexam.ui.quiz.QuizViewModel
import com.example.androidexam.ui.welcome.WelcomeScreen
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class NavigationTest {

    /// Create a compose test rule
    @get:Rule
    val composeTestRule = createComposeRule()
    private lateinit var navController: TestNavHostController

    /// Setup the navigation host
    /// The navigation host is a composable that is used to navigate between screens
    @Before
    fun setupAppNavHost() {
        composeTestRule.setContent {
            navController = TestNavHostController(LocalContext.current)
            navController.navigatorProvider.addNavigator(ComposeNavigator())
            NavComponent(navController = navController)
        }
    }


    /// Tests that the welcome screen is displayed when the app is launched
    @Test
    fun verifyStartDestination() {
        composeTestRule
            .onNodeWithText("Welcome to Fred's Pub Quiz")
            .assertIsDisplayed()
    }

    /// Test the navigation from the welcome screen to the create quiz screen
    @Test
    fun testCreateGameButton() {
        composeTestRule.onNodeWithText("Start new Quiz").performClick()
        composeTestRule
            .onNodeWithText("Create a new quiz")
            .assertIsDisplayed()
    }

    /// Test the navigation from the welcome screen to the create quiz screen and back
    @Test
    fun testCreateGameButtonAndBackButton() {
        composeTestRule.onNodeWithText("Start new Quiz").performClick()
        composeTestRule
            .onNodeWithText("Create a new quiz")
            .assertIsDisplayed()
        composeTestRule
            .onNodeWithText("Back")
            .performClick()
        composeTestRule
            .onNodeWithText("Welcome to Fred's Pub Quiz")
            .assertIsDisplayed()
    }

}