package com.example.vitesse.ui.components

import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

/**
 * Top app bar with a title, back navigation icon, and optional action buttons.
 *
 * @param modifier Optional [Modifier] to style the top app bar.
 * @param title The title text displayed in the app bar.
 * @param actions Composable row scope for placing action icons/buttons on the right.
 * @param navigateBack Callback invoked when the navigation (back) icon is clicked.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun VitesseTopAppBar(
    modifier: Modifier = Modifier,
    title: String,
    actions: @Composable RowScope.() -> Unit = {},
    navigateBack: () -> Unit
){
    TopAppBar (
        title = { Text(
            text = title,
            style = MaterialTheme.typography.titleLarge
        ) },
        modifier = modifier.windowInsetsPadding(WindowInsets(4,8,4,8)),
        navigationIcon = { VitesseIconButton(
            icon = Icons.AutoMirrored.Filled.ArrowBack,
            onClick = navigateBack,
            modifier = modifier
        )
        },
        actions = actions
    )
}