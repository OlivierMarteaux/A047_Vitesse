package com.example.vitesse.data

import android.content.Context
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.vitesse.data.converter.DateConverter
import com.example.vitesse.data.dao.ApplicantDao
import com.example.vitesse.data.model.Applicant
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import java.time.LocalDate

/**
 * The Room database for the Vitesse app.
 *
 * This database holds entities for [Applicant], and provides DAOs for accessing them.
 * It also defines a singleton pattern and a callback to populate the database on first creation.
 *
 * Annotated with [@Database] to specify the entities and version.
 * Annotated with [@TypeConverters] to handle custom data types like [LocalDate].
 */
@Database(entities = [Applicant::class], version = 1, exportSchema = true)
@TypeConverters(DateConverter::class)
abstract class VitesseDatabase: RoomDatabase() {

    abstract fun applicantDao(): ApplicantDao

    /**
     * A [RoomDatabase.Callback] to populate the database with initial data
     * when it is first created.
     *
     * @property scope The [CoroutineScope] used to run the population asynchronously.
     */
    private class VitesseDatabaseCallback(
        private val scope: CoroutineScope
    ) : Callback() {
        @RequiresApi(Build.VERSION_CODES.O)
        override fun onCreate(db: SupportSQLiteDatabase) {
            super.onCreate(db)
            INSTANCE?.let { database ->
                scope.launch {
                    populateDatabase(database.applicantDao())
                    Log.d("OM:VitesseDatabaseCallback", "Database populated")
                }
            }
        }
    }

    companion object {
        /**
         * A volatile singleton instance of [VitesseDatabase] to prevent race conditions.
         */
        @Volatile
        private var INSTANCE: VitesseDatabase? = null

        /**
         * Returns the singleton instance of [VitesseDatabase].
         *
         * @param context The application context.
         * @param coroutineScope The coroutine scope used for initial population.
         * @return The singleton [VitesseDatabase] instance.
         */
        fun getInstance(context: Context, coroutineScope: CoroutineScope): VitesseDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room
                    .databaseBuilder(
                        context.applicationContext,
                        VitesseDatabase::class.java,
                        "vitesse_database.db"
                    )
                    .addCallback(VitesseDatabaseCallback(coroutineScope))
                    .build()
                INSTANCE = instance
                instance
            }
        }

        @RequiresApi(Build.VERSION_CODES.O)
        suspend fun populateDatabase(applicantDao: ApplicantDao) {
            val prepopulatedApplicants = listOf(
                Applicant(
                    firstName = "Alice",
                    lastName = "Johnson",
                    phone = "+1-514-555-1234",
                    email = "alice.johnson@example.com",
                    birthDate = LocalDate.of(1990, 5, 14),
                    salary = 60000.0,
                    note = "Strong Java and Kotlin background.",
                    photoUri = "https://randomuser.me/api/portraits/women/12.jpg",
                    bookmarked = true
                ),
                Applicant(
                    firstName = "Bob",
                    lastName = "Martinez",
                    phone = "+1-438-555-9876",
                    email = "bob.martinez@example.com",
                    birthDate = LocalDate.of(1985, 8, 22),
                    salary = 72000.0,
                    note = "Experienced in full-stack web development.",
                    photoUri = "https://randomuser.me/api/portraits/men/34.jpg",
                    bookmarked = false
                ),
                Applicant(
                    firstName = "Claire",
                    lastName = "Nguyen",
                    phone = "+1-438-555-2345",
                    email = "claire.nguyen@example.com",
                    birthDate = LocalDate.of(1992, 11, 2),
                    salary = 58000.0,
                    note = "UI/UX designer with a background in front-end.",
                    photoUri = "https://randomuser.me/api/portraits/women/45.jpg",
                    bookmarked = true
                ),
                Applicant(
                    firstName = "David",
                    lastName = "Smith",
                    phone = "+1-514-555-7766",
                    email = "david.smith@example.com",
                    birthDate = LocalDate.of(1980, 3, 30),
                    salary = 90000.0,
                    note = "Senior architect, looking for leadership roles.",
                    photoUri = "https://randomuser.me/api/portraits/men/9.jpg",
                    bookmarked = false
                ),
                Applicant(
                    firstName = "Emma",
                    lastName = "Lopez",
                    phone = "+1-514-555-0000",
                    email = "emma.lopez@example.com",
                    birthDate = LocalDate.of(1995, 1, 10),
                    salary = 64000.0,
                    note = "Mobile app developer with Kotlin/Jetpack Compose.",
                    photoUri = "https://randomuser.me/api/portraits/women/27.jpg",
                    bookmarked = true
                )
            )
            prepopulatedApplicants.forEach { applicant ->
                applicantDao.upsertApplicant(applicant)
            }
        }
    }
}