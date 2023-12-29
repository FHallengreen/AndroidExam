package com.example.androidexam.network

import com.example.androidexam.model.Category
import com.example.androidexam.model.Difficulty
import com.example.androidexam.model.Questions
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * Used to get the quiz from the API by using Retrofit
 */
interface QuizApiService {
    @GET("api.php")
    suspend fun getQuiz(
        @Query("amount") amount: Int,
        @Query("category") categoryId: Int,
        @Query("difficulty") difficulty: String,
        @Query("type") type: String = "multiple",
        @Query("encode") encoding: String = "url3986"): ApiResponse
}

/// This is a helper function that converts the API call to a Flow
fun QuizApiService.getQuizAsFlow(categoryId: Int, amount: Int, difficulty: String): Flow<List<ApiQuiz>> = flow {
    emit(getQuiz(categoryId,amount,difficulty).results)
}