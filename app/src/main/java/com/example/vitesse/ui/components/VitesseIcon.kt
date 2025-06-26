package com.example.vitesse.ui.components

import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector

/**
 * Displays an icon using the given [ImageVector].
 *
 * @param icon The [ImageVector] graphic to display.
 * @param modifier Optional [Modifier] for styling the icon.
 */
@Composable
fun VitesseIcon(
    icon: ImageVector,
    modifier: Modifier = Modifier
) {
    Icon(
        imageVector = icon,
        contentDescription = null,
        modifier = modifier,
    )
}