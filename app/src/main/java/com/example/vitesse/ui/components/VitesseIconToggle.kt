package com.example.vitesse.ui.components

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.IconToggleButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.TooltipScope
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector

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