package com.example.vitesse

import com.example.vitesse.data.dao.ApplicantDao
import com.example.vitesse.data.model.Applicant
import com.example.vitesse.data.repository.ApplicantRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito.mock
import org.mockito.kotlin.verify
import java.time.LocalDate

class ApplicantRepositoryTest {
    private val mockApplicantDao: ApplicantDao = mock()
    private lateinit var repository: ApplicantRepository
    private val testDispatcher = StandardTestDispatcher()

    private val applicant = Applicant(
        id = 1,
        firstName = "Alice",
        lastName = "Johnson",
        phone = "+1-514-555-1234",
        email = "alice.johnson@example.com",
        birthDate = LocalDate.of(1990, 5, 14),
        salary = 60000.0,
        note = "Strong Java and Kotlin background.",
        photoUri = "https://randomuser.me/api/portraits/women/12.jpg",
        isFavorite = true
    )

    @OptIn(ExperimentalCoroutinesApi::class)
    @Before
    fun setup(){
        Dispatchers.setMain(testDispatcher)
        repository = ApplicantRepository(mockApplicantDao)
    }

    @Test
    fun applicantRepository_GetAllApplicants_CallsDaoGetAllApplicants() = runTest {
        // When
        repository.getAllApplicants()
        testDispatcher.scheduler.advanceUntilIdle()
        // Then
        verify(mockApplicantDao).getAllApplicants()
    }

    @Test
    fun applicantRepository_GetIsFavoriteApplicants_CallsDaoGetIsFavoriteApplicants() = runTest {
        // When
        repository.getFavoriteApplicants()
        testDispatcher.scheduler.advanceUntilIdle()
        // Then
        verify(mockApplicantDao).getFavoriteApplicants()
    }

    @Test
    fun applicantRepository_GetApplicantById_CallsDaoGetApplicantById() = runTest {
        // When
        repository.getApplicantById(1)
        testDispatcher.scheduler.advanceUntilIdle()
        // Then
        verify(mockApplicantDao).getApplicantById(1)
    }

    @Test
    fun applicantRepository_AddApplicant_CallsDaoAddApplicant() = runTest {
        // When
        repository.upsertApplicant(applicant)
        testDispatcher.scheduler.advanceUntilIdle()
        // Then
        verify(mockApplicantDao).upsertApplicant(applicant)
    }

    @Test
    fun applicantRepository_DeleteApplicant_CallsDaoDeleteApplicant() = runTest {
        // When
        repository.deleteApplicant(applicant)
        testDispatcher.scheduler.advanceUntilIdle()
        // Then
        verify(mockApplicantDao).deleteApplicant(applicant)
    }
}