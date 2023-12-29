package com.example.androidexam.data.database.quiz

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.androidexam.data.database.progress.UserProgress
import com.example.androidexam.data.database.result.QuizResultsDao
import com.example.androidexam.data.database.result.Result
import com.example.androidexam.util.Converters

/**
 * Database class for the quiz
 * This is the class that is used to create the database
 * It is also used to get the database instance
 */
@Database(entities = [CachedDbQuiz::class, UserProgress::class, Result::class], version = 4, exportSchema = false)
@TypeConverters(Converters::class)
abstract class QuizDb : RoomDatabase() {

    abstract fun quizDao(): QuizDao
    abstract fun quizResultsDao(): QuizResultsDao

    companion object {
        @Volatile
        private var Instance: QuizDb? = null

        fun getDatabase(context: Context): QuizDb {
            return Instance ?: synchronized(this) {
                Room.databaseBuilder(context, QuizDb::class.java, "quiz_database")
                    .fallbackToDestructiveMigration()
                    .build()
                    .also { Instance = it }
            }
        }
    }
}
