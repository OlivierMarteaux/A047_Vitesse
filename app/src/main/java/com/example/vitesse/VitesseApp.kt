package com.example.vitesse

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.vitesse.ui.navigation.VitesseNavHost

/**
 * Top level composable that represents screens for the application.
 */
@Composable
fun VitesseApp(
    navController: NavHostController = rememberNavController()
) {
    VitesseNavHost(navController = navController)
}