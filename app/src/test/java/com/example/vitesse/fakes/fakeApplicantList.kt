package com.example.vitesse.fakes

import com.example.vitesse.data.model.Applicant
import java.time.LocalDate

val fakeApplicantList = listOf(
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
        isFavorite = true,
        normalizedFirstName = "Alice",
        normalizedLastName = "Johnson"
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
        isFavorite = false,
        normalizedFirstName = "Bob",
        normalizedLastName = "Martinez"
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
        isFavorite = true,
        normalizedFirstName = "Claire",
        normalizedLastName = "Nguyen"
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
        isFavorite = false,
        normalizedFirstName = "David",
        normalizedLastName = "Smith"
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
        isFavorite = true,
        normalizedFirstName = "Emma",
        normalizedLastName = "Lopez",
    )
)