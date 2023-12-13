package com.example.androidexam.ui.createquiz

sealed interface QuizApiState {

    object Idle : QuizApiState
    object Success : QuizApiState
    object Error : QuizApiState
    object Loading : QuizApiState
}