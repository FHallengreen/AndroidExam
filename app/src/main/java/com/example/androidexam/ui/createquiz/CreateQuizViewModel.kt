package com.example.androidexam.ui.createquiz

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.androidexam.QuizApplication
import com.example.androidexam.data.database.quiz.QuizRepository
import com.example.androidexam.model.Category
import com.example.androidexam.model.Difficulty
import com.example.androidexam.model.Questions
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.io.IOException

/**
 * Create quiz view model
 * @param quizRepository the quiz repository
 */
class CreateQuizViewModel(private val quizRepository: QuizRepository) : ViewModel() {

    var quizApiState: QuizApiState by mutableStateOf(QuizApiState.Idle)
        private set

    /**
     * Check if there is an existing quiz in the database
     * If there is an existing quiz, delete it
     * This is done to prevent the user from starting multiple quizzes
     */
    private fun checkIfExistingQuiz() {
        viewModelScope.launch(Dispatchers.IO) {
            quizRepository.fetchQuizFromDatabase()
            quizRepository.deleteQuiz()
            Log.e("CreateQuizViewModel", "checkIfExistingQuiz: Quiz deleted")
        }
    }

    /**
     * Start quiz
     * Fetches a new quiz from the api and caches it in the database
     * @param category the category of the quiz
     * @param questions the amount of questions in the quiz
     * @param difficulty the difficulty of the quiz
     */
    fun startQuiz(category: Category, questions: Questions, difficulty: Difficulty) {
        viewModelScope.launch {
            checkIfExistingQuiz()
            quizApiState = QuizApiState.Loading
            quizApiState = try {
                quizRepository.fetchAndCacheQuiz(category.id, questions.number, difficulty.name)
                QuizApiState.Success
            } catch (e: IOException) {
                Log.e("CreateQuizViewModel", e.message ?: "Error occurred")
                QuizApiState.Error
            }
        }
    }


    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val application = (this[APPLICATION_KEY] as QuizApplication)
                val quizRepository = application.container.quizRepository
                CreateQuizViewModel(
                    quizRepository = quizRepository
                )
            }
        }
    }
}



