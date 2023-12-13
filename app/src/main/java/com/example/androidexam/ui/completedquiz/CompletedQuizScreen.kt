package com.example.androidexam.ui.completedquiz

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.androidexam.R
import com.example.androidexam.ui.quiz.QuizViewModel
import com.example.androidexam.util.decodeUrl

@Composable
fun CompletedQuizScreen(
    navController: NavHostController,
    viewModel: CompletedQuizViewModel = viewModel(factory = CompletedQuizViewModel.Factory)
) {

    val lastQuizResult = viewModel.lastQuizResult.value

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = Modifier.fillMaxWidth()
    ) {
        Spacer(modifier = Modifier.height(50.dp))

        if (lastQuizResult != null) {
            Text(text = "Quiz Completed", style = MaterialTheme.typography.displayLarge.copy
                (fontWeight = FontWeight.Bold))

            Spacer(modifier = Modifier.height(50.dp))
            Text(
                text = "Quiz Category: ${decodeUrl(lastQuizResult.category)} \n " +
                        "Questions Answered: ${lastQuizResult.totalQuestions} \n " +
                        "Correct Answers: ${lastQuizResult.correctAnswers}",
                style = MaterialTheme.typography.headlineMedium.copy(fontWeight = FontWeight.Bold),
                color = MaterialTheme.colorScheme.primary,
                textAlign = TextAlign.Center,
            )
        } else {
            Text(text = "No quiz results available")
        }

        Spacer(modifier = Modifier.height(50.dp))
        Image(
            painter = painterResource(R.drawable.pubquizlogo),
            contentDescription = null,
            modifier = Modifier.size(200.dp)
        )
        Spacer(modifier = Modifier.height(50.dp))

        Column {
            Button(
                onClick = { navController.navigate("createGame") },
                modifier = Modifier
                    .padding(16.dp)
                    .fillMaxWidth()
            ) {
                Text(text = "Start new Quiz")
            }
        }
    }
}
