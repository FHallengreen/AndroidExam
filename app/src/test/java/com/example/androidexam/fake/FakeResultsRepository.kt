package com.example.androidexam.fake

import com.example.androidexam.data.database.result.QuizResultsDao
import com.example.androidexam.data.database.result.Result
import com.example.androidexam.data.database.result.ResultRepository

class FakeResultsRepository(private val fakeResultDao: FakeResultDao) : ResultRepository {
    override suspend fun saveQuizResult(quizResult: Result) {
        fakeResultDao.insertQuizResult(quizResult)
    }

    override suspend fun getLastQuizResult(): Result {
        return fakeResultDao.getLastQuizResult()
    }
}
