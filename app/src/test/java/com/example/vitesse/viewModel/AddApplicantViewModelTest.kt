package com.example.vitesse.viewModel

import com.example.vitesse.data.model.Applicant
import com.example.vitesse.data.repository.ApplicantRepository
import com.example.vitesse.ui.screens.addApplicant.AddApplicantViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito.mock
import org.mockito.Mockito.verify

@OptIn(ExperimentalCoroutinesApi::class)
class AddApplicantViewModelTest {

    private lateinit var applicantRepository: ApplicantRepository
    private lateinit var viewModel: AddApplicantViewModel

    @Before
    fun setUp() {
        Dispatchers.setMain(UnconfinedTestDispatcher()) // or StandardTestDispatcher()
        applicantRepository = mock(ApplicantRepository::class.java)
        viewModel = AddApplicantViewModel(applicantRepository)
    }

    @Test
    fun addApplicantViewModel_saveNewApplicant_callsRepositoryInsertApplicant() = runTest {
        val expectedApplicant = Applicant() // default applicant
        viewModel.saveNewApplicant()

        verify(applicantRepository).insertApplicant(expectedApplicant)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }
}