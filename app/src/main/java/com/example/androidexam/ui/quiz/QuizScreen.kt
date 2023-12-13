import android.util.Log
import androidx.compose.runtime.*
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
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
            else -> { Text("Error: ${(quizState as QuizViewModel.QuizState.Error).message}") }
        }
    }
}

/// Quiz content composable
/// This composable is used to display the quiz question and answers.
/// When the user clicks on an answer, the answer is checked and the next question is displayed.
@Composable
fun QuizContent(quizList: List<CachedDbQuiz>, currentQuestionIndex: Int, viewModel: QuizViewModel) {
    if (quizList.isNotEmpty() && currentQuestionIndex in quizList.indices) {
        val currentQuiz = quizList[currentQuestionIndex]

        Text(
            text = currentQuiz.question,
            style = MaterialTheme.typography.headlineMedium,
            textAlign = TextAlign.Center
        )
        Spacer(modifier = Modifier.height(16.dp))

        val answers = (currentQuiz.incorrectAnswers + currentQuiz.correctAnswer).shuffled()

        answers.forEach { answer ->
            Button(
                onClick = { viewModel.onAnswerSelected(answer) },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 4.dp)
            ) {
                Text(answer)
            }
        }
    } else {
        Text("No more questions available.")
    }
}
