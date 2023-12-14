package com.example.androidexam.fake

import com.example.androidexam.data.database.quiz.CachedDbQuiz
import com.example.androidexam.data.database.quiz.QuizDao
import com.example.androidexam.data.database.progress.UserProgress
import com.example.androidexam.data.database.result.QuizResultsDao
import com.example.androidexam.data.database.result.Result

class FakeResultDao: QuizResultsDao {
    private val quizResults = mutableListOf<Result>()

    override suspend fun insertQuizResult(quizResult: Result) {
        quizResults.add(quizResult)
    }

    override suspend fun getLastQuizResult(): Result {
        return quizResults.lastOrNull() ?: throw NoSuchElementException("No Quiz Result found")
    }

}