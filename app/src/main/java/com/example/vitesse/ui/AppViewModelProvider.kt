package com.example.vitesse.ui

import android.app.Application
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory
import androidx.lifecycle.viewmodel.CreationExtras
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.vitesse.VitesseApplication
import com.example.vitesse.ui.addApplicant.AddApplicantViewModel
import com.example.vitesse.ui.applicantDetail.ApplicantDetailViewModel
import com.example.vitesse.ui.editApplicant.EditApplicantViewModel
import com.example.vitesse.ui.home.HomeViewModel

/**
 * Provides Factory to create instance of ViewModel for the entire Vitesse app
 */
object AppViewModelProvider {
    val Factory = viewModelFactory {

        // Initializer for HomeViewModel
        initializer {
            HomeViewModel(vitesseApplication().container.applicantRepository)
        }

        // Initializer for AddViewModel
        initializer {
            AddApplicantViewModel(vitesseApplication().container.applicantRepository)
        }

        // Initializer for EditViewModel
        initializer {
            EditApplicantViewModel(vitesseApplication().container.applicantRepository)
        }

        // Initializer for DetailViewModel
        initializer {
            ApplicantDetailViewModel(
                vitesseApplication().container.applicantRepository
            )
        }
    }
}

/**
 * Extension function to queries for [Application] object and returns an instance of
 * [VitesseApplication].
 */
fun CreationExtras.vitesseApplication(): VitesseApplication =
    (this[AndroidViewModelFactory.APPLICATION_KEY] as VitesseApplication)
