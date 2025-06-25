package com.example.vitesse.data.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.vitesse.data.model.Applicant
import com.example.vitesse.data.model.ApplicantFts
import kotlinx.coroutines.flow.Flow

@Dao
interface ApplicantDao {
    @Query("SELECT * FROM applicant")
    fun getAllApplicants(): Flow<List<Applicant>>

    @Query("SELECT * FROM applicant WHERE id = :id")
    fun getApplicantById(id: Int): Flow<Applicant?>

// fixed: Avoid using upsert method to avoid hard-to-detect bugs
//    @Upsert
//    suspend fun upsertApplicant(applicant: Applicant)

    @Insert
    suspend fun insertApplicant(applicant: Applicant)

    // For instrumented tests only
    @Insert
    suspend fun insertApplicantFts(applicantFts: ApplicantFts)

    @Update
    suspend fun updateApplicant(applicant: Applicant)

    @Delete
    suspend fun deleteApplicant(applicant: Applicant)

    @Query("""
        SELECT applicant.* FROM applicant
        JOIN applicant_fts ON applicant.id = applicant_fts.rowid
        WHERE applicant_fts MATCH :query
    """)
    fun getApplicants(query: String): Flow<List<Applicant>>
}