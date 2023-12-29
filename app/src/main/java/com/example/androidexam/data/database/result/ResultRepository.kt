package com.example.androidexam.data.database.result

interface ResultRepository {

    suspend fun saveQuizResult(quizResult: Result)

    suspend fun getLastQuizResult(): Result
}

/**
 * Repository for accessing the quiz results table in the database.
 */
class ApiResultRepository(
    private val resultDao: QuizResultsDao
) : ResultRepository {

    /// Save the quiz result to the database
    override suspend fun saveQuizResult(quizResult: Result) {
        resultDao.insertQuizResult(quizResult)
    }

    /// Get the last quiz result from the database
    override suspend fun getLastQuizResult(): Result {
        return resultDao.getLastQuizResult()
    }

}