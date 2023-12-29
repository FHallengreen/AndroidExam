package com.example.androidexam.ui.createquiz

/**
 * Quiz api state
 * Used to represent the state of the api call
 */
sealed interface QuizApiState {

    object Idle : QuizApiState
    object Success : QuizApiState
    object Error : QuizApiState
    object Loading : QuizApiState
}