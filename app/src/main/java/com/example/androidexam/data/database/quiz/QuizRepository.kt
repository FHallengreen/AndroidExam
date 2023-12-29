package com.example.androidexam.data.database.quiz

import android.util.Log
import com.example.androidexam.data.database.progress.UserProgress
import com.example.androidexam.network.QuizApiService
import com.example.androidexam.util.decodeUrl
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext
import java.io.IOException

/// Interface for the QuizRepository
interface QuizRepository {

    suspend fun fetchAndCacheQuiz(categoryId: Int, amount: Int, difficulty: String)
    suspend fun saveProgress(progress: Int, correctAnswers: Int)
    suspend fun fetchQuizFromDatabase(): Flow<List<CachedDbQuiz>>
    suspend fun checkIfExistingQuiz(): Boolean
    suspend fun deleteQuiz()
    suspend fun getQuizIndex(): Int
    suspend fun getCorrectAnswers(): Int



}

/// Implementation of the QuizRepository
/// @param quizDao: The DAO for the quiz database
/// @param quizApiService: The API service for the quiz API
class CachingQuizRepository(
    private val quizDao: QuizDao, private val quizApiService: QuizApiService
) : QuizRepository {
    override suspend fun fetchQuizFromDatabase(): Flow<List<CachedDbQuiz>> {
        return quizDao.getAllItems()
    }

    /**
     * Fetches a quiz from the API and caches it in the database
     * @param categoryId: The category ID of the quiz
     * @param amount: The number of questions in the quiz
     * @param difficulty: The difficulty of the quiz
     * @throws IOException: If there is an error fetching the quiz
     */
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
            val initialProgress = UserProgress(progress = 0, correctAnswers = 0)
            quizDao.insertUserProgress(initialProgress)
        } catch (e: Exception) {
            Log.e("CachingQuizRepository", "fetchAndCacheQuiz: ${e.message}")
        }
    }

    /**
     * Saves the progress of the user in the database
     * @param progress: The current progress of the user
     * @param correctAnswers: The number of correct answers the user has
     */
    override suspend fun saveProgress(progress: Int, correctAnswers: Int) {
        val initialProgress = UserProgress(progress = progress, correctAnswers = correctAnswers)
        quizDao.updateUserProgress(initialProgress)
    }

    /**
     * Checks if there is an existing quiz in the database
     * @return: True if there is an existing quiz, false otherwise
     */
    override suspend fun checkIfExistingQuiz(): Boolean {
        return quizDao.count() != 0
    }

    /// Get the current question index from the database
    override suspend fun getQuizIndex(): Int {
        return withContext(Dispatchers.IO) {
            quizDao.getCurrentQuestionIndex()
        }
    }

    /// Get the number of correct answers from the database
    override suspend fun getCorrectAnswers(): Int {
        return withContext(Dispatchers.IO) {
            quizDao.getCorrectAnswers()
        }
    }

    /// Delete the quiz from the database
    override suspend fun deleteQuiz() {
        val quizCount = quizDao.count()
        if (quizCount > 1) {
            quizDao.deleteAllQuizzes()
        } else {
            Log.i("CachingQuizRepository", "No quiz to delete")
        }
    }


}




