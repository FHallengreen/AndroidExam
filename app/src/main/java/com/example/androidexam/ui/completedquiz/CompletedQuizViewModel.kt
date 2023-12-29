package com.example.androidexam.ui.completedquiz


import androidx.compose.runtime.mutableStateOf

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.androidexam.QuizApplication
import kotlinx.coroutines.launch
import androidx.compose.runtime.State
import com.example.androidexam.data.database.result.Result
import com.example.androidexam.data.database.result.ResultRepository


/**
 * ViewModel for the [CompletedQuizScreen]
 * @param resultRepository Repository for accessing the database
 */
class CompletedQuizViewModel(private val resultRepository: ResultRepository) : ViewModel() {


    private val _lastQuizResult = mutableStateOf<Result?>(null)
    val lastQuizResult: State<Result?> = _lastQuizResult

    init {
        getLastResult()
    }

    /**
     * Gets the last quiz result from the database
     */
    fun getLastResult() {
        viewModelScope.launch {
            val result = resultRepository.getLastQuizResult()
            _lastQuizResult.value = result
        }
    }

    /**
     * Factory for creating the ViewModel
     */
    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val application = (this[APPLICATION_KEY] as QuizApplication)
                val resultRepository = application.container.resultRepository
                CompletedQuizViewModel(resultRepository = resultRepository)
            }
        }
    }

}