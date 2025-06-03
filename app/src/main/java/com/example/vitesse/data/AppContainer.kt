package com.example.vitesse.data

import android.content.Context
import com.example.vitesse.VitesseApplication
import com.example.vitesse.data.repository.ApplicantRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob

interface AppContainer {
    val applicantRepository: ApplicantRepository
}

class AppDatabaseContainer(
    private val context: Context,
    private val applicationScope: CoroutineScope
) : AppContainer {

    override val applicantRepository: ApplicantRepository by lazy {
        ApplicantRepository(VitesseDatabase.getInstance(context, applicationScope).applicantDao())
    }
}