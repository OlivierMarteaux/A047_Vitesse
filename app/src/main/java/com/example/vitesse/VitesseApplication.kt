package com.example.vitesse

import android.app.Application
import com.example.vitesse.data.AppContainer
import com.example.vitesse.data.AppDatabaseContainer
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob

class VitesseApplication : Application() {
    lateinit var container: AppContainer
    // A long-lived CoroutineScope tied to the application's lifecycle
    private val applicationScope = CoroutineScope(SupervisorJob())

    override fun onCreate() {
        super.onCreate()
        container = AppDatabaseContainer(this, applicationScope)
    }
}