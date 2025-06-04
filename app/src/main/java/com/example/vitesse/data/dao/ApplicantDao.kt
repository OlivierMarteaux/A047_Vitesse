package com.example.vitesse.data.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Query
import androidx.room.Upsert
import com.example.vitesse.data.model.Applicant
import kotlinx.coroutines.flow.Flow

@Dao
interface ApplicantDao {
    @Query("SELECT * FROM applicant")
    fun getAllApplicants(): Flow<List<Applicant>>

    @Query("SELECT * FROM applicant WHERE id = :id")
    fun getApplicantById(id: Int): Flow<Applicant?>

    @Query("SELECT * FROM applicant WHERE isFavorite = 1")
    fun getFavoriteApplicants(): Flow<List<Applicant>>

    @Upsert
    suspend fun upsertApplicant(applicant: Applicant)

    @Delete
    suspend fun deleteApplicant(applicant: Applicant)
}