package com.example.androidexam.data.database.results

import androidx.room.Entity
import androidx.room.PrimaryKey

/// QuizResultsDb is a data class that represents the quiz_results_table in the database.
@Entity(tableName = "quiz_results_table")
data class QuizResultsDb(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val category: String,
    val totalQuestions: Int,
    val correctAnswers: Int
)
