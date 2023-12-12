package com.example.androidexam

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.androidexam.ui.createquiz.CreateGame
import com.example.androidexam.ui.welcome.WelcomeScreen
import com.example.androidexam.ui.theme.AndroidExamTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AndroidExamTheme {
                val navController = rememberNavController()
                NavHost(navController = navController, startDestination = "welcome") {
                    composable("welcome") {
                        WelcomeScreen(
                            onStartQuiz = { navController.navigate("createGame") },
                        )
                    }
                    composable("createGame") {
                        CreateGame(navController)
                    }
                }

            }
        }
    }
}



