package com.example.vitesse.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import com.example.vitesse.ui.home.HomeDestination
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
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

            )
        }
    }
}