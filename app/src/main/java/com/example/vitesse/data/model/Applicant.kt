package com.example.vitesse.data.model

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Fts4
import androidx.room.PrimaryKey
import java.time.LocalDate

/**
 * Represents an applicant entity stored in the Room database.
 *
 * @property id The unique ID of the applicant (auto-generated).
 * @property firstName The applicant's first name.
 * @property lastName The applicant's last name.
 * @property phone The applicant's phone number.
 * @property email The applicant's email address.
 * @property birthDate The applicant's date of birth, stored as [LocalDate]. Nullable.
 * @property salary The applicant's expected or current salary.
 * @property note Additional notes or comments about the applicant.
 * @property photoUri URI string pointing to the applicant's profile photo, if any.
 * @property isFavorite Indicates whether the applicant is marked as a favorite.
 * @property normalizedFirstName Normalized version of the first name (used for full-text search).
 * @property normalizedLastName Normalized version of the last name (used for full-text search).
 */
@RequiresApi(Build.VERSION_CODES.O)
@Entity(tableName = "applicant")
data class Applicant(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    @ColumnInfo(name = "first_name") val firstName: String = "",
    @ColumnInfo(name = "last_name") val lastName: String = "",
    val phone: String = "",
    val email: String = "",
    @ColumnInfo(name = "date_of_birth") val birthDate: LocalDate? = null,
    val salary: Double = 0.0,
    val note: String = "",
    @ColumnInfo(name = "photo_uri") val photoUri: String? = null,
    val isFavorite: Boolean = false,
    @ColumnInfo(name = "normalized_first_name") val normalizedFirstName: String = "",
    @ColumnInfo(name = "normalized_last_name") val normalizedLastName: String = ""
)

/**
 * Full-text search (FTS) entity used for efficient searching of applicants.
 *
 * This entity is linked to the [Applicant] entity and uses normalized first and last names
 * to perform full-text search operations.
 *
 * @property id The row ID from the `applicant` table; serves as the primary key.
 * @property normalizedFirstName Normalized first name used for FTS indexing.
 * @property normalizedLastName Normalized last name used for FTS indexing.
 */
@Fts4(contentEntity = Applicant::class)
@Entity(tableName = "applicant_fts")
data class ApplicantFts(
    @PrimaryKey
    @ColumnInfo(name = "rowid") val id: Int,
    @ColumnInfo(name = "normalized_first_name") val normalizedFirstName: String,
    @ColumnInfo(name = "normalized_last_name") val normalizedLastName: String,
)