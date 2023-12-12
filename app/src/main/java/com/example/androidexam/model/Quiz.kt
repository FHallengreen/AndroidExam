package com.example.androidexam.model

import kotlinx.serialization.Serializable

@Serializable
data class Quiz (
    val id: Int,
    val question: String,
    val difficulty: String,
    val category: String,
    val correctAnswer: String,
    val incorrectAnswers: List<String>
)