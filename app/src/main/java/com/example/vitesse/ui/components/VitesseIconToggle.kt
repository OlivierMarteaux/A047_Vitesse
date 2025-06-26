package com.example.vitesse.ui.components

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.IconToggleButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.TooltipScope
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector

/**
 * Toggleable icon button with checked and unchecked states and tooltip.
 *
 * @param iconChecked The icon to display when the toggle is checked.
 * @param iconUnchecked The icon to display when the toggle is unchecked.
 * @param modifier Optional [Modifier] for styling the toggle button.
 * @param checked Current checked state.
 * @param onCheckedChange Lambda invoked when the toggle state changes.
 * @param tooltip Composable content to display as tooltip on hover or long press.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun VitesseIconToggle(
    iconChecked: ImageVector,
    iconUnchecked: ImageVector,
    modifier : Modifier = Modifier,
    checked: Boolean,
    onCheckedChange: (Boolean) -> Unit,
    tooltip: @Composable TooltipScope.() -> Unit = {}
) {
    VitesseTooltipBox(tooltip = tooltip) {
        IconToggleButton(
            checked = checked,
            onCheckedChange = onCheckedChange,
            modifier = modifier,
            colors = IconButtonDefaults.iconToggleButtonColors(
                checkedContentColor = MaterialTheme.colorScheme.onPrimary
            )
        ) {
            if (checked) VitesseIcon(iconChecked)
            else VitesseIcon(iconUnchecked)
        }
    }
}