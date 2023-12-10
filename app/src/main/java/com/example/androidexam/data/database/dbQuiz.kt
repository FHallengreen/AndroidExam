package com.example.androidexam.data.database

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName="quiz_table")
data class dbQuiz (
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val question: String,
    val answer: String,

)