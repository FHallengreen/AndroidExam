package com.example.androidexam.data.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface QuizDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(quiz: List<CachedDbQuiz>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertUserProgress(progress: UserProgressDb)

    @Update
    suspend fun updateUserProgress(progress: UserProgressDb)

    //select correct correctAnswers from user_progress_table
    @Query("SELECT correctAnswers FROM user_progress_table WHERE id = 1")
    fun getCorrectAnswers(): Int

    @Update
    suspend fun updateQuiz(quiz: CachedDbQuiz)

    @Delete
    suspend fun deleteQuiz(quiz: CachedDbQuiz)

    // Delete all quizzes in the database
    @Query("DELETE FROM cached_quiz_table")
    suspend fun deleteAllQuizzes()

    @Query("SELECT * from cached_quiz_table")
    fun getAllItems(): Flow<List<CachedDbQuiz>>

    @Query("SELECT COUNT(*) FROM cached_quiz_table")
    fun count(): Int

    // Get the current question index val progress: Int,
    @Query("SELECT progress FROM user_progress_table WHERE id = 1")
    fun getCurrentQuestionIndex(): Int

}