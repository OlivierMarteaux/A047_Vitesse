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
import com.example.vitesse.ui.addApplicant.AddApplicantDestination
import com.example.vitesse.ui.addApplicant.AddApplicantScreen
import com.example.vitesse.ui.applicantDetail.ApplicantDetailDestination
import com.example.vitesse.ui.applicantDetail.ApplicantDetailScreen
import com.example.vitesse.ui.editApplicant.EditApplicantDestination
import com.example.vitesse.ui.editApplicant.EditApplicantScreen
import com.example.vitesse.ui.home.HomeDestination
import com.example.vitesse.ui.home.HomeScreen

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
                navigateBack = { navController.popBackStack() },
                navigateToEditApplicant = { navController.navigate("${EditApplicantDestination.route}/$it") }
            )
        }
        composable(
            route = EditApplicantDestination.routeWithArgs,
            arguments = listOf(navArgument(EditApplicantDestination.ApplicantIdArg){
                type = NavType.IntType})
        ){
            EditApplicantScreen(
                navigateBack = { navController.popBackStack() },
//                onNavigateUp = { navController.navigateUp() }
            )
        }
        composable(
            route = AddApplicantDestination.route){
            AddApplicantScreen(
                navigateBack = { navController.popBackStack() }
//                onNavigateUp = { navController.navigateUp() }
            )
        }
    }
}