package com.example.vitesse.viewModel

import androidx.lifecycle.SavedStateHandle
import com.example.vitesse.data.model.Applicant
import com.example.vitesse.data.model.ExchangeRate
import com.example.vitesse.data.model.LocalCurrency
import com.example.vitesse.data.repository.ApplicantRepository
import com.example.vitesse.data.repository.CurrencyRepository
import com.example.vitesse.ui.screens.applicantDetail.ApplicantDetailDestination
import com.example.vitesse.ui.screens.applicantDetail.ApplicantDetailViewModel
import com.example.vitesse.ui.screens.common.GetDataState
import io.mockk.coEvery
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
import org.junit.Before
import org.junit.Test
import utils.NoOpLogger


@OptIn(ExperimentalCoroutinesApi::class)
class ApplicantDetailViewModelTest {

//    private val testDispatcher = UnconfinedTestDispatcher()// for Testrunner with coverage
    private val testDispatcher = StandardTestDispatcher()

    private lateinit var applicantRepository: ApplicantRepository
    private lateinit var currencyRepository: CurrencyRepository
    private lateinit var savedStateHandle: SavedStateHandle

    private lateinit var viewModel: ApplicantDetailViewModel

    private val testApplicant = Applicant(id = 99, isFavorite = false)
    private val testExchangeRate = ExchangeRate(LocalCurrency(1.0, 1.2))

    @Before
    fun setup() {
        Dispatchers.setMain(testDispatcher)
        applicantRepository = mockk(relaxed = true)
        currencyRepository = mockk()
        savedStateHandle = SavedStateHandle(mapOf(ApplicantDetailDestination.ApplicantIdArg to 99))

        every { applicantRepository.getApplicantById(99) } returns flowOf(testApplicant)
        coEvery { currencyRepository.getExchangeRate("eur") } returns testExchangeRate

        viewModel = ApplicantDetailViewModel(
            savedStateHandle = savedStateHandle,
            applicantRepository = applicantRepository,
            currencyRepository = currencyRepository,
            logger = NoOpLogger
        )
    }

    @Test
    fun `init should load applicant and exchange rate`() = runTest {
        advanceUntilIdle()

        val applicantState = viewModel.uiState.applicant
        advanceUntilIdle()
        val rateState = viewModel.uiState.exchangeRate
        advanceUntilIdle()

        assert(applicantState is GetDataState.Success && applicantState.data == testApplicant)
        assert(rateState is GetDataState.Success && rateState.data == testExchangeRate)
    }

    @Test
    fun `toggleFavorite should flip isFavorite`() = runTest {
        viewModel.toggleFavorite()
        assert(viewModel.isFavorite)

        viewModel.toggleFavorite()
        assert(!viewModel.isFavorite)
    }

    @Test
    fun `updateApplicantFavoriteState should call update with modified favorite`() = runTest {
        advanceUntilIdle()
        viewModel.toggleFavorite() // true
        advanceUntilIdle()

        coVerify(exactly = 0) { applicantRepository.updateApplicant(any()) }
        viewModel.updateApplicantFavoriteState()
        advanceUntilIdle()

        coVerify {
            applicantRepository.updateApplicant(
                withArg {
                    assert(it.id == testApplicant.id)
                    assert(it.isFavorite)
                }
            )
        }
    }

    @Test
    fun applicantDetailViewModel_DeleteApplicant_CallRepositoryDeleteApplicant() = runTest {
        advanceUntilIdle()
        val applicant = Applicant(id = 99)
        viewModel.deleteApplicant(applicant)
        advanceUntilIdle()
        coVerify { applicantRepository.deleteApplicant(applicant) }
    }

    @Test
    fun `showCallAlertDialog updates state`() {
        viewModel.showCallAlertDialog(true)
        assert(viewModel.callPermissionAlertDialog)
    }

    @Test
    fun `showDeleteConfirmationDialog updates state`() {
        viewModel.showDeleteConfirmationDialog(true)
        assert(viewModel.deleteConfirmationDialog)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }
}