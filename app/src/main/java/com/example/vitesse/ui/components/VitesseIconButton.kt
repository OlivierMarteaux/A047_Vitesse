package com.example.vitesse.ui.components

import androidx.compose.foundation.layout.size
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.IconButton
import androidx.compose.material3.TooltipScope
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.dimensionResource
import com.example.vitesse.R

/**
 * Icon button with tooltip support.
 *
 * @param icon The icon image vector to display inside the button.
 * @param onClick Callback invoked when the button is clicked.
 * @param modifier Optional [Modifier] for styling the button.
 * @param tooltip Composable content to display as tooltip on hover or long press.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun VitesseIconButton(
    icon: ImageVector,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    tooltip: @Composable TooltipScope.() -> Unit = {},
){
    VitesseTooltipBox(tooltip = tooltip) {
        IconButton(
            onClick = onClick,
            modifier = Modifier.size(dimensionResource(R.dimen.icon_size)),
        ) {
            VitesseIcon(icon = icon, modifier = modifier)
        }
    }
}