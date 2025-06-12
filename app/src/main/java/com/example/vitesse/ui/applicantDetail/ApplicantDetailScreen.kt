package com.example.vitesse.ui.applicantDetail

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.BorderStroke
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
import androidx.compose.material.icons.outlined.Call
import androidx.compose.material.icons.outlined.Delete
import androidx.compose.material.icons.outlined.Edit
import androidx.compose.material.icons.outlined.Email
import androidx.compose.material.icons.outlined.Star
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.vitesse.R
import com.example.vitesse.TextBodyLarge
import com.example.vitesse.TextBodyMedium
import com.example.vitesse.TextBodySmall
import com.example.vitesse.TextTitleMedium
import com.example.vitesse.VitesseIconButton
import com.example.vitesse.VitesseTopAppBar
import com.example.vitesse.data.model.Applicant
import com.example.vitesse.ui.AppViewModelProvider
import com.example.vitesse.ui.navigation.NavigationDestination

object ApplicantDetailDestination : NavigationDestination {
    override val route = "applicant_detail"
    override val titleRes = R.string.applicant_detail_screen
    const val ApplicantIdArg = "applicantId"
    val routeWithArgs = "$route/{$ApplicantIdArg}"
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun ApplicantDetailScreen (
    modifier: Modifier = Modifier,
    navigateBack: () -> Unit = {},
    navigateToEditApplicant: (Int) -> Unit = {},
    viewModel: ApplicantDetailViewModel = viewModel(factory = AppViewModelProvider.Factory)
){
    val applicant = viewModel.uiState.applicant

    Scaffold(
        modifier = modifier.fillMaxSize(),
        topBar = { VitesseTopAppBar(
//            title = with (applicant) { "$firstName $lastName" },
            title = applicant.run { "$firstName $lastName" },
            modifier = Modifier,
            navigateBack = navigateBack,
            actions = {
                VitesseIconButton(
                    icon = Icons.Outlined.Star,
                    onClick = { /*TODO*/ },
                    modifier = modifier
                )
                VitesseIconButton(
                    icon = Icons.Outlined.Edit,
                    onClick = { navigateToEditApplicant(1) },
                    modifier = modifier
                )
                VitesseIconButton(
                    icon = Icons.Outlined.Delete,
                    onClick = { /*TODO*/ },
                    modifier = modifier
                )
            }
        ) },
    ){ topAppBarPadding ->
        ApplicantDetailBody(
            modifier = modifier.padding(topAppBarPadding),
            applicant = applicant
        )
    }
}

@Composable
fun ApplicantDetailBody(
    modifier: Modifier = Modifier,
    applicant: Applicant
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
            contentDescription = null
        )
        Row(
            horizontalArrangement = Arrangement.Center
        ){
            ApplicantDetailContact(
                icon = Icons.Outlined.Call,
                text = stringResource(R.string.call),
                onClick = { /*TODO*/ }
            )
            ApplicantDetailContact(
                icon = ImageVector.vectorResource(R.drawable.chat_24px),
                text = stringResource(R.string.sms),
                onClick = { /*TODO*/ }
            )
            ApplicantDetailContact(
                icon = Icons.Outlined.Email,
                text = stringResource(R.string.email),
                onClick = { /*TODO*/ }
            )
        }
        ApplicantDetailCard(header = stringResource(R.string.about)){
            TextBodyLarge(text = applicant.birthDate.toString(),)
            TextBodyMedium(
                text = stringResource(R.string.birthday),
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

@Composable
fun ApplicantDetailContact(
    icon: ImageVector,
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
){
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier.padding(horizontal = 16.dp)
    ){
        VitesseIconButton(
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

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun ApplicantDetailScreenPreview(){
    ApplicantDetailScreen()
}