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
import utils.debugLog
import java.util.Locale

/**
 * Root composable of the Vitesse application that sets up core app behaviors and navigation.
 *
 * This composable initializes:
 * - Permission requests on the first app launch.
 * - Status bar color customization.
 * - Keyboard dismissal when tapping outside input fields.
 * - The main navigation host for navigating between screens.
 *
 * @param navController The [NavHostController] used to manage navigation between screens.
 *                      Defaults to a newly remembered controller.
 *
 * Requirements:
 * - Requires API level 26 (Android O) or higher.
 */
@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun VitesseApp(
    navController: NavHostController = rememberNavController()
) {
    debugLog("LocalLanguage = ${Locale.getDefault().language}")
    RequestPermissionsOnFirstLaunch()
    SetStatusBarColor(Color.White)
    DismissKeyboardOnTapOutside { VitesseNavHost(navController = navController) }
}

