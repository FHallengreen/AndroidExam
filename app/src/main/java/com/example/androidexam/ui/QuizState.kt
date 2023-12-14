package com.example.androidexam.ui

import com.example.androidexam.data.database.quiz.CachedDbQuiz

sealed class QuizState {
    object Loading : QuizState()
    data class QuizLoaded(val quiz: CachedDbQuiz) : QuizState()
    data class AnswerSelected(val isCorrect: Boolean, val selectedAnswer: String, val correctAnswer: String) : QuizState()
    data class Error(val message: String) : QuizState()
    object Completed: QuizState()
}
