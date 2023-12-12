package com.example.androidexam.data

import android.util.Log
import androidx.compose.ui.text.toLowerCase
import com.example.androidexam.data.database.CachedDbQuiz
import com.example.androidexam.data.database.CompletedQuiz
import com.example.androidexam.data.database.QuizDao
import com.example.androidexam.network.QuizApiService
import java.io.IOException

interface QuizRepository {

    suspend fun fetchAndCacheQuiz(categoryId: Int, amount: Int, difficulty: String)

//    suspend fun saveCompletedQuiz(completedQuiz: CompletedQuiz)

}

class CachingQuizRepository(
    private val quizDao: QuizDao, private val quizApiService: QuizApiService
) : QuizRepository {

    override suspend fun fetchAndCacheQuiz(categoryId: Int, amount: Int, difficulty: String) {
        val apiResponse = quizApiService.getQuiz(amount, categoryId, difficulty.lowercase())
        Log.i("CachingQuizRepository", "fetchAndCacheQuiz: ${apiResponse.response_code}")
        if (apiResponse.response_code != 0) {
            throw IOException("Error fetching quiz: ${apiResponse.response_code}")
        }
        try{
        val quizzes = apiResponse.results.map { apiQuiz ->
            CachedDbQuiz(
                id = 0,
                question = apiQuiz.question,
                difficulty = apiQuiz.difficulty,
                category = apiQuiz.category,
                correctAnswer = apiQuiz.correct_answer,
                incorrectAnswers = apiQuiz.incorrect_answers,
            )
        }
        quizDao.insertAll(quizzes)
        }catch (e: Exception){
            Log.e("CachingQuizRepository", "fetchAndCacheQuiz: ${e.message}")
        }
    }

  /*  override suspend fun saveCompletedQuiz(completedQuiz: CompletedQuiz) {
        quizDao.insertCompletedQuiz(completedQuiz)
    }*/

}