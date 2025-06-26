package com.example.vitesse.data.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.vitesse.data.model.Applicant
import com.example.vitesse.data.model.ApplicantFts
import kotlinx.coroutines.flow.Flow

/**
 * Data Access Object (DAO) interface for accessing [Applicant] entities in the local Room database.
 */
@Dao
interface ApplicantDao {
    /**
     * Retrieves all applicants from the database.
     *
     * @return A [Flow] that emits the list of all [Applicant]s.
     */
    @Query("SELECT * FROM applicant")
    fun getAllApplicants(): Flow<List<Applicant>>

    /**
     * Retrieves a specific applicant by their ID.
     *
     * @param id The unique identifier of the applicant.
     * @return A [Flow] that emits the [Applicant] if found, or `null` otherwise.
     */
    @Query("SELECT * FROM applicant WHERE id = :id")
    fun getApplicantById(id: Int): Flow<Applicant?>

// fixed: Avoid using upsert method to avoid hard-to-detect bugs
//    @Upsert
//    suspend fun upsertApplicant(applicant: Applicant)


    /**
     * Inserts a new applicant into the database.
     *
     * @param applicant The [Applicant] to insert.
     */
    @Insert
    suspend fun insertApplicant(applicant: Applicant)

    /**
     * Inserts a new [ApplicantFts] entry into the full-text search table.
     * This method is intended for use in instrumented tests only.
     *
     * @param applicantFts The [ApplicantFts] entity to insert into the FTS table.
     */
    @Insert
    suspend fun insertApplicantFts(applicantFts: ApplicantFts)

    /**
     * Updates an existing applicant in the database.
     *
     * @param applicant The [Applicant] with updated data.
     */
    @Update
    suspend fun updateApplicant(applicant: Applicant)

    /**
     * Deletes an applicant from the database.
     *
     * @param applicant The [Applicant] to delete.
     */
    @Delete
    suspend fun deleteApplicant(applicant: Applicant)

    /**
     * Performs a full-text search query on the applicant database.
     *
     * @param query The search query string used to match against the FTS table.
     * @return A [Flow] emitting a list of [Applicant]s whose data match the FTS query.
     */
    @Query("""
        SELECT applicant.* FROM applicant
        JOIN applicant_fts ON applicant.id = applicant_fts.rowid
        WHERE applicant_fts MATCH :query
    """)
    fun getApplicants(query: String): Flow<List<Applicant>>
}