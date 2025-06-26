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
import com.example.vitesse.data.model.ApplicantFts
import extensions.stripAccents
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.util.concurrent.Executors

/**
 * The Room database for the Vitesse app.
 *
 * This database holds entities for [Applicant], and provides DAOs for accessing them.
 * It also defines a singleton pattern and a callback to populate the database on first creation.
 *
 * Annotated with [@Database] to specify the entities and version.
 * Annotated with [@TypeConverters] to handle custom data types like [LocalDate].
 */
@Database(entities = [Applicant::class, ApplicantFts::class], version = 1, exportSchema = true)
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
                    .setQueryCallback({ sqlQuery, bindArgs ->
                        Log.d("SQL_QUERY", "SQL: $sqlQuery args: $bindArgs")
                    }, Executors.newSingleThreadExecutor())
                    .addCallback(VitesseDatabaseCallback(coroutineScope))
                    .build()
                INSTANCE = instance
                instance
            }
        }

        /**
         * Populates the Room database with a predefined list of [Applicant] entries.
         *
         * This method is typically used for initial setup or instrumented testing, providing sample data
         * to work with in the application.
         *
         * Each applicant is inserted using the [ApplicantDao.insertApplicant] method after normalizing
         * the first and last names (accents removed) for compatibility with full-text search (FTS).
         *
         * **Note:** Requires API level [Build.VERSION_CODES.O] or higher due to the usage of [java.time.LocalDate].
         *
         * @param applicantDao The [ApplicantDao] through which applicant data is inserted into the database.
         *
         * @throws Exception if the database operation fails (e.g., due to I/O or Room exceptions).
         */
        @RequiresApi(Build.VERSION_CODES.O)
        suspend fun populateDatabase(applicantDao: ApplicantDao) {
            val prepopulatedApplicants = listOf(
                Applicant(
                    firstName = "Olivier",
                    lastName = "Marteaux",
                    phone = "06 32 84 92 70",
                    email = "olivier_marteaux@hotmail.com",
                    birthDate = LocalDate.of(1984, 3, 1),
                    salary = 40000.0,
                    note = "Olivier est un ingénieur expérimenté en reconversion vers le développement Android. Grâce à son parcours structuré et rigoureux dans l'industrie spatiale, il combine de solides compétences en architecture technique avec une passion pour les technologies mobiles, Jetpack Compose et Kotlin. Curieux, autonome et impliqué, il s’investit pleinement dans ses projets, comme en témoigne son portfolio GitHub.",
                    photoUri = "file:///android_asset/olivier_marteaux.jpg",
                    isFavorite = true
                ),
                Applicant(
                    firstName = "Camille",
                    lastName = "Dupont",
                    phone = "06 12 34 56 78",
                    email = "camille.dupont@example.com",
                    birthDate = LocalDate.of(1992, 3, 12),
                    salary = 35000.0,
                    note = "Camille is a proactive developer who enjoys collaborating on challenging problems. She has a keen eye for detail and a strong understanding of user-centered design principles.",
                    photoUri = "https://randomuser.me/api/portraits/women/1.jpg",
                    isFavorite = false
                ),
                Applicant(
                    firstName = "Julien",
                    lastName = "Martin",
                    phone = "07 89 45 23 67",
                    email = "julien.martin@example.com",
                    birthDate = LocalDate.of(1988, 11, 5),
                    salary = 42000.0,
                    note = "Julien brings over ten years of experience in software architecture. He communicates ideas clearly and is always looking for ways to optimize performance in large-scale systems.",
                    photoUri = "https://randomuser.me/api/portraits/men/2.jpg",
                    isFavorite = true
                ),
                Applicant(
                    firstName = "Élise",
                    lastName = "Moreau",
                    phone = "06 98 76 54 32",
                    email = "elise.moreau@example.com",
                    birthDate = LocalDate.of(1995, 7, 19),
                    salary = 39000.0,
                    note = "Élise is passionate about front-end development and design systems. She consistently delivers polished interfaces and is enthusiastic about accessibility and clean code standards.",
                    photoUri = "https://randomuser.me/api/portraits/women/3.jpg",
                    isFavorite = false
                ),
                Applicant(
                    firstName = "Lucas",
                    lastName = "Girard",
                    phone = "07 64 32 18 90",
                    email = "lucas.girard@example.com",
                    birthDate = LocalDate.of(1990, 1, 25),
                    salary = 46000.0,
                    note = "Lucas is a results-driven developer with a strong background in cloud infrastructure. His expertise with CI/CD pipelines enhances productivity across teams he collaborates with.",
                    photoUri = "https://randomuser.me/api/portraits/men/4.jpg",
                    isFavorite = true
                ),
                Applicant(
                    firstName = "Chloé",
                    lastName = "Renard",
                    phone = "06 22 44 88 33",
                    email = "chloe.renard@example.com",
                    birthDate = LocalDate.of(1993, 5, 10),
                    salary = 37500.0,
                    note = "Chloé is known for her ability to manage complex projects with ease. Her organizational skills and clear communication ensure smooth collaboration across all departments.",
                    photoUri = "https://randomuser.me/api/portraits/women/5.jpg",
                    isFavorite = false
                ),
                Applicant(
                    firstName = "Mathis",
                    lastName = "Leclerc",
                    phone = "07 11 22 33 44",
                    email = "mathis.leclerc@example.com",
                    birthDate = LocalDate.of(1987, 8, 3),
                    salary = 48000.0,
                    note = "Mathis combines strong analytical thinking with deep technical skills. He has been instrumental in optimizing legacy systems and mentoring junior team members.",
                    photoUri = "https://randomuser.me/api/portraits/men/6.jpg",
                    isFavorite = false
                ),
                Applicant(
                    firstName = "Claire",
                    lastName = "Benoit",
                    phone = "06 77 55 44 33",
                    email = "claire.benoit@example.com",
                    birthDate = LocalDate.of(1991, 4, 28),
                    salary = 41000.0,
                    note = "Claire excels in agile environments where adaptability is key. She values continuous learning and often contributes to internal knowledge-sharing sessions.",
                    photoUri = "https://randomuser.me/api/portraits/women/7.jpg",
                    isFavorite = true
                ),
                Applicant(
                    firstName = "Hugo",
                    lastName = "Marchand",
                    phone = "07 88 99 11 22",
                    email = "hugo.marchand@example.com",
                    birthDate = LocalDate.of(1994, 10, 15),
                    salary = 44000.0,
                    note = "Hugo is a strong team player who always steps up when deadlines approach. He thrives under pressure and consistently delivers quality code on time.",
                    photoUri = "https://randomuser.me/api/portraits/men/8.jpg",
                    isFavorite = false
                ),
                Applicant(
                    firstName = "Sophie",
                    lastName = "Perrot",
                    phone = "06 33 66 99 00",
                    email = "sophie.perrot@example.com",
                    birthDate = LocalDate.of(1996, 2, 8),
                    salary = 37000.0,
                    note = "Sophie is a full-stack developer with a flair for UX. Her strong communication skills and empathy make her a great bridge between dev and design teams.",
                    photoUri = "https://randomuser.me/api/portraits/women/9.jpg",
                    isFavorite = false
                ),
                Applicant(
                    firstName = "Antoine",
                    lastName = "Lemoine",
                    phone = "07 55 44 33 22",
                    email = "antoine.lemoine@example.com",
                    birthDate = LocalDate.of(1989, 6, 20),
                    salary = 43000.0,
                    note = "Antoine brings both technical leadership and people skills. He enjoys pair programming, teaching, and empowering junior developers to gain confidence and autonomy.",
                    photoUri = "https://randomuser.me/api/portraits/men/10.jpg",
                    isFavorite = true
                ),
                Applicant(
                    firstName = "Alice",
                    lastName = "Johnson",
                    phone = "+1-514-555-1234",
                    email = "alice.johnson@example.com",
                    birthDate = LocalDate.of(1990, 5, 14),
                    salary = 60000.0,
                    note = "Strong Java and Kotlin background.",
                    photoUri = "https://randomuser.me/api/portraits/women/12.jpg",
                    isFavorite = true
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
                    isFavorite = false
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
                    isFavorite = true
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
                    isFavorite = false
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
                    isFavorite = true
                )
            )
            prepopulatedApplicants.forEach { applicant ->
                applicantDao.insertApplicant(applicant.copy(
                    normalizedFirstName = applicant.firstName.stripAccents(),
                    normalizedLastName = applicant.lastName.stripAccents()
                ))
            }
        }
    }
}