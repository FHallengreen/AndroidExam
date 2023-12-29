package com.example.androidexam.network

import kotlinx.serialization.Serializable

/**
 * Data class for the response from the API
 */
@Serializable
data class ApiResponse (
    val response_code: Int,
    val results: List<ApiQuiz>
)
