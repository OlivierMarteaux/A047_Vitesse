package com.example.vitesse.ui.components.startup

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import com.google.accompanist.systemuicontroller.rememberSystemUiController

/**
 * Sets the status bar color for the current screen.
 *
 * This composable uses [rememberSystemUiController] from Accompanist to update
 * the system status bar color dynamically.
 *
 * @param color The [Color] to set as the status bar background.
 *
 * The status bar icons and text are set to use a dark theme (suitable for light backgrounds).
 *
 * Usage:
 * ```
 * SetStatusBarColor(Color.White)
 * ```
 */
@Composable
fun SetStatusBarColor(color: Color) {
    val systemUiController = rememberSystemUiController()
    systemUiController.setStatusBarColor(
        color = color,
        darkIcons = true
    )
}