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
 * Top level composable that represents screens for the application.
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

