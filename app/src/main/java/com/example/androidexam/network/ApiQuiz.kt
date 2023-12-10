package com.example.androidexam.network

import kotlinx.serialization.Serializable

@Serializable
data class ApiQuiz (
    val type: String,
    val difficulty: String,
    val category: String,
    val question: String,
    val correct_answer: String,
    val incorrect_answers: List<String>
)