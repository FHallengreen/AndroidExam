package com.example.androidexam.data

import android.content.Context
import com.example.androidexam.data.database.QuizDb
import com.example.androidexam.network.QuizApiService
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import okhttp3.MediaType.Companion.toMediaType
import retrofit2.Retrofit
import kotlinx.serialization.json.Json
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor

interface AppContainer {
    val quizRepository: QuizRepository
}

class DefaultAppContainer(private val context: Context) : AppContainer {

    private val baseUrl = "https://opentdb.com/"
    private val loggingInterceptor = HttpLoggingInterceptor().apply {
        level = HttpLoggingInterceptor.Level.BODY // You can change the level as needed
    }

    // Create an OkHttpClient and add the logging interceptor
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


}