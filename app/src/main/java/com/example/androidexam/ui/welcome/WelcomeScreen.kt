@file:OptIn(ExperimentalMaterial3Api::class)

package com.example.androidexam.ui.welcome

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.androidexam.R
import com.example.androidexam.ui.QuizState
import com.example.androidexam.ui.navigation.QuizScreenRoute
import com.example.androidexam.ui.quiz.QuizViewModel

/**
 * Welcome screen composable
 * This screen is used to welcome the user to the app.
 * It contains a button to start the quiz.
 * When the user clicks on the start quiz button, the create game screen is displayed.
 * @param navController the nav controller used to navigate to the create game screen
 * @param viewModel the view model used to get the current question index
 */
@Composable
fun WelcomeScreen(
    navController: NavHostController,
    viewModel: QuizViewModel = viewModel(factory = QuizViewModel.Factory)
) {
    val currentQuestionIndex = viewModel.currentQuestionIndex.intValue

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = Modifier.fillMaxSize()
    ) {
        Text(
            text = "Welcome to Fred's Pub Quiz",

            style = MaterialTheme.typography.headlineMedium.copy(fontWeight = FontWeight.Bold),
            color = MaterialTheme.colorScheme.primary
        )
        Spacer(modifier = Modifier.height(16.dp))
        Image(
            painter = painterResource(R.drawable.pubquizlogo),
            contentDescription = "pubquizlogo",
            modifier = Modifier.size(200.dp)
        )
        Spacer(modifier = Modifier.height(25.dp))

        Row(
            modifier = Modifier.padding(16.dp),
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            if (currentQuestionIndex > 0) {

                Button(
                    onClick = { navController.navigate(QuizScreenRoute.QuizScreen.routeName) },
                    modifier = Modifier
                        .height(48.dp)
                        .width(150.dp)
                ) {
                    Text(
                        "Continue",
                        style = MaterialTheme.typography.titleLarge,
                        color = MaterialTheme.colorScheme.onPrimary
                    )
                }
            }

            Button(
                onClick = { navController.navigate(QuizScreenRoute.CreateGame.routeName) },
                modifier = Modifier
                    .height(48.dp)
                    .width(300.dp)
            ) {
                Text(
                    "Start new Quiz",
                    style = MaterialTheme.typography.titleLarge,
                    color = MaterialTheme.colorScheme.onPrimary
                )
            }
        }
    }
}