package com.example.androidexam.ui.completedquiz


import androidx.compose.runtime.mutableStateOf

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.androidexam.QuizApplication
import com.example.androidexam.data.QuizRepository
import kotlinx.coroutines.launch
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableIntStateOf
import com.example.androidexam.data.database.results.QuizResultsDb
import com.example.androidexam.ui.QuizState


class CompletedQuizViewModel(private val quizRepository: QuizRepository) : ViewModel() {


    private val _lastQuizResult = mutableStateOf<QuizResultsDb?>(null)
    val lastQuizResult: State<QuizResultsDb?> = _lastQuizResult

    init {
        getLastResult()
    }


    private fun getLastResult() {
        viewModelScope.launch {
            val result = quizRepository.getLastQuizResult()
            _lastQuizResult.value = result
        }
    }

    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val application = (this[APPLICATION_KEY] as QuizApplication)
                val quizRepository = application.container.quizRepository
                CompletedQuizViewModel(quizRepository = quizRepository)
            }
        }
    }

}