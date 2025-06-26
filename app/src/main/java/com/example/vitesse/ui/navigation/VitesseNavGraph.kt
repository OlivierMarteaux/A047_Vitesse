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
 * Navigation host composable that manages the app's navigation graph.
 *
 * Defines the navigation routes and their corresponding composable screens, handling
 * navigation between Home, Applicant Detail, Edit Applicant, and Add Applicant screens.
 *
 * Each screen includes callbacks to navigate forward or backward through the app's flow,
 * with debug logging on navigation actions.
 *
 * @param navController The [NavHostController] responsible for navigation commands.
 * @param modifier Optional [Modifier] to apply to the [NavHost] container.
 *
 * Requirements:
 * - Requires API level 26 (Android O) or higher due to usage of [ApplicantDetailScreen] and related components.
 *
 * Navigation destinations:
 * - Home screen (start destination): allows navigation to applicant details or adding new applicants.
 * - Applicant Detail screen: shows details of an applicant, supports navigation back and to edit screen.
 * - Edit Applicant screen: allows editing an applicant and navigating back.
 * - Add Applicant screen: allows adding a new applicant and navigating back.
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
                navigateBack =
                    // fixed: replace popbackstack by popupto to avoid remanent ghost buttons from
                    //  previous screen
//                    {
//                        navController.popBackStack()
//                        debugLog("NavHost: DetailScreen: Navigating back")
//                    },
                    {
                        navController.navigate(HomeDestination.route) {
                            popUpTo(ApplicantDetailDestination.route) { inclusive = true }
                        }
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