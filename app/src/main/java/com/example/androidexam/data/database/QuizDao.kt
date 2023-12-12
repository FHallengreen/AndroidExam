package com.example.androidexam.data.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface QuizDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(quiz: List<CachedDbQuiz>)

 /*   @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCompletedQuiz(completedQuiz: CompletedQuiz)*/

    @Query("SELECT * from cached_quiz_table ORDER BY id ASC")
    fun getAllItems(): Flow<List<CachedDbQuiz>>

}