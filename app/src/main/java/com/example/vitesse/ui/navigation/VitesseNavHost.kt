package com.example.vitesse.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.vitesse.ui.addApplicant.AddApplicantDestination
import com.example.vitesse.ui.addApplicant.AddApplicantScreen
import com.example.vitesse.ui.applicantDetail.ApplicantDetailDestination
import com.example.vitesse.ui.applicantDetail.ApplicantDetailScreen
import com.example.vitesse.ui.home.HomeDestination
import com.example.vitesse.ui.home.HomeScreen

/**
 * Provides Navigation graph for the application.
 */
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
        composable(route = HomeDestination.route) {
            HomeScreen(
                navigateToApplicantDetail = { navController.navigate("${ApplicantDetailDestination.route}/${it}") },
                navigateToAddApplicant = { navController.navigate(AddApplicantDestination.route) }
            )
        }
        composable(
            route = ApplicantDetailDestination.routeWithArgs,
            arguments = listOf(navArgument(ApplicantDetailDestination.ApplicantIdArg){
                type = NavType.IntType})
        ){
            ApplicantDetailScreen(
//                navigateBack = { navController.navigateUp() },
                navigateBack = { navController.popBackStack() }
            )
        }
        composable(
            route = AddApplicantDestination.route){
            AddApplicantScreen(
//                navigateBack = { navController.navigateUp() },
//                onNavigateUp = { navController.navigateUp() }
            )
        }
    }
}