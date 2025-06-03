package com.example.vitesse.data.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.time.LocalDate

@Entity(tableName = "applicant")
data class Applicant(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    @ColumnInfo(name = "first_name") val firstName: String,
    @ColumnInfo(name = "last_name") val lastName: String,
    val phone: String,
    val email: String,
    @ColumnInfo(name = "date_of_birth") val birthDate: LocalDate,
    val salary: Double,
    val note: String,
    @ColumnInfo(name = "photo_uri") val photoUri: String,
    val bookmarked: Boolean
)