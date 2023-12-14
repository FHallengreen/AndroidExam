package com.example.androidexam

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.androidexam.data.database.CachedDbQuiz
import com.example.androidexam.data.database.QuizDao
import com.example.androidexam.data.database.QuizDb
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import java.io.IOException
import kotlinx.coroutines.flow.first
import org.junit.Assert.assertEquals


@RunWith(AndroidJUnit4::class)
class QuizDaoTest {

    private lateinit var quizDao: QuizDao
    private lateinit var quizDb: QuizDb

    private var quiz1 = CachedDbQuiz(
        id = 1,
        question = "What is the capital of Norway?",
        correctAnswer = "Oslo",
        incorrectAnswers = listOf("Bergen", "Stavanger", "Trondheim"),
        difficulty = "Easy",
        category = "General Knowledge"
    )

    private var quiz2 = CachedDbQuiz(
        id = 2,
        question = "What is the capital of Sweden?",
        correctAnswer = "Stockholm",
        incorrectAnswers = listOf("Gothenburg", "Malmö", "Uppsala"),
        difficulty = "Easy",
        category = "General Knowledge"
    )

    private suspend fun addOneQuizToDb() {
        quizDao.insertAll(listOf(quiz1))
    }
    private suspend fun addTwoQuizzesToDb() {
        quizDao.insertAll(listOf(quiz1,quiz2))
    }

    @Before
    fun createDb() {
        val context: Context = ApplicationProvider.getApplicationContext()
        // Using an in-memory database because the information stored here disappears when the
        // process is killed.
        quizDb = Room.inMemoryDatabaseBuilder(context, QuizDb::class.java)
            // Allowing main thread queries, just for testing.
            .allowMainThreadQueries()
            .build()
        quizDao = quizDb.quizDao()
    }
    @After
    @Throws(IOException::class)
    fun closeDb() {
        if (this::quizDb.isInitialized) {
            quizDb.close()
        }
    }

    @Test
    @Throws(Exception::class)
    fun insertQuizIntoDB() = runBlocking{
        addOneQuizToDb()
        val allItems = quizDao.getAllItems().first()
        assertEquals(allItems[0], quiz1)

    }

    @Test
    @Throws(Exception::class)
    fun insertTwoQuizzesIntoDB() = runBlocking{
        addTwoQuizzesToDb()
        val allItems = quizDao.getAllItems().first()
        assertEquals(allItems[0], quiz1)
        assertEquals(allItems[1], quiz2)
    }


}
