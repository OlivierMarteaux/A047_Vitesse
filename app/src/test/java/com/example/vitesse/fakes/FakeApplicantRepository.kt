package com.example.vitesse.fakes

import com.example.vitesse.data.model.Applicant
import com.example.vitesse.data.repository.ApplicantRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.map

class FakeApplicantRepository : ApplicantRepository {

    private val applicantsFlow = MutableStateFlow<List<Applicant>>(emptyList())

    override fun getApplicantById(id: Int): Flow<Applicant?> {
        return applicantsFlow.map { list -> list.find { it.id == id } }
    }

    override fun getAllApplicants(): Flow<List<Applicant>> {
        return applicantsFlow
    }

    override fun getApplicants(query: String): Flow<List<Applicant>> {
        val normalizedQuery = query.trim().lowercase()
        return applicantsFlow.map { list ->
            list.filter {
                it.firstName.lowercase().contains(normalizedQuery) ||
                        it.lastName.lowercase().contains(normalizedQuery)
            }
        }
    }

    override suspend fun insertApplicant(applicant: Applicant) {
        val updatedList = applicantsFlow.value.toMutableList().apply {
            add(applicant)
        }
        applicantsFlow.value = updatedList
    }

    override suspend fun updateApplicant(applicant: Applicant) {
        val updatedList = applicantsFlow.value.map {
            if (it.id == applicant.id) applicant else it
        }
        applicantsFlow.value = updatedList
    }

    override suspend fun deleteApplicant(applicant: Applicant) {
        val updatedList = applicantsFlow.value.toMutableList().apply {
            removeIf { it.id == applicant.id }
        }
        applicantsFlow.value = updatedList
    }
}