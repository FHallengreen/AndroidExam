package com.example.androidexam.data

import android.util.Log
import com.example.androidexam.data.database.CachedDbQuiz
import com.example.androidexam.data.database.QuizDao
import com.example.androidexam.data.database.UserProgressDb
import com.example.androidexam.network.QuizApiService
import com.example.androidexam.util.decodeUrl
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.withContext
import java.io.IOException

interface QuizRepository {

    suspend fun fetchAndCacheQuiz(categoryId: Int, amount: Int, difficulty: String)

    suspend fun saveProgress(progress: Int, correctAnswers: Int)
    suspend fun fetchQuizFromDatabase(): Flow<List<CachedDbQuiz>>

    suspend fun checkIfExistingQuiz(): Boolean
    suspend fun deleteQuiz()
    suspend fun getQuizIndex(): Int

    suspend fun getCorrectAnswers(): Int

}

class CachingQuizRepository(
    private val quizDao: QuizDao, private val quizApiService: QuizApiService
) : QuizRepository {
    override suspend fun fetchQuizFromDatabase(): Flow<List<CachedDbQuiz>>{
        return quizDao.getAllItems()
    }

    override suspend fun fetchAndCacheQuiz(categoryId: Int, amount: Int, difficulty: String) {
        val apiResponse = quizApiService.getQuiz(amount, categoryId, difficulty.lowercase())
        Log.i("CachingQuizRepository", "fetchAndCacheQuiz: ${apiResponse.response_code}")
        if (apiResponse.response_code != 0) {
            throw IOException("Error fetching quiz: ${apiResponse.response_code}")
        }
        try {
            val quizzes = apiResponse.results.map { apiQuiz ->
                CachedDbQuiz(
                    id = 0,
                    question = decodeUrl(apiQuiz.question),
                    difficulty = apiQuiz.difficulty,
                    category = apiQuiz.category,
                    correctAnswer = decodeUrl(apiQuiz.correct_answer),
                    incorrectAnswers = apiQuiz.incorrect_answers.map { decodeUrl(it) },
                )
            }
            Log.e("CachingQuizRepository", "fetchAndCacheQuiz: ${quizzes.size}")
            quizDao.insertAll(quizzes)
            val initialProgress = UserProgressDb(progress = 0, correctAnswers = 0)
            quizDao.insertUserProgress(initialProgress)
        } catch (e: Exception) {
            Log.e("CachingQuizRepository", "fetchAndCacheQuiz: ${e.message}")
        }
    }

    override suspend fun saveProgress(progress: Int, correctAnswers: Int) {
        val initialProgress = UserProgressDb(progress = progress, correctAnswers = correctAnswers)
        quizDao.updateUserProgress(initialProgress)
    }
    override suspend fun checkIfExistingQuiz(): Boolean {
        return quizDao.count() != 0
    }

    override suspend fun getQuizIndex(): Int {
        return withContext(Dispatchers.IO){
            quizDao.getCurrentQuestionIndex()
        }
    }

    override suspend fun getCorrectAnswers(): Int {
        return withContext(Dispatchers.IO){
            quizDao.getCorrectAnswers()
        }
    }

    override suspend fun deleteQuiz() {
        val quizCount = quizDao.count()
        if (quizCount > 1) {
            quizDao.deleteAllQuizzes()
        } else {
            Log.i("CachingQuizRepository", "No quiz to delete")
        }

    }


}

