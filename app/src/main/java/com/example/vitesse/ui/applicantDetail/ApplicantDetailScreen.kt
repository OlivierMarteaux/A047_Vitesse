package com.example.vitesse.ui.applicantDetail

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.outlined.Call
import androidx.compose.material.icons.outlined.Delete
import androidx.compose.material.icons.outlined.Edit
import androidx.compose.material.icons.outlined.Email
import androidx.compose.material.icons.outlined.Star
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.example.vitesse.R
import com.example.vitesse.ui.navigation.NavigationDestination
import com.google.accompanist.systemuicontroller.rememberSystemUiController

object ApplicantDetailDestination : NavigationDestination {
    override val route = "applicant_detail"
    override val titleRes = R.string.applicant_detail_screen
    const val ApplicantIdArg = "applicantId"
    val routeWithArgs = "$route/{$ApplicantIdArg}"
}

@Composable
fun ApplicantDetailScreen (
    modifier: Modifier = Modifier,
    //viewModel: ApplicantDetailViewModel = viewModel(factory = AppViewModelProvider.Factory)
){

    // Set status bar color to white and use dark icons
    val systemUiController = rememberSystemUiController()
    systemUiController.setStatusBarColor(
        color = Color.White,
        darkIcons = true
    )

    Scaffold(
        modifier = modifier.fillMaxSize(),
        topBar = { ApplicantDetailTopAppBar() },
    ){ topAppBarPadding ->
        ApplicantDetailBody(
            modifier = modifier.padding(topAppBarPadding)
        )
    }
}

@Composable
fun ApplicantDetailBody(
    modifier: Modifier = Modifier
){
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(horizontal = 20.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ){
        Image(
            painter = painterResource(R.drawable.martyna_siddeswara),
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .height(195.dp)
                .padding(14.dp),
            contentDescription = null)
        Row(
            horizontalArrangement = Arrangement.Center
        ){
            ApplicantDetailContact(
                icon = IconResource.Vector(Icons.Outlined.Call),
                text = stringResource(R.string.call),
                onClick = { /*TODO*/ }
            )
            ApplicantDetailContact(
                icon = IconResource.Paint(painterResource(R.drawable.chat_24px)),
                text = stringResource(R.string.sms),
                onClick = { /*TODO*/ }
            )
            ApplicantDetailContact(
                icon = IconResource.Vector(Icons.Outlined.Email),
                text = stringResource(R.string.email),
                onClick = { /*TODO*/ }
            )
        }
        ApplicantDetailCard(header = stringResource(R.string.about)){
            TextBodyLarge(text = "text_exemple",)
            TextBodyMedium(
                text = "text_exemple",
                modifier = Modifier.padding(bottom = 11.dp)
            )
        }
        ApplicantDetailCard(header = stringResource(R.string.salary_expectations)){
            TextBodyLarge(
                text = "text_exemple",
                modifier = Modifier.padding(bottom = 32.dp)
            )
            TextBodyMedium(text = "text_exemple")
        }
        ApplicantDetailCard(header = stringResource(R.string.notes)){
            TextBodyMedium(text = "text_exemple")
        }
    }
}

@Composable
fun ApplicantDetailCard(
    modifier: Modifier = Modifier,
    header: String,
    content: @Composable () -> Unit
){
    Card(
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface,
            contentColor = MaterialTheme.colorScheme.onSurface
            ),
        border = BorderStroke(width = 1.dp, color = MaterialTheme.colorScheme.outlineVariant),
        shape = MaterialTheme.shapes.medium,
        modifier = modifier
            .fillMaxWidth()
            .padding(top = 14.dp)
    ){
        Column(
            modifier = Modifier.padding(top = 24.dp, start = 16.dp, end = 16.dp, bottom = 16.dp)
        ){
            TextTitleMedium(
                text = header,
                modifier = Modifier.padding(bottom = 40.dp)
            )
            content()
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ApplicantDetailTopAppBar(
    modifier: Modifier = Modifier
){
    TopAppBar (
        title = { Text(
            text = "Alice JOHNSON",
            style = MaterialTheme.typography.titleLarge
        )},
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
            onClick = { /*TODO*/ },
            modifier = modifier
        )},
        actions = {
            ApplicantDetailIconButton(
                icon = IconResource.Vector(Icons.Outlined.Star),
                onClick = { /*TODO*/ },
                modifier = modifier
            )
            ApplicantDetailIconButton(
                icon = IconResource.Vector(Icons.Outlined.Edit),
                onClick = { /*TODO*/ },
                modifier = modifier
            )
            ApplicantDetailIconButton(
                icon = IconResource.Vector(Icons.Outlined.Delete),
                onClick = { /*TODO*/ },
                modifier = modifier
            )
        }
    )
}

@Composable
fun ApplicantDetailContact(
    icon: IconResource,
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
){
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier.padding(horizontal = 16.dp)
    ){
        ApplicantDetailIconButton(
            icon = icon,
            onClick = onClick,
            modifier = modifier
                .padding(4.dp)
                .border(
                    width = Dp.Hairline,
                    color = MaterialTheme.colorScheme.primary,
                    shape = CircleShape
                )
                .padding(8.dp)
        )
        TextBodySmall(text = text,)
    }
}

@Composable
fun ApplicantDetailIconButton(
    icon: IconResource,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
){
    IconButton(
        onClick = onClick,
        modifier = Modifier.size(dimensionResource(R.dimen.icon_size)),
    ){when (icon) {
        is IconResource.Vector -> Icon(
            imageVector = icon.imageVector,
            contentDescription = null,
            modifier = modifier

        )
        is IconResource.Paint -> Icon(
            painter = icon.painter,
            contentDescription = null,
            modifier = modifier
        )
    }}
}

sealed class IconResource {
    data class Vector(val imageVector: ImageVector) : IconResource()
    data class Paint(val painter: Painter) : IconResource()
}

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
    modifier: Modifier = Modifier
){
    Text(
        text = text,
        style = MaterialTheme.typography.bodySmall,
        color = Color.Black,
        modifier = modifier
    )
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun ApplicantDetailScreenPreview(){
    ApplicantDetailScreen()
}