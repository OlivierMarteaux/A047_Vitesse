package com.example.vitesse.data.repository

import android.util.Log
import com.example.vitesse.data.dao.ApplicantDao
import com.example.vitesse.data.model.Applicant
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emptyFlow

/**
 * Repository for managing applicant-related operations.
 *
 * Acts as an abstraction layer over [ApplicantDao] to handle data access and error logging.
 *
 * @property applicantDao The DAO used to access applicant data from the local database.
 */
class ApplicantRepository (private val applicantDao: ApplicantDao) {

    /**
     * Retrieves a flow of the [Applicant] with the specified ID.
     * @param id The ID of the applicant to retrieve.
     */
    fun getApplicantById(id: Int): Flow<Applicant?> =
        try {applicantDao.getApplicantById(id)}
        catch (e: Exception) { Log.e("OM:ApplicantRepository.getApplicantById", e.message.toString()); emptyFlow()}

    /**
     * Retrieves a flow of all bookmarked [Applicant] entities.
     */
    fun getBookmarkedApplicants(): Flow<List<Applicant>> =
        try {applicantDao.getBookmarkedApplicants()}
        catch (e: Exception) { Log.e("OM:ApplicantRepository.getBookmarkedApplicants", e.message.toString()); emptyFlow()}

    /**
     * Retrieves a flow of all [Applicant] entities.
     *
     * @return A [Flow] emitting the list of applicants.
     * Logs an error and returns an empty flow if an exception occurs.
     */
    fun getAllApplicants(): Flow<List<Applicant>> =
        try {applicantDao.getAllApplicants()}
        catch (e: Exception) {
            Log.e("OM:ApplicantRepository.getAllApplicants", e.message.toString())
            emptyFlow()
        }

    /**
     * Inserts a new [Applicant] or updates it if it already exists.
     *
     * @param applicant The applicant to add.
     * Logs an error if an exception occurs.
     */
    suspend fun addApplicant(applicant: Applicant) =
        try {applicantDao.upsertApplicant(applicant)}
        catch (e: Exception) { Log.e("OM:ApplicantRepository.addApplicant", e.message.toString())}

    /**
     * Deletes the specified [Applicant] from the database.
     *
     * @param applicant The applicant to delete.
     * Logs an error if an exception occurs.
     */
    suspend fun deleteApplicant(applicant: Applicant) =
        try { applicantDao.deleteApplicant(applicant)}
        catch (e: Exception) { Log.e("OM:ApplicantRepository.deleteApplicant", e.message.toString())}
}