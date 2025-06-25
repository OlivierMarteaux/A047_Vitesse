package com.example.vitesse.fakes

import com.example.vitesse.data.model.Applicant
import com.example.vitesse.data.repository.ApplicantRepository

class ErrorApplicantRepository: ApplicantRepository {
    override fun getAllApplicants() = throw Exception("fail")
    override fun getApplicants(query: String) = throw Exception("fail")
    override fun getApplicantById(id: Int) = throw Exception("fail")
    override suspend fun insertApplicant(applicant: Applicant) = throw Exception("fail")
    override suspend fun updateApplicant(applicant: Applicant) = throw Exception("fail")
    override suspend fun deleteApplicant(applicant: Applicant) = throw Exception("fail")
}