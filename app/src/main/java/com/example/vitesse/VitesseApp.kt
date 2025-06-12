package com.example.vitesse

import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.dimensionResource
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.vitesse.ui.navigation.VitesseNavHost
import com.google.accompanist.systemuicontroller.rememberSystemUiController

/**
 * Top level composable that represents screens for the application.
 */
@Composable
fun VitesseApp(
    navController: NavHostController = rememberNavController()
) {
    // Set status bar color to white and use dark icons
    val systemUiController = rememberSystemUiController()
    systemUiController.setStatusBarColor(
        color = Color.White,
        darkIcons = true
    )

    VitesseNavHost(navController = navController)
}

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
        navigationIcon = { VitesseIconButton(
            icon = Icons.AutoMirrored.Filled.ArrowBack,
            onClick = navigateBack,
            modifier = modifier
        )
        },
        actions = actions
    )
}

@Composable
fun VitesseIconButton(
    icon: ImageVector,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
){
    IconButton(
        onClick = onClick,
        modifier = Modifier.size(dimensionResource(R.dimen.icon_size)),
    ){
        VitesseIcon(icon = icon, modifier = modifier)
    }
}

@Composable
fun VitesseIcon(
    icon: ImageVector,
    modifier: Modifier = Modifier
) {
    Icon(
        imageVector = icon,
        contentDescription = null,
        modifier = modifier
    )
}

//@Composable
//fun VitesseIcon(
//    icon: IconResource,
//    modifier: Modifier = Modifier
//) {
//    when (icon) {
//        is IconResource.Vector -> Icon(
//            imageVector = icon.imageVector,
//            contentDescription = null,
//            modifier = modifier
//        )
//
//        is IconResource.Paint -> Icon(
//            painter = icon.painter,
//            contentDescription = null,
//            modifier = modifier
//        )
//    }
//}

//sealed class IconResource {
//    data class Vector(val imageVector: ImageVector) : IconResource()
//    data class Paint(val painter: Painter) : IconResource()
//}

@Composable
fun TextTitleMedium (
    text:String,
    modifier: Modifier = Modifier
){
    Text(
        text = text,
        style = MaterialTheme.typography.titleMedium,
        color = MaterialTheme.colorScheme.onSurface,
        modifier = modifier
    )
}

@Composable
fun TextBodyLarge (
    text:String,
    modifier: Modifier = Modifier
){
    Text(
        text = text,
        style = MaterialTheme.typography.bodyLarge,
        color = MaterialTheme.colorScheme.onSurface,
        modifier = modifier
    )
}

@Composable
fun TextBodyMedium (
    text:String,
    modifier: Modifier = Modifier
){
    Text(
        text = text,
        style = MaterialTheme.typography.bodyMedium,
        color = MaterialTheme.colorScheme.onSurfaceVariant,
        modifier = modifier
    )
}

@Composable
fun TextBodySmall (
    text:String,
    modifier: Modifier = Modifier,
    color: Color = Color.Black
){
    Text(
        text = text,
        style = MaterialTheme.typography.bodySmall,
        color = color,
        modifier = modifier
    )
}

@Composable
fun TextLabelLarge(
    text:String,
    modifier: Modifier = Modifier
){
    Text(
        text = text,
        style = MaterialTheme.typography.labelLarge,
        color = MaterialTheme.colorScheme.onSurfaceVariant,
        modifier = modifier
    )
}

@Composable
fun TextHeadLineLarge(
    text:String,
    modifier: Modifier = Modifier
){
    Text(
        text = text,
        style = MaterialTheme.typography.headlineLarge,
        color = MaterialTheme.colorScheme.onSurface,
        modifier = modifier
    )
}