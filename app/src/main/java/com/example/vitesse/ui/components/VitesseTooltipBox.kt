package com.example.vitesse.ui.components

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.TooltipBox
import androidx.compose.material3.TooltipDefaults
import androidx.compose.material3.TooltipScope
import androidx.compose.material3.TooltipState
import androidx.compose.runtime.Composable

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun VitesseTooltipBox(
    tooltip: @Composable TooltipScope.() -> Unit = {},
    content: @Composable () -> Unit
){
    TooltipBox(
        positionProvider = TooltipDefaults.rememberPlainTooltipPositionProvider(),
        state = TooltipState(),
        tooltip = tooltip,
        content = content
    )
}