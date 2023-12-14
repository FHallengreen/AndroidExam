package com.example.androidexam.data.database.quiz

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.androidexam.data.database.progress.UserProgress
import kotlinx.coroutines.flow.Flow


/// This is the Dao for the Quiz database
/// It contains all the queries that are used to interact with the database
/// The queries are used in the QuizRepository
@Dao
interface QuizDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(quiz: List<CachedDbQuiz>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertUserProgress(progress: UserProgress)

    @Update
    suspend fun updateUserProgress(progress: UserProgress)

    //select correct correctAnswers from user_progress_table
    @Query("SELECT correctAnswers FROM user_progress_table")
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

    @Query("SELECT progress FROM user_progress_table WHERE id = 1")
    fun getCurrentQuestionIndex(): Int

}