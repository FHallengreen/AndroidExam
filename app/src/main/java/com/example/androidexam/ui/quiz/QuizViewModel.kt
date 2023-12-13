package com.example.androidexam.ui.quiz

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
import com.example.androidexam.data.QuizRepository
import com.example.androidexam.data.database.CachedDbQuiz
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class QuizViewModel(private val quizRepository: QuizRepository) : ViewModel() {

    private val _quizState = MutableStateFlow<QuizState>(QuizState.Loading)
    val quizState: StateFlow<QuizState> = _quizState

    var currentQuestionIndex = mutableStateOf(0)
        private set
    var quizList by mutableStateOf<List<CachedDbQuiz>>(listOf())
        private set
    init {
        loadQuiz()
        getQuizIndex()
    }

    private fun getQuizIndex() {
        viewModelScope.launch {
            currentQuestionIndex.value = quizRepository.getQuizIndex()
            Log.e("QuizViewModel", "getQuizIndex: ${currentQuestionIndex.value}")
        }
    }

    /// This function is called when the user clicks on the "Delete quiz" button.
    /// The quiz is deleted from the database.
    private fun loadQuiz() {
        viewModelScope.launch {
            try {
                quizRepository.fetchQuizFromDatabase().collect { quizzes ->
                    if (quizzes.isNotEmpty()) {
                        withContext(Dispatchers.Main) {
                            quizList = quizzes
                            _quizState.value = QuizState.QuizLoaded(quizzes[currentQuestionIndex.value])
                        }
                    } else {
                        withContext(Dispatchers.Main) {
                            _quizState.value = QuizState.Error("No quizzes found.")
                        }
                    }
                }
            } catch (e: Exception) {
                withContext(Dispatchers.Main) {
                    _quizState.value = QuizState.Error("Error loading quizzes: ${e.message}")
                }
            }
        }
    }

    /// This function is called when the user clicks on an answer.
    /// The answer is checked and the next question is displayed.
    fun onAnswerSelected(selectedAnswer: String) {
        viewModelScope.launch {
            checkAnswer(selectedAnswer)
        }
    }

    /// This function is called when the user clicks on an answer.
    /// The answer is checked and the next question is displayed.
    /// The user's progress is saved in the database.
    private suspend fun checkAnswer(selectedAnswer: String) {
        val correctAnswers = quizRepository.getCorrectAnswers()
        val currentQuiz = quizList[currentQuestionIndex.value]
        val isCorrect = selectedAnswer == currentQuiz.correctAnswer
        _quizState.value = QuizState.AnswerSelected(isCorrect, selectedAnswer, currentQuiz.correctAnswer)
        if (isCorrect) {
            quizRepository.saveProgress(currentQuestionIndex.value + 1, correctAnswers + 1)
            currentQuestionIndex.value += 1
        } else {
            quizRepository.saveProgress(currentQuestionIndex.value + 1, correctAnswers)
            currentQuestionIndex.value += 1
        }
    }

    fun moveToNextQuestion(){
        viewModelScope.launch {
            if (currentQuestionIndex.value < quizList.size - 1) {
                _quizState.value = QuizState.QuizLoaded(quizList[currentQuestionIndex.value])
            } else {
                _quizState.value = QuizState.Error("No more questions")
            }
        }
    }

    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val application = (this[APPLICATION_KEY] as QuizApplication)
                val quizRepository = application.container.quizRepository
                QuizViewModel(quizRepository = quizRepository)
            }
        }
    }

    sealed class QuizState {
        object Loading : QuizState()
        data class QuizLoaded(val quiz: CachedDbQuiz) : QuizState()
        data class AnswerSelected(val isCorrect: Boolean, val selectedAnswer: String, val correctAnswer: String) : QuizState()
        data class Error(val message: String) : QuizState()
    }
}