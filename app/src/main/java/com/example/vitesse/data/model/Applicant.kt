package com.example.vitesse.data.model

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Fts4
import androidx.room.PrimaryKey
import java.time.LocalDate

@RequiresApi(Build.VERSION_CODES.O)
@Entity(tableName = "applicant")
data class Applicant(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    @ColumnInfo(name = "first_name") val firstName: String = "",
    @ColumnInfo(name = "last_name") val lastName: String = "",
    val phone: String = "",
    val email: String = "",
    @ColumnInfo(name = "date_of_birth") val birthDate: LocalDate = LocalDate.now(),
    val salary: Double = 0.0,
    val note: String = "",
    @ColumnInfo(name = "photo_uri") val photoUri: String = "",
    val isFavorite: Boolean = false
)

@Fts4(contentEntity = Applicant::class)
@Entity(tableName = "applicant_fts")
data class ApplicantFts(
    @ColumnInfo(name = "first_name") val firstName: String,
    @ColumnInfo(name = "last_name") val lastName: String,
)