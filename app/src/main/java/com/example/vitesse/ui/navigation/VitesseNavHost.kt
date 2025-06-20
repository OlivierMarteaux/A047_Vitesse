package com.example.vitesse.ui.navigation

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.vitesse.ui.screens.addApplicant.AddApplicantDestination
import com.example.vitesse.ui.screens.addApplicant.AddApplicantScreen
import com.example.vitesse.ui.screens.applicantDetail.ApplicantDetailDestination
import com.example.vitesse.ui.screens.applicantDetail.ApplicantDetailScreen
import com.example.vitesse.ui.screens.editApplicant.EditApplicantDestination
import com.example.vitesse.ui.screens.editApplicant.EditApplicantScreen
import com.example.vitesse.ui.screens.home.HomeDestination
import com.example.vitesse.ui.screens.home.HomeScreen
import utils.debugLog

/**
 * Provides Navigation graph for the application.
 */
@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun VitesseNavHost(
    navController: NavHostController,
    modifier: Modifier = Modifier,
) {
    NavHost(
        navController = navController,
        startDestination = HomeDestination.route,
        modifier = modifier
    ){

        // Home Screen
        composable(route = HomeDestination.route) {
            HomeScreen(
                navigateToApplicantDetail = {
                    navController.navigate("${ApplicantDetailDestination.route}/${it}")
                    debugLog("NavHost: HomeScreen: Navigating to ${ApplicantDetailDestination.route}/$it")
                                            },
                navigateToAddApplicant = {
                    navController.navigate(AddApplicantDestination.route)
                    debugLog("NavHost: HomeScreen: Navigating to ${AddApplicantDestination.route}")
                }
            )
        }

        // Applicant Detail Screen
        composable(
            route = ApplicantDetailDestination.routeWithArgs,
            arguments = listOf(navArgument(ApplicantDetailDestination.ApplicantIdArg){
                type = NavType.IntType})
        ){
            ApplicantDetailScreen(
                navigateBack = {
                    navController.popBackStack()
                    debugLog("NavHost: DetailScreen: Navigating back")
                               },
                navigateToEditApplicant = {
                    navController.navigate("${EditApplicantDestination.route}/$it")
                    debugLog("NavHost: DetailScreen: Navigating to ${EditApplicantDestination.route}/$it")
                }
            )
        }

        // Edit Applicant Screen
        composable(
            route = EditApplicantDestination.routeWithArgs,
            arguments = listOf(navArgument(EditApplicantDestination.ApplicantIdArg){
                type = NavType.IntType})
        ){
            EditApplicantScreen(
                navigateBack = {
                    navController.popBackStack()
                    debugLog("NavHost: EditScreen: Navigating back")
                               },
            )
        }

        // Add Applicant Screen
        composable(
            route = AddApplicantDestination.route){
            AddApplicantScreen(
                navigateBack = {
                    navController.popBackStack()
                    debugLog("NavHost: AddScreen: Navigating back")
                }
            )
        }
    }
}