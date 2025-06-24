package com.example.vitesse.data.repository

import android.util.Log
import com.example.vitesse.data.dao.ApplicantDao
import com.example.vitesse.data.model.Applicant
import extensions.stripAccents
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
     * Retrieves a flow of all [Applicant] entities.
     *
     * @return A [Flow] emitting the list of applicants.
     * Logs an error and returns an empty flow if an exception occurs.
     */
    fun getAllApplicants(): Flow<List<Applicant>> =
        try {
            applicantDao.getAllApplicants()
        } catch (e: Exception) { Log.e("OM:ApplicantRepository.getAllApplicants", e.message.toString()); emptyFlow()}

    /* fixed: Avoid using upsert method to avoid hard-to-detect bugs
    suspend fun upsertApplicant(applicant: Applicant) =
        try {applicantDao.upsertApplicant(applicant)}
        catch (e: Exception) { Log.e("OM:ApplicantRepository.addApplicant", e.message.toString())}
     */

    suspend fun insertApplicant(applicant: Applicant) =
        try {applicantDao.insertApplicant(applicant.copy(
            normalizedFirstName = applicant.firstName.stripAccents(),
            normalizedLastName = applicant.lastName.stripAccents()
        ))}
        catch (e: Exception) { Log.e("OM:ApplicantRepository.addApplicant", e.message.toString())}

    suspend fun updateApplicant(applicant: Applicant) =
        try {applicantDao.updateApplicant(applicant.copy(
            normalizedFirstName = applicant.firstName.stripAccents(),
            normalizedLastName = applicant.lastName.stripAccents()
        ))}
        catch (e: Exception) { Log.e("OM:ApplicantRepository.updateApplicant", e.message.toString())}

    /**
     * Deletes the specified [Applicant] from the database.
     *
     * @param applicant The applicant to delete.
     * Logs an error if an exception occurs.
     */
    suspend fun deleteApplicant(applicant: Applicant) =
        try {
            applicantDao.deleteApplicant(applicant)
            Log.d("OM_TAG", "ApplicantRepository.deleteApplicant(): $applicant")
        }
        catch (e: Exception) { Log.e("OM:ApplicantRepository.deleteApplicant", e.message.toString())}

    /**
     * Retrieves a flow of applicants matching the given search query.
     *
     * @param query The search query.
     * @return A [Flow] emitting the list of matching applicants.
     */
    fun getApplicants(query: String): Flow<List<Applicant>> =
        try {applicantDao.getApplicants(formatSqlQuery(query))}
        catch (e: Exception) { Log.e("OM:ApplicantRepository.getApplicants", e.message.toString()); emptyFlow()}

    private fun formatSqlQuery(rawQuery: String): String{
        return rawQuery
            .stripAccents()
            .trim()
            .lowercase()
            .split("\\s+".toRegex())           // Split by any whitespace
            .filter { it.isNotBlank() }        // Remove empty strings
            .joinToString(" ") { "$it*" }      // Append '*' to each term
    }
}