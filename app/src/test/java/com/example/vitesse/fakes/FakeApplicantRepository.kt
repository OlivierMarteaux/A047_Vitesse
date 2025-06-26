package com.example.vitesse.fakes

import com.example.vitesse.data.model.Applicant
import com.example.vitesse.data.repository.ApplicantRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.map

/**
 * Fake implementation of [ApplicantRepository] used for unit testing or preview purposes.
 *
 * Stores all applicant data in-memory using [MutableStateFlow].
 */
class FakeApplicantRepository : ApplicantRepository {

    private val applicantsFlow = MutableStateFlow<List<Applicant>>(emptyList())

    /**
     * Retrieves an applicant by ID from the in-memory list.
     */
    override fun getApplicantById(id: Int): Flow<Applicant?> {
        return applicantsFlow.map { list -> list.find { it.id == id } }
    }

    /**
     * Returns all applicants from the in-memory list.
     */
    override fun getAllApplicants(): Flow<List<Applicant>> {
        return applicantsFlow
    }

    /**
     * Filters applicants whose first or last name matches the search query (case-insensitive).
     */
    override fun getApplicants(query: String): Flow<List<Applicant>> {
        val normalizedQuery = query.trim().lowercase()
        return applicantsFlow.map { list ->
            list.filter {
                it.firstName.lowercase().contains(normalizedQuery) ||
                        it.lastName.lowercase().contains(normalizedQuery)
            }
        }
    }

    /**
     * Adds a new applicant to the in-memory list.
     */
    override suspend fun insertApplicant(applicant: Applicant) {
        val updatedList = applicantsFlow.value.toMutableList().apply {
            add(applicant)
        }
        applicantsFlow.value = updatedList
    }

    /**
     * Replaces an existing applicant in the list based on matching ID.
     */
    override suspend fun updateApplicant(applicant: Applicant) {
        val updatedList = applicantsFlow.value.map {
            if (it.id == applicant.id) applicant else it
        }
        applicantsFlow.value = updatedList
    }

    /**
     * Removes an applicant from the in-memory list by ID.
     */
    override suspend fun deleteApplicant(applicant: Applicant) {
        val updatedList = applicantsFlow.value.toMutableList().apply {
            removeIf { it.id == applicant.id }
        }
        applicantsFlow.value = updatedList
    }
}