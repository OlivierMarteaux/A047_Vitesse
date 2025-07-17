package com.example.vitesse.viewModel

import com.example.vitesse.fakes.ErrorApplicantRepository
import com.example.vitesse.fakes.FakeApplicantRepository
import com.example.vitesse.fakes.fakeApplicantList
import com.example.vitesse.ui.screens.home.HomeUiState
import com.example.vitesse.ui.screens.home.HomeViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.TestScope
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class HomeViewModelTest {

    private val testDispatcher = StandardTestDispatcher()
    private val testScope = TestScope(testDispatcher)

    @Before
    fun setup() {
        Dispatchers.setMain(testDispatcher)

    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun homeViewModel_initWithApplicantList_SuccessUiState() = testScope.runTest {
        // Given applicants are inserted into the repository
        val applicantList = fakeApplicantList
        val fakeRepository = FakeApplicantRepository()
        val viewModel = HomeViewModel(fakeRepository)
        applicantList.forEach { fakeRepository.insertApplicant(it) }

        // When viewModel is initialized
        advanceUntilIdle()

        // Then uiState should be Success with the correct data
        assertTrue(viewModel.uiState is HomeUiState.Success)
        val state = viewModel.uiState as HomeUiState.Success
        assertEquals(applicantList.size, state.applicants.size)
        assertEquals("Alice", state.applicants[0].firstName)
    }

    @Test
    fun homeViewModel_initWithEmptyApplicantList_EmptyUiState() = testScope.runTest {
        // Given an empty repository
        val fakeRepository = FakeApplicantRepository()
        val viewModel = HomeViewModel(fakeRepository)

        // When viewModel is initialized
        advanceUntilIdle()

        // Then uiState should be Empty
        assertTrue(viewModel.uiState is HomeUiState.Empty)
    }

    @Test
    fun homeViewModel_initWithError_ErrorUiState() = testScope.runTest {
        // Given a repository that throws an exception
        val errorApplicantRepository = ErrorApplicantRepository()
        val viewModel = HomeViewModel(errorApplicantRepository)

        // When viewModel is initialized
        advanceUntilIdle()

        // Then uiState should be Error
        assertTrue(viewModel.uiState is HomeUiState.Error)
    }

}