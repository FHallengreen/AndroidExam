package com.example.androidexam.data.database

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName="completed_quiz_table")
data class CompletedQuiz (
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val questions: String,
    val difficulty: String,
    val category: String,
    val correctAnswers: Int,
    //TODO Add more fields like time.
)
