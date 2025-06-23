package com.example.vitesse

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.vitesse.ui.components.startup.DismissKeyboardOnTapOutside
import com.example.vitesse.ui.components.startup.RequestPermissionsOnFirstLaunch
import com.example.vitesse.ui.components.startup.SetStatusBarColor
import com.example.vitesse.ui.navigation.VitesseNavHost
import extensions.stripAccents
import utils.debugLog

/**
 * Top level composable that represents screens for the application.
 */
@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun VitesseApp(
    navController: NavHostController = rememberNavController()
) {
    RequestPermissionsOnFirstLaunch()
    SetStatusBarColor(Color.White)
    val input = "Élise Moreau"
    val result = input.stripAccents()
    debugLog("result: $result")
    val input2 = "Chloé Renard"
    val result2 = input2.stripAccents()
    debugLog("result2: $result2")

    DismissKeyboardOnTapOutside { VitesseNavHost(navController = navController) }
}

