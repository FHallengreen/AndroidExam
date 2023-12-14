package com.example.androidexam.data.database.result

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface QuizResultsDao {

    @Insert
    suspend fun insertQuizResult(quizResult: Result)

    @Query("SELECT * FROM quiz_results_table ORDER BY id DESC LIMIT 1")
    suspend fun getLastQuizResult(): Result

}