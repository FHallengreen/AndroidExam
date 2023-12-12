package com.example.androidexam.ui.createquiz

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.androidexam.QuizApplication
import com.example.androidexam.data.QuizRepository
import com.example.androidexam.model.Category
import com.example.androidexam.model.Difficulty
import com.example.androidexam.model.Questions
import kotlinx.coroutines.launch

class CreateQuizViewModel(private val quizRepository: QuizRepository) : ViewModel() {

    fun startQuiz(category: Category, questions: Questions, difficulty: Difficulty) {
        val url = buildApiUrl(category.id, questions.number, difficulty.level)
        viewModelScope.launch {
            try {

            }
            catch (e: Exception) {
                Log.e(e.message, e.stackTraceToString())
            }

        }

        // TODO: Perform API call and handle the response
    }

    private fun buildApiUrl(categoryId: Int, questions: Int, difficulty: String): String {
        return "https://opentdb.com/api.php?amount=$questions&category=$categoryId&difficulty=$difficulty&type=multiple"
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



