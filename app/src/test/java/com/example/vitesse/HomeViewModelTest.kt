package com.example.vitesse

import com.example.vitesse.data.model.Applicant
import com.example.vitesse.data.repository.ApplicantRepository
import com.example.vitesse.fakes.FakeApplicantRepository
import com.example.vitesse.ui.screens.home.HomeUiState
import com.example.vitesse.ui.screens.home.HomeViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.TestScope
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Test

//@OptIn(ExperimentalCoroutinesApi::class)
//class HomeViewModelTest {
//
//    private val testDispatcher = StandardTestDispatcher()
//    private val testScope = TestScope(testDispatcher)
//
//    @Before
//    fun setup() {
//        Dispatchers.setMain(testDispatcher)
//    }
//
//    @After
//    fun tearDown() {
//        Dispatchers.resetMain()
//    }
//
//    @Test
//    fun `uiState should be Success when repository returns applicants`() = testScope.runTest {
//        // Given
//        val applicant = Applicant(id = 1, name = "Alice", age = 30) // adapt to your model
//        val viewModel = HomeViewModel(FakeApplicantRepository())
//        viewModel.insertApplicant(applicant)
//
//        // When
//        advanceUntilIdle()
//
//        // Then
//        assertTrue(viewModel.uiState is HomeUiState.Success)
//        val state = viewModel.uiState as HomeUiState.Success
//        assertEquals(1, state.applicants.size)
//        assertEquals("Alice", state.applicants[0].name)
//    }
//
//    @Test
//    fun `uiState should be Empty when repository returns empty list`() = testScope.runTest {
//        val fakeRepo = FakeApplicantRepository(flowOf(emptyList()))
//        val viewModel = HomeViewModel(fakeRepo)
//
//        advanceUntilIdle()
//
//        assertTrue(viewModel.uiState is HomeUiState.Empty)
//    }
//
//    @Test
//    fun `uiState should be Error when repository throws exception`() = testScope.runTest {
//        val fakeRepo = object : ApplicantRepository {
//            override fun getAllApplicants(): Flow<List<Applicant>> = flow { throw Exception("fail") }
//            override fun getApplicants(query: String): Flow<List<Applicant>> = flow { throw Exception("fail") }
//        }
//
//        val viewModel = HomeViewModel(fakeRepo)
//
//        advanceUntilIdle()
//
//        assertTrue(viewModel.uiState is HomeUiState.Error)
//    }
//}