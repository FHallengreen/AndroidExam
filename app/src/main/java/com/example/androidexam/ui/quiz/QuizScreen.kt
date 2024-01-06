import android.util.Log
import androidx.compose.runtime.*
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.androidexam.data.database.quiz.CachedDbQuiz
import com.example.androidexam.ui.QuizState
import com.example.androidexam.ui.navigation.QuizScreenRoute
import com.example.androidexam.ui.quiz.QuizViewModel


/**
 * Quiz screen composable
 * This screen is used to display the quiz questions and answers.
 * @param navController the nav controller used to navigate to the completed quiz screen
 * @param viewModel the view model used to get the current question index
 */
@Composable
fun QuizScreen(
    navController: NavHostController,
    viewModel: QuizViewModel = viewModel(factory = QuizViewModel.Factory)
) {
    val quizState by viewModel.quizState.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        when (quizState) {
            is QuizState.Loading -> {
                CircularProgressIndicator()
            }

            is QuizState.QuizLoaded -> QuizContent(
                quizList = viewModel.quizList,
                currentQuestionIndex = viewModel.currentQuestionIndex.intValue,
                viewModel = viewModel
            )

            is QuizState.Error -> {
                Text("Error: ${(quizState as QuizState.Error).message}")
            }

            is QuizState.AnswerSelected -> ShowFeedback(
                quizState as QuizState.AnswerSelected,
                viewModel
            )

            is QuizState.Completed -> navController.navigate(QuizScreenRoute.CompletedQuizScreen.routeName)
        }
    }
}


/**
 * Quiz content composable
 * This composable is used to display the quiz questions and answers.
 * @param quizList the list of quiz questions
 * @param currentQuestionIndex the current question index
 * @param viewModel the view model used to get the current question index
 */
@Composable
fun QuizContent(quizList: List<CachedDbQuiz>, currentQuestionIndex: Int, viewModel: QuizViewModel) {
    val quizState by viewModel.quizState.collectAsState()

    if (quizList.isNotEmpty() && currentQuestionIndex in quizList.indices) {
        val currentQuiz = quizList[currentQuestionIndex]

        val answers by remember(currentQuiz) {
            mutableStateOf((currentQuiz.incorrectAnswers + currentQuiz.correctAnswer).shuffled())
        }


        Text(
            text = "Question ${currentQuestionIndex + 1} of ${quizList.size}",
            style = MaterialTheme.typography.headlineMedium,
            textAlign = TextAlign.Center,
            color = MaterialTheme.colorScheme.primary
        )

        Spacer(modifier = Modifier.height(10.dp))

        Text(
            text = currentQuiz.question,
            style = MaterialTheme.typography.headlineMedium,
            textAlign = TextAlign.Center
        )
        Spacer(modifier = Modifier.height(10.dp))

        answers.forEach { answer ->
            val isCorrectAnswer = answer == currentQuiz.correctAnswer
            val isSelectedAnswer =
                quizState is QuizState.AnswerSelected && answer == (quizState as QuizState.AnswerSelected).selectedAnswer

            Button(
                onClick = {
                    if (quizState !is QuizState.AnswerSelected) viewModel.onAnswerSelected(
                        answer
                    )
                },
                colors = ButtonDefaults.buttonColors(
                    containerColor = when {
                        isSelectedAnswer && isCorrectAnswer -> Color.Green
                        isSelectedAnswer && !isCorrectAnswer -> Color.Red
                        else -> MaterialTheme.colorScheme.primary
                    }
                ),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 4.dp)
            ) {
                Text(answer)
            }
        }
    }
}

/// Show feedback composable
/// This composable is used to display the feedback to the user after answering a question.
/// When the user clicks on the next question button, the next question is displayed.
@Composable
fun ShowFeedback(quizState: QuizState.AnswerSelected, viewModel: QuizViewModel) {

    Text(
        text = if (quizState.isCorrect) "Correct!" else "Incorrect!",
        color = if (quizState.isCorrect) Color.Green else Color.Red,
        style = MaterialTheme.typography.headlineLarge,
        textAlign = TextAlign.Center,
        modifier = Modifier.padding(top = 16.dp)
    )
    Spacer(modifier = Modifier.height(20.dp))

    Text(
        text = if (quizState.isCorrect) "You answered correctly. The answer was ${quizState.selectedAnswer}" else "${quizState.selectedAnswer} was not correct, " +
                "the right answer was ${quizState.correctAnswer}",
        textAlign = TextAlign.Center,
        style = MaterialTheme.typography.titleLarge
    )

    Spacer(modifier = Modifier.height(75.dp))

    Log.e("QuizScreen", "${viewModel.currentQuestionIndex.intValue} & ${viewModel.quizList.size}")
    if (viewModel.currentQuestionIndex.intValue == viewModel.quizList.size) {
        Button(
            onClick = { viewModel.completeQuiz() },
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp)
        ) {
            Text("Finish Quiz")
        }
    } else {
        Button(
            onClick = { viewModel.moveToNextQuestion() },
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp)
        ) {
            Text("Next Question")
        }
    }
}
