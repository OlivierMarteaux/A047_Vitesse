package com.example.vitesse.data.repository

import android.util.Log
import com.example.vitesse.data.dao.ApplicantDao
import com.example.vitesse.data.model.Applicant
import extensions.stripAccents
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emptyFlow
import utils.Logger

/**
 * Repository interface for accessing and managing [Applicant] data.
 *
 * This abstraction allows data to be retrieved from various sources, such as a database, network,
 * or in-memory collection (e.g., for testing).
 */
interface ApplicantRepository {
    /**
     * Retrieves a specific applicant by ID.
     *
     * @param id The unique identifier of the applicant.
     * @return A [Flow] emitting the [Applicant], or `null` if not found.
     */
    fun getApplicantById(id: Int): Flow<Applicant?>
    /**
     * Retrieves all applicants from the data source.
     *
     * @return A [Flow] emitting a list of all [Applicant]s.
     */
    fun getAllApplicants(): Flow<List<Applicant>>
    /**
     * Performs a search for applicants whose first or last name matches the given query.
     *
     * @param query The search query string.
     * @return A [Flow] emitting a list of matching [Applicant]s.
     */
    fun getApplicants(query: String): Flow<List<Applicant>>
    /**
     * Inserts a new applicant into the data source.
     *
     * @param applicant The [Applicant] to insert.
     */
    suspend fun insertApplicant(applicant: Applicant)
    /**
     * Updates an existing applicant in the data source.
     *
     * @param applicant The [Applicant] with updated fields.
     */
    suspend fun updateApplicant(applicant: Applicant)
    /**
     * Deletes an applicant from the data source.
     *
     * @param applicant The [Applicant] to remove.
     */
    suspend fun deleteApplicant(applicant: Applicant)
}

/**
 * Repository for managing applicant-related operations.
 *
 * Acts as an abstraction layer over [ApplicantDao] to handle data access and error logging.
 *
 * @property applicantDao The DAO used to access applicant data from the local database.
 */
class LocalApplicantRepository (
    private val applicantDao: ApplicantDao,
    private val logger: Logger
): ApplicantRepository {

    /**
     * Retrieves a flow of the [Applicant] with the specified ID.
     * @param id The ID of the applicant to retrieve.
     */
    override fun getApplicantById(id: Int): Flow<Applicant?> =
        try {applicantDao.getApplicantById(id)}
        catch (e: Exception) { Log.e("OM:LocalApplicantRepository.getApplicantById", e.message.toString()); emptyFlow()}

    /**
     * Retrieves a flow of all [Applicant] entities.
     *
     * @return A [Flow] emitting the list of applicants.
     * Logs an error and returns an empty flow if an exception occurs.
     */
    override fun getAllApplicants(): Flow<List<Applicant>> =
        try {
            applicantDao.getAllApplicants()
        } catch (e: Exception) { Log.e("OM:LocalApplicantRepository.getAllApplicants", e.message.toString()); emptyFlow()}

    /* fixed: Avoid using upsert method to avoid hard-to-detect bugs
    suspend fun upsertApplicant(applicant: Applicant) =
        try {applicantDao.upsertApplicant(applicant)}
        catch (e: Exception) { Log.e("OM:LocalApplicantRepository.addApplicant", e.message.toString())}
     */

    override suspend fun insertApplicant(applicant: Applicant) {
        try {
            applicantDao.insertApplicant(
                applicant.copy(
                    normalizedFirstName = applicant.firstName.stripAccents(),
                    normalizedLastName = applicant.lastName.stripAccents()
                )
            )
        } catch (e: Exception) {
            Log.e("OM:LocalApplicantRepository.addApplicant", e.message.toString())
        }
    }

    override suspend fun updateApplicant(applicant: Applicant) {
        try {
            applicantDao.updateApplicant(
                applicant.copy(
                    normalizedFirstName = applicant.firstName.stripAccents(),
                    normalizedLastName = applicant.lastName.stripAccents()
                )
            )
        } catch (e: Exception) {
            Log.e("OM:LocalApplicantRepository.updateApplicant", e.message.toString())
        }
    }

    /**
     * Deletes the specified [Applicant] from the database.
     *
     * @param applicant The applicant to delete.
     * Logs an error if an exception occurs.
     */
    override suspend fun deleteApplicant(applicant: Applicant) {
        try {
            applicantDao.deleteApplicant(applicant)
            logger.d("LocalApplicantRepository.deleteApplicant(): $applicant")
        } catch (e: Exception) {
            Log.e("OM:LocalApplicantRepository.deleteApplicant", e.message.toString())
        }
    }

    /**
     * Retrieves a flow of applicants matching the given search query.
     *
     * @param query The search query.
     * @return A [Flow] emitting the list of matching applicants.
     */
    override fun getApplicants(query: String): Flow<List<Applicant>> =
        try {
            applicantDao.getApplicants(formatSqlQuery(query))
        } catch (e: Exception) {
            Log.e("OM:LocalApplicantRepository.getApplicants", e.message.toString()); emptyFlow()
        }

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