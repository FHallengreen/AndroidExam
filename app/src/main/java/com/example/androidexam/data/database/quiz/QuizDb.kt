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

/// Database class with a singleton Instance object.
@Database(entities = [CachedDbQuiz::class, UserProgress::class, Result::class], version = 4, exportSchema = false)
@TypeConverters(Converters::class)
abstract class QuizDb : RoomDatabase() {

    abstract fun quizDao(): QuizDao
    abstract fun quizResultsDao(): QuizResultsDao

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
