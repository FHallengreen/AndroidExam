package com.example.androidexam.data.database

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName="user_progress_table")
data class UserProgressDb (
    @PrimaryKey(autoGenerate = true)
    val id: Int = 1,
    val progress: Int,
    val correctAnswers: Int = 0,
)
