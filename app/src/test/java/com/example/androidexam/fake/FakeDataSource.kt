package com.example.androidexam.fake

import com.example.androidexam.data.database.quiz.CachedDbQuiz
import com.example.androidexam.data.database.result.Result
import com.example.androidexam.network.ApiQuiz

object FakeDataSource {

    const val difficulty = "Easy"
    const val category = "General Knowledge"
    const val question = "What is the name of Denmark in Danish?"
    const val correct_answer = "Danmark"
    val incorrect_answers = listOf("Denmark", "Danskland", "Havenmark")

    const val difficulty2 = "Easy"
    const val category2 = "General Knowledge"
    const val question2 = "What is the name of Sweden in Swedish?"
    const val correct_answer2 = "Sverige"
    val incorrect_answers2 = listOf("Svedala", "Sveden", "Svedmark")

    val result = Result(
        id = 1,
        category = category,
        totalQuestions = 10,
        correctAnswers = 5
    )
    val quiz = listOf(
        CachedDbQuiz(
            id = 1,
            difficulty = difficulty,
            category = category,
            question = question,
            correctAnswer = correct_answer,
            incorrectAnswers = incorrect_answers
        ),
        CachedDbQuiz(
            id = 2,
            difficulty = difficulty2,
            category = category2,
            question = question2,
            correctAnswer = correct_answer2,
            incorrectAnswers = incorrect_answers2
        )
    )

    val apiQuiz1 =
        ApiQuiz(
            category = category,
            type = "multiple",
            difficulty = difficulty,
            question = question,
            correct_answer = correct_answer,
            incorrect_answers = incorrect_answers
        )


    val apiQuiz2 =
        ApiQuiz(
            category = category2,
            type = "multiple",
            difficulty = difficulty2,
            question = question2,
            correct_answer = correct_answer2,
            incorrect_answers = incorrect_answers2
        )
}

