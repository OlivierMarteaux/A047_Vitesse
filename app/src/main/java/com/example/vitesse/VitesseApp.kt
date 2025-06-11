package com.example.vitesse

import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.vitesse.ui.applicantDetail.ApplicantDetailIconButton
import com.example.vitesse.ui.applicantDetail.IconResource
import com.example.vitesse.ui.navigation.VitesseNavHost

/**
 * Top level composable that represents screens for the application.
 */
@Composable
fun VitesseApp(
    navController: NavHostController = rememberNavController()
) {
    VitesseNavHost(navController = navController)
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun VitesseTopAppBar(
    modifier: Modifier = Modifier,
    title: String,
    actions: @Composable () -> Unit = {},
    navigateBack: () -> Unit
){
    TopAppBar (
        title = { Text(
            text = title,
            style = MaterialTheme.typography.titleLarge
        )
        },
        colors = TopAppBarColors(
            containerColor = MaterialTheme.colorScheme.surface,
            titleContentColor = MaterialTheme.colorScheme.onSurface,
            navigationIconContentColor = MaterialTheme.colorScheme.onSurfaceVariant,
            actionIconContentColor = MaterialTheme.colorScheme.onSurfaceVariant,
            scrolledContainerColor = Color.Transparent,
        ),
        modifier = modifier.windowInsetsPadding(WindowInsets(4,8,4,8)),
        navigationIcon = { ApplicantDetailIconButton(
            icon = IconResource.Vector(Icons.AutoMirrored.Filled.ArrowBack),
            onClick = navigateBack,
            modifier = modifier
        )
        },
        actions = { actions() }
    )
}