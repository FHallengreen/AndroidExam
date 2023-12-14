package com.example.androidexam

import com.example.androidexam.data.database.result.Result
import com.example.androidexam.fake.FakeResultDao
import com.example.androidexam.fake.FakeResultsRepository
import com.example.androidexam.ui.completedquiz.CompletedQuizViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.TestDispatcher
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runBlockingTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestWatcher
import org.junit.runner.Description

class CompletedQuizViewModelTest {

    @get:Rule
    val testDispatcherRule = TestDispatcherRule()

    private lateinit var viewModel: CompletedQuizViewModel
    private lateinit var fakeResultsRepository: FakeResultsRepository

    /// Set up the fake repository and add a quiz result
    @Before
    fun setUp() {
        fakeResultsRepository = FakeResultsRepository(FakeResultDao())
        val testQuizResult = Result(
            category = "Test Category",
            totalQuestions = 10,
            correctAnswers = 5
        )
        runBlocking {
            fakeResultsRepository.saveQuizResult(testQuizResult)
        }
        viewModel = CompletedQuizViewModel(resultRepository = fakeResultsRepository)
    }

    /// Test that the last quiz result is loaded correctly
    /// Idle the coroutine dispatcher to wait for the result
    /// Assert that the last quiz result is the same as the one we added in the setup
    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun getLastQuizResultUpdatesState() = runBlockingTest {
        val expectedQuizResult = Result(
            category = "Test Category",
            totalQuestions = 10,
            correctAnswers = 5
        )

        viewModel.getLastResult()

        advanceUntilIdle()

        assertEquals(expectedQuizResult, viewModel.lastQuizResult.value)
    }


    /// Reset the main dispatcher after the test is done
    @OptIn(ExperimentalCoroutinesApi::class)
    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }
}

/// A test rule that sets the main coroutine dispatcher to a test dispatcher
@ExperimentalCoroutinesApi
class TestDispatcherRule(
    private val testDispatcher: TestDispatcher = UnconfinedTestDispatcher()
) : TestWatcher() {
    override fun starting(description: Description) {
        Dispatchers.setMain(testDispatcher)
    }

    override fun finished(description: Description) {
        Dispatchers.resetMain()
    }
}
