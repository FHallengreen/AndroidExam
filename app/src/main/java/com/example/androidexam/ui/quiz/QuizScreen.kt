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
import com.example.androidexam.data.database.CachedDbQuiz
import com.example.androidexam.ui.quiz.QuizViewModel

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
            is QuizViewModel.QuizState.Loading -> {CircularProgressIndicator()}
            is QuizViewModel.QuizState.QuizLoaded -> QuizContent(
                quizList = viewModel.quizList,
                currentQuestionIndex = viewModel.currentQuestionIndex.value,
                viewModel = viewModel
            )
            is QuizViewModel.QuizState.Error -> { Text("Error: ${(quizState as QuizViewModel.QuizState.Error).message}") }
            is QuizViewModel.QuizState.AnswerSelected -> ShowFeedback(quizState as QuizViewModel.QuizState.AnswerSelected, viewModel)
        }
    }
}

@Composable
fun ShowFeedback(quizState: QuizViewModel.QuizState.AnswerSelected, viewModel: QuizViewModel) {

    Text(
        text = if (quizState.isCorrect) "Correct!" else "Incorrect!",
        color = if (quizState.isCorrect) Color.Green else Color.Red,
        style = MaterialTheme.typography.headlineLarge,
        textAlign = TextAlign.Center,
        modifier = Modifier.padding(top = 16.dp)
    )
    Spacer(modifier = Modifier.height(20.dp))

    Text(text = if (quizState.isCorrect) "You answered correctly. The answer was ${quizState.selectedAnswer}" else "${quizState.selectedAnswer} was not correct, " +
            " The right answer was ${quizState.correctAnswer}", textAlign = TextAlign.Center, style = MaterialTheme.typography.titleLarge)

    Spacer(modifier = Modifier.height(75.dp))

    Button(
        onClick = { viewModel.moveToNextQuestion() },
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
    ) {
        Text("Next Question")
    }
}

/// Quiz content composable
/// This composable is used to display the quiz question and answers.
/// When the user clicks on an answer, the answer is checked and the next question is displayed.
@Composable
fun QuizContent(quizList: List<CachedDbQuiz>, currentQuestionIndex: Int, viewModel: QuizViewModel) {
    val quizState by viewModel.quizState.collectAsState()

    if (quizList.isNotEmpty() && currentQuestionIndex in quizList.indices) {
        val currentQuiz = quizList[currentQuestionIndex]
        val answers = (currentQuiz.incorrectAnswers + currentQuiz.correctAnswer).shuffled()

        Text(
            text = currentQuiz.question,
            style = MaterialTheme.typography.headlineMedium,
            textAlign = TextAlign.Center
        )
        Spacer(modifier = Modifier.height(16.dp))

        answers.forEach { answer ->
            val isCorrectAnswer = answer == currentQuiz.correctAnswer
            val isSelectedAnswer =
                quizState is QuizViewModel.QuizState.AnswerSelected && answer == (quizState as QuizViewModel.QuizState.AnswerSelected).selectedAnswer

            Button(
                onClick = {
                    if (quizState !is QuizViewModel.QuizState.AnswerSelected) viewModel.onAnswerSelected(
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

