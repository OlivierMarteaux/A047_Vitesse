package com.example.vitesse.ui

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory
import androidx.lifecycle.createSavedStateHandle
import androidx.lifecycle.viewmodel.CreationExtras
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.vitesse.VitesseApplication
import com.example.vitesse.ui.screens.addApplicant.AddApplicantViewModel
import com.example.vitesse.ui.screens.applicantDetail.ApplicantDetailViewModel
import com.example.vitesse.ui.screens.editApplicant.EditApplicantViewModel
import com.example.vitesse.ui.screens.home.HomeViewModel
import utils.AndroidLogger

/**
 * Provides a centralized [ViewModelProvider.Factory] for creating ViewModel instances used in the app.
 *
 * This factory supports creation of the following ViewModels:
 * - [HomeViewModel]: Requires [ApplicantRepository].
 * - [AddApplicantViewModel]: Requires [ApplicantRepository].
 * - [EditApplicantViewModel]: Requires [SavedStateHandle] and [ApplicantRepository].
 * - [ApplicantDetailViewModel]: Requires [SavedStateHandle], [ApplicantRepository], [CurrencyRepository], and a logger.
 *
 * All repositories and dependencies are retrieved from the singleton [VitesseApplication] container,
 * ensuring consistent and centralized access to app resources.
 *
 * Requirements:
 * - Requires API level 26 (Android O) or higher.
 */
object AppViewModelProvider {
    @RequiresApi(Build.VERSION_CODES.O)
    val Factory = viewModelFactory {

        // Initializer for HomeViewModel
        initializer {
            HomeViewModel(
                applicantRepository = vitesseApplication().container.localApplicantRepository
            )
        }

        // Initializer for AddViewModel
        initializer {
            AddApplicantViewModel(
                applicantRepository = vitesseApplication().container.localApplicantRepository
            )
        }

        // Initializer for EditViewModel
        initializer {
            EditApplicantViewModel(
                savedStateHandle = this.createSavedStateHandle(),
                applicantRepository = vitesseApplication().container.localApplicantRepository
            )
        }

        // Initializer for DetailViewModel
        initializer {
            ApplicantDetailViewModel(
                savedStateHandle = this.createSavedStateHandle(),
                applicantRepository = vitesseApplication().container.localApplicantRepository,
                currencyRepository = vitesseApplication().container.currencyRepository,
                logger = AndroidLogger
            )
        }
    }
}

/**
 * Extension function to retrieve the [VitesseApplication] instance from
 * [CreationExtras] inside a ViewModel factory context.
 *
 * This allows accessing application-scoped dependencies for ViewModel initialization.
 *
 * @receiver [CreationExtras] from the ViewModel creation context.
 * @return The singleton [VitesseApplication] instance.
 */
fun CreationExtras.vitesseApplication(): VitesseApplication =
    (this[AndroidViewModelFactory.APPLICATION_KEY] as VitesseApplication)
