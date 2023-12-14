package com.example.androidexam
import com.example.androidexam.fake.FakeDataSource.result
import com.example.androidexam.fake.FakeResultDao
import com.example.androidexam.fake.FakeResultsRepository
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
class FakeResultsRepositoryTest {

    private lateinit var fakeResultDao: FakeResultDao
    private lateinit var fakeResultsRepository: FakeResultsRepository

    @Before
    fun setup() {
        fakeResultDao = FakeResultDao()
        fakeResultsRepository = FakeResultsRepository(fakeResultDao)
    }

    @Test
    fun testSaveAndGetLastQuizResult() = runBlocking {
        fakeResultsRepository.saveQuizResult(result)

        val retrievedResult = fakeResultsRepository.getLastQuizResult()
        assertEquals(result, retrievedResult)
    }

}
