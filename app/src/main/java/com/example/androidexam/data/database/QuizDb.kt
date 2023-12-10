package com.example.androidexam.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

/// Database class with a singleton Instance object.
@Database(entities = [dbQuiz::class], version = 1, exportSchema = false)
abstract class QuizDb : RoomDatabase() {

    abstract fun quizDao(): QuizDao

    /// Singleton pattern
    companion object {
        @Volatile
        private var Instance: QuizDb? = null

        /// Get database instance
        fun getDatabase(context: Context): QuizDb {
            // if the Instance is not null, return it, otherwise create a new database instance.
            return Instance ?: synchronized(this) {
                Room.databaseBuilder(context, QuizDb::class.java, "quiz_database")
                    .fallbackToDestructiveMigration()
                    .build()
                    .also { Instance = it }
            }
        }
    }
}
