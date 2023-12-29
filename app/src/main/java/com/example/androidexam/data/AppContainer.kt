package com.example.androidexam.data

import android.content.Context
import com.example.androidexam.data.database.quiz.CachingQuizRepository
import com.example.androidexam.data.database.quiz.QuizDb
import com.example.androidexam.data.database.quiz.QuizRepository
import com.example.androidexam.data.database.result.ApiResultRepository
import com.example.androidexam.data.database.result.ResultRepository
import com.example.androidexam.network.QuizApiService
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import okhttp3.MediaType.Companion.toMediaType
import retrofit2.Retrofit
import kotlinx.serialization.json.Json
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor

interface AppContainer {
    val quizRepository: QuizRepository
    val resultRepository: ResultRepository
}

/**
 * This is the default implementation of [AppContainer],
 * which includes [QuizRepository] and [ResultRepository]
 */
class DefaultAppContainer(private val context: Context) : AppContainer {

    private val baseUrl = "https://opentdb.com/"
    private val loggingInterceptor = HttpLoggingInterceptor().apply {
        level = HttpLoggingInterceptor.Level.BODY // You can change the level as needed
    }

    private val okHttpClient = OkHttpClient.Builder()
        .addInterceptor(loggingInterceptor)
        .build()

    private val retrofit = Retrofit.Builder()
        .addConverterFactory(
            Json.asConverterFactory("application/json".toMediaType())
        )
        .client(okHttpClient)
        .baseUrl(baseUrl)
        .build()

    private val retrofitService: QuizApiService by lazy {
        retrofit.create(QuizApiService::class.java)
    }

    override val quizRepository: QuizRepository by lazy {
        CachingQuizRepository(QuizDb.getDatabase(context = context).quizDao(), retrofitService)
    }
    override val resultRepository: ResultRepository by lazy {
        ApiResultRepository(QuizDb.getDatabase(context = context).quizResultsDao())
    }


}