package com.example.vitesse

import android.util.Log
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import org.junit.runner.RunWith
import androidx.test.ext.junit.runners.AndroidJUnit4
import app.cash.turbine.test
import com.example.vitesse.data.VitesseDatabase
import com.example.vitesse.data.model.Applicant
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNull
import org.junit.Before
import org.junit.Test
import java.time.LocalDate

@RunWith (AndroidJUnit4::class)
class ApplicantDaoTest {

    private lateinit var database: VitesseDatabase
    private val applicantList = listOf(
        Applicant(
            id = 1,
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
            id = 2,
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
            id = 3,
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
            id = 4,
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
            id = 5,
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

    @Before
    // Given a database and a user:
    fun createDb(){
        database = Room
            .inMemoryDatabaseBuilder(
                ApplicationProvider.getApplicationContext(),
                VitesseDatabase::class.java
            )
            .allowMainThreadQueries()
            .build()
        Log.d("OM:ApplicantDaoTest", "Database created")
    }

    @Test
    fun applicantDao_InsertApplicant_ReturnsCorrectApplicant() = runTest {
        // When adding an applicant to the database:
        database.applicantDao().upsertApplicant(applicantList[0])
        Log.d("OM:ApplicantDaoTest", "Applicant inserted")
        database.applicantDao().getApplicantById(1).test{
            // When the applicant is retrieved:
            val retrievedApplicant = awaitItem()
            Log.d("OM:ApplicantDaoTest", "Applicant retrieved: $retrievedApplicant")
            // Then the retrieved applicant should be the same as the inserted applicant
            assertEquals(applicantList[0], retrievedApplicant)
            cancel()
        }
    }

    @Test
    fun applicantDao_DeleteApplicant_ReturnsNoApplicants() = runTest {
        // Given an applicant is added to the database:
        database.applicantDao().upsertApplicant(applicantList[0])
        // When the applicant is deleted:
        database.applicantDao().deleteApplicant(applicantList[0])
        // Then the database should return no applicants
        database.applicantDao().getApplicantById(1).test {
            // And the retrieved applicant should be null
            val retrievedApplicant = awaitItem()
            assertNull(retrievedApplicant)
            cancel()
        }
    }

    @Test
    fun applicantDao_GetAllApplicants_ReturnsAllApplicants() = runTest {
        // Given applicants are added to the database:
        applicantList.forEach { database.applicantDao().upsertApplicant(it) }
        // When all applicants are retrieved:
        database.applicantDao().getAllApplicants().test {
            // Then the retrieved list should contain all the applicants
            assertEquals(applicantList, awaitItem())
            cancel()
        }
    }

    @Test
    fun applicantDao_GetBookmarkedApplicants_ReturnsBookmarkedApplicants() = runTest {
        // Given applicants are added to the database:
        applicantList.forEach { database.applicantDao().upsertApplicant(it) }
        // When bookmarked applicants are retrieved:
        database.applicantDao().getBookmarkedApplicants().test {
            // Then the retrieved list should contain only the bookmarked applicants
            assertEquals(applicantList.filter { it.bookmarked }, awaitItem())
            cancel()
        }
    }

    @After
    // close the database instance after test
    fun closeDb(){
        database.close()
    }
}