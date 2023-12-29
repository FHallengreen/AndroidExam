package com.example.androidexam.data.database.quiz

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.androidexam.data.database.progress.UserProgress
import kotlinx.coroutines.flow.Flow


/**
 * Data access object for the quiz
 * This is the interface that is used to access the database
 */
@Dao
interface QuizDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(quiz: List<CachedDbQuiz>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertUserProgress(progress: UserProgress)

    @Update
    suspend fun updateUserProgress(progress: UserProgress)

    @Query("SELECT correctAnswers FROM user_progress_table")
    fun getCorrectAnswers(): Int

    @Update
    suspend fun updateQuiz(quiz: CachedDbQuiz)

    @Query("DELETE FROM cached_quiz_table")
    suspend fun deleteAllQuizzes()

    @Query("SELECT * from cached_quiz_table")
    fun getAllItems(): Flow<List<CachedDbQuiz>>

    @Query("SELECT COUNT(*) FROM cached_quiz_table")
    fun count(): Int

    @Query("SELECT progress FROM user_progress_table WHERE id = 1")
    fun getCurrentQuestionIndex(): Int

}