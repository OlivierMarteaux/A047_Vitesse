package com.example.vitesse.ui.applicantDetail

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.vitesse.R
import com.example.vitesse.ui.AppViewModelProvider
import com.example.vitesse.ui.navigation.NavigationDestination

object ApplicantDetailDestination : NavigationDestination {
    override val route = "applicant_detail"
    override val titleRes = R.string.applicant_detail_screen
    const val ApplicantIdArg = "applicantId"
    val routeWithArgs = "$route/{$ApplicantIdArg}"
}

@Composable
fun ApplicantDetailScreen (
    modifier: Modifier = Modifier,
    viewModel: ApplicantDetailViewModel = viewModel(factory = AppViewModelProvider.Factory)
){
    Scaffold(
        modifier = modifier.fillMaxSize(),
        topBar = { ApplicantDetailTopAppBar() },
    ){ topAppBarPadding ->
        Column(
            modifier = modifier
                .fillMaxSize()
                .padding(topAppBarPadding),
            horizontalAlignment = Alignment.CenterHorizontally
        ){
            Image(
                painter = painterResource(R.drawable.martyna_siddeswara),
                contentScale = ContentScale.Crop,
                modifier = modifier.height(195.dp),
                contentDescription = null)
            Row(
                horizontalArrangement = Arrangement.Center
            ){
                ApplicantContactIcon(
                    icon = IconResource.Vector(Icons.Filled.Star),
                    text = stringResource(R.string.call),
                    onClick = { /*TODO*/ }
                )
                ApplicantContactIcon(
                    icon = IconResource.Paint(painterResource(R.drawable.chat_24px)),
                    text = stringResource(R.string.sms),
                    onClick = { /*TODO*/ }
                )
                ApplicantContactIcon(
                    icon = IconResource.Vector(Icons.Filled.Email),
                    text = stringResource(R.string.email),
                    onClick = { /*TODO*/ }
                )
            }
            Card(
                modifier = modifier
                    .padding(12.dp)
                    .fillMaxWidth()
            ){
                Column(){
                    Text(text = "text_exemple")
                    Text(text = "text_exemple")
                    Text(text = "text_exemple")
                }
            }
            Card(){
                Column(){
                    Text(text = "text_exemple")
                    Text(text = "text_exemple")
                    Text(text = "text_exemple")
                }
            }
            Card(){
                Column(){
                    Text(text = "text_exemple")
                    Text(text = "text_exemple")
                }
            }
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
            style = MaterialTheme.typography.titleMedium
        )},
        modifier = modifier.padding(
            top = dimensionResource(R.dimen.padding_medium),
            bottom = dimensionResource(R.dimen.padding_medium),
            start = dimensionResource(R.dimen.padding_small),
            end = dimensionResource(R.dimen.padding_small)),
        navigationIcon = { TopAppBarIconButton(
            icon = Icons.AutoMirrored.Filled.ArrowBack,
            onClick = { /*TODO*/ },
            modifier = modifier
        )},
        actions = {
            TopAppBarIconButton(
                icon = Icons.Filled.Star,
                onClick = { /*TODO*/ },
                modifier = modifier
            )
            TopAppBarIconButton(
                icon = Icons.Filled.Edit,
                onClick = { /*TODO*/ },
                modifier = modifier
            )
            TopAppBarIconButton(
                icon = Icons.Filled.Delete,
                onClick = { /*TODO*/ },
                modifier = modifier
            )
        }
    )
}

@Composable
fun ApplicantContactIcon(
    icon: IconResource,
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
){
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier.padding(12.dp)
    ){
        IconButton(
            onClick = onClick,
            modifier = modifier,
        ){when (icon) {
            is IconResource.Vector -> Icon(
                imageVector = icon.imageVector,
                contentDescription = null,
                modifier = Modifier.border(
                    width = Dp.Hairline,
                    color = MaterialTheme.colorScheme.primary,
                    shape = CircleShape
                ).padding(8.dp)
            )
            is IconResource.Paint -> Icon(
                painter = icon.painter,
                contentDescription = null,
                modifier = Modifier.border(
                    width = Dp.Hairline,
                    color = MaterialTheme.colorScheme.primary,
                    shape = CircleShape
                ).padding(8.dp)
            )
        }}
        Text(
            text = text,
            style = MaterialTheme.typography.bodySmall
        )
    }
}

@Composable
fun TopAppBarIconButton(
    icon: ImageVector,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
){
    IconButton(
        onClick = onClick,
        modifier = modifier,
    ){Icon(
        imageVector = icon,
        contentDescription = null,
        modifier = modifier.padding(dimensionResource(R.dimen.padding_medium))
    )}
}

sealed class IconResource {
    data class Vector(val imageVector: ImageVector) : IconResource()
    data class Paint(val painter: Painter) : IconResource()
}