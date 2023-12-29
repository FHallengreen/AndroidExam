package com.example.androidexam.ui.navigation

import QuizScreen
import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.androidexam.ui.completedquiz.CompletedQuizScreen
import com.example.androidexam.ui.createquiz.CreateGame
import com.example.androidexam.ui.welcome.WelcomeScreen

/**
 * NavHost composable function that defines all the navigation in the app.
 * @param navController the navController that is used to navigate between screens
 * @param modifier Modifier
 */
@Composable
fun NavComponent(navController: NavHostController, modifier: Modifier = Modifier) {
    NavHost(
        navController = navController,
        startDestination = QuizScreenRoute.Welcome.routeName,
        modifier = modifier
    ) {
        composable(route = QuizScreenRoute.Welcome.routeName) {
            WelcomeScreen(navController)
        }
        composable(route = QuizScreenRoute.CreateGame.routeName) {
            CreateGame(navController)
        }
        composable(route = QuizScreenRoute.QuizScreen.routeName) {
            QuizScreen(navController)
        }
        composable(route = QuizScreenRoute.CompletedQuizScreen.routeName) {
            CompletedQuizScreen(navController)
        }
    }
}