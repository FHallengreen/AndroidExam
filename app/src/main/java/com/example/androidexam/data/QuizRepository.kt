package com.example.androidexam.data

import com.example.androidexam.data.database.QuizDao
import com.example.androidexam.network.QuizApiService

interface QuizRepository {


}

class CachingQuizRepository(
    private val quizDao: QuizDao, private val quizApiService: QuizApiService
) : QuizRepository {

}