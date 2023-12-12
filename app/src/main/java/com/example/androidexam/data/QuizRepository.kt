package com.example.androidexam.data

import com.example.androidexam.data.database.QuizDao
import com.example.androidexam.model.Quiz
import com.example.androidexam.network.QuizApiService
import kotlinx.coroutines.flow.Flow

interface QuizRepository {


}

class CachingQuizRepository(
    private val quizDao: QuizDao, private val quizApiService: QuizApiService
) : QuizRepository {



}