package com.example.androidexam.network

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.http.GET

/// This is the interface that defines the API
interface QuizApiService {
    @GET("questions")
    suspend fun getQuestions(): ApiResponse
}

/// This is a helper function that converts the API call to a Flow
fun QuizApiService.getQuizAsFlow(): Flow<List<ApiQuiz>> = flow {
    emit(getQuestions().results)
}