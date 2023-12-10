package com.example.androidexam.network

import kotlinx.serialization.Serializable

@Serializable
data class ApiResponse (
    val response_code: Int,
    val results: List<ApiQuiz>
)
