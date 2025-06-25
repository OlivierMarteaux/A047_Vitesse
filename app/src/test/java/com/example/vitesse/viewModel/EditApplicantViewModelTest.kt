package com.example.vitesse.viewModel

import androidx.lifecycle.SavedStateHandle
import com.example.vitesse.data.model.Applicant
import com.example.vitesse.data.repository.ApplicantRepository
import com.example.vitesse.ui.screens.common.GetDataState
import com.example.vitesse.ui.screens.editApplicant.EditApplicantDestination
import com.example.vitesse.ui.screens.editApplicant.EditApplicantViewModel
import io.mockk.coVerify
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.StandardTestDispatcher
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
class EditApplicantViewModelTest {

    private val testDispatcher = StandardTestDispatcher()

    private lateinit var applicantRepository: ApplicantRepository
    private lateinit var savedStateHandle: SavedStateHandle
    private lateinit var viewModel: EditApplicantViewModel

    private val testApplicant = Applicant(id = 1, firstName = "Alice", lastName = "Johnson")

    @Before
    fun setup() {
        Dispatchers.setMain(testDispatcher)

        applicantRepository = mockk(relaxed = true)
        savedStateHandle = SavedStateHandle(mapOf(EditApplicantDestination.ApplicantIdArg to 1))

        every { applicantRepository.getApplicantById(1) } returns flowOf(testApplicant)

        viewModel = EditApplicantViewModel(
            savedStateHandle = savedStateHandle,
            applicantRepository = applicantRepository
        )
    }

    @Test
    fun `uiState should be updated with applicant on init`() = runTest {
        advanceUntilIdle() // run init block

        val state = viewModel.uiState
        assert(state.applicant is GetDataState.Success)
        assertEquals((state.applicant as GetDataState.Success).data.id, 1)
        assertEquals((state.applicant as GetDataState.Success).data.firstName, "Alice")
        assertTrue(state.isSaveable)
    }

    @Test
    fun `saveEditedApplicant should call updateApplicant with correct data`() = runTest {
        advanceUntilIdle() // ensure applicant is loaded

        viewModel.saveEditedApplicant()
        advanceUntilIdle() // let saveEditedApplicant coroutine finish

        coVerify {
            applicantRepository.updateApplicant(testApplicant)
        }
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }
}