package com.example.vitesse

import android.app.Application
import com.example.vitesse.data.AppContainer
import com.example.vitesse.data.AppDatabaseContainer
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob

/**
 * Custom [Application] class for the Vitesse app.
 *
 * Initializes the application-wide dependencies and resources, including the
 * [AppContainer] which holds repository instances.
 *
 * This class maintains a long-lived [CoroutineScope] tied to the application's lifecycle,
 * allowing for background tasks that live as long as the app does.
 *
 * Properties:
 * - [container]: Provides access to app-wide dependencies such as repositories.
 * - [applicationScope]: Coroutine scope with a [SupervisorJob] to handle concurrent coroutines safely.
 *
 * Initialization:
 * - On app creation, the [container] is initialized with an instance of [AppDatabaseContainer],
 *   passing the application context and [applicationScope].
 */
class VitesseApplication : Application() {
    lateinit var container: AppContainer
    // A long-lived CoroutineScope tied to the application's lifecycle
    private val applicationScope = CoroutineScope(SupervisorJob())

    override fun onCreate() {
        super.onCreate()
        container = AppDatabaseContainer(this, applicationScope)
    }
}