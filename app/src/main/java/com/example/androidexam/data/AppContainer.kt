package com.example.androidexam.data

import android.content.Context
import com.example.androidexam.data.database.QuizDb
import com.example.androidexam.network.QuizApiService
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import okhttp3.MediaType.Companion.toMediaType
import retrofit2.Retrofit
import kotlinx.serialization.json.Json

interface AppContainer {
    val quizRepository: QuizRepository
}

class DefaultAppContainer(private val context: Context) : AppContainer {

    private val baseUrl = "https://opentdb.com/api.php?"
    private val retrofit = Retrofit.Builder()
        .addConverterFactory(
            Json.asConverterFactory("application/json".toMediaType())
        )
        .baseUrl(baseUrl)
        .build()

    private val retrofitService: QuizApiService by lazy {
        retrofit.create(QuizApiService::class.java)
    }

    override val quizRepository: QuizRepository by lazy {
        CachingQuizRepository(QuizDb.getDatabase(context = context).quizDao(), retrofitService)
    }


}