package com.example.vitesse.ui.applicantDetail

import android.content.Context
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Call
import androidx.compose.material.icons.outlined.Delete
import androidx.compose.material.icons.outlined.Edit
import androidx.compose.material.icons.outlined.Email
import androidx.compose.material.icons.outlined.Star
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil3.ImageLoader
import coil3.compose.AsyncImage
import coil3.request.ImageRequest
import coil3.request.crossfade
import coil3.size.Scale
import coil3.size.Size
import coil3.toUri
import com.example.vitesse.R
import com.example.vitesse.TextBodyLarge
import com.example.vitesse.TextBodyMedium
import com.example.vitesse.TextBodySmall
import com.example.vitesse.TextTitleMedium
import com.example.vitesse.VitesseApplication
import com.example.vitesse.VitesseIconButton
import com.example.vitesse.VitesseIconToggle
import com.example.vitesse.VitesseTopAppBar
import com.example.vitesse.data.model.Applicant
import com.example.vitesse.data.model.Currency
import com.example.vitesse.ui.AppViewModelProvider
import com.example.vitesse.ui.navigation.NavigationDestination
import extensions.callPhoneNumber
import extensions.sendEmail
import extensions.sendSms
import extensions.toBritishPoundString
import extensions.toLocalCurrencyString
import extensions.toLocalDateString

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
    val context = LocalContext.current
    val loader: ImageLoader = VitesseApplication().newImageLoader(context)
    val applicant = viewModel.uiState.applicant
    Log.d("OM_TAG", "applicant: $applicant")
    val currency = viewModel.uiState.currency
    var showConfirmationDialog by remember { mutableStateOf(false) }

    if (showConfirmationDialog) {
        DeleteConfirmationDialog(
            onConfirm = {
                viewModel.delete(applicant)
                navigateBack()
            },
            onDismiss = { showConfirmationDialog = false }
        )
    }

    Scaffold(
        modifier = modifier.fillMaxSize(),
        topBar = { VitesseTopAppBar(
            title = applicant.run { "$firstName $lastName" },
            modifier = Modifier,
            navigateBack = navigateBack,
            actions = {
                VitesseIconToggle(
                    iconChecked = Icons.Outlined.Star,
                    iconUnchecked = ImageVector.vectorResource(R.drawable.star_24dp),
                    checked = applicant.isFavorite,
                    onCheckedChange = { viewModel.updateApplicant(applicant.copy(isFavorite = !applicant.isFavorite)) },
                    modifier = modifier,
                )
                VitesseIconButton(
                    icon = Icons.Outlined.Edit,
                    onClick = { navigateToEditApplicant(applicant.id) },
                    modifier = modifier
                )
                VitesseIconButton(
                    icon = Icons.Outlined.Delete,
                    onClick = { showConfirmationDialog = true },
                    modifier = modifier
                )
            }
        ) },
    ){ topAppBarPadding ->
        ApplicantDetailBody(
            modifier = modifier.padding(topAppBarPadding),
            applicant = applicant,
            currency = currency
        )
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun ApplicantDetailBody(
    modifier: Modifier = Modifier,
    applicant: Applicant,
    currency: Currency
){
    val context = LocalContext.current
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(horizontal = 20.dp)
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally
    ){
//        Image(
//            painter = rememberAsyncImagePainter(applicant.photoUri),
//            contentScale = ContentScale.Crop,
//            modifier = Modifier
//                .height(195.dp)
//                .padding(14.dp),
//            contentDescription = null
//        )
        VitesseAsyncImage(
            applicant = applicant,
            loader = VitesseApplication().newImageLoader(context),
            context = context,
            modifier = Modifier
                .height(195.dp)
        )
        Row(
            horizontalArrangement = Arrangement.Center
        ){
            ApplicantDetailContact(
                icon = Icons.Outlined.Call,
                text = stringResource(R.string.call),
                onClick = { context.callPhoneNumber(applicant.phone) }
            )
            ApplicantDetailContact(
                icon = ImageVector.vectorResource(R.drawable.chat_24px),
                text = stringResource(R.string.sms),
                onClick = { context.sendSms(applicant.phone) }
            )
            ApplicantDetailContact(
                icon = Icons.Outlined.Email,
                text = stringResource(R.string.email),
                onClick = { context.sendEmail(applicant.email) }
            )
        }
        ApplicantDetailCard(header = stringResource(R.string.about)){
            TextBodyLarge(text = applicant.birthDate.toLocalDateString())
            TextBodyMedium(
                text = stringResource(R.string.birthday),
                modifier = Modifier.padding(bottom = 11.dp)
            )
        }
        ApplicantDetailCard(header = stringResource(R.string.expected_salary)){
            TextBodyLarge(
                text = applicant.salary.toLocalCurrencyString(),
                modifier = Modifier.padding(bottom = 32.dp)
            )
            TextBodyMedium(text = (stringResource(
                R.string.or,
                (applicant.salary * currency.eur.gbp).toBritishPoundString())
                    ))
        }
        ApplicantDetailCard(
            header = stringResource(R.string.notes),
            modifier = Modifier.padding(bottom = 30.dp)
        ){
            TextBodyMedium(text = applicant.note)
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
        TextBodySmall(text = text)
    }
}

@Composable
fun DeleteConfirmationDialog(
    onConfirm: () -> Unit,
    onDismiss: () -> Unit
) {
    AlertDialog(
        onDismissRequest = onDismiss,
        title = {
            Text(text = stringResource(R.string.deletion))
        },
        text = {
            Text(stringResource(R.string.are_you_sure_you_want_to_delete_this_candidate_this_action_cannot_be_undone))
        },
        confirmButton = {
            TextButton(onClick = onConfirm) {
                Text(stringResource(R.string.confirm) /*color = Color.Red*/)
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text(stringResource(R.string.cancel))
            }
        }
    )
}

@Composable
fun VitesseAsyncImage(
    modifier: Modifier = Modifier,
    applicant: Applicant,
    loader: ImageLoader,
    context: Context,
    contentScale: ContentScale = ContentScale.Fit,
){
    val uri = applicant.photoUri.toUri()
//    val uri = applicant.photoUri.par
    val request = ImageRequest.Builder(context = context)
        .data(uri)
        .size(Size.ORIGINAL)
        .scale(Scale.FILL)
        .crossfade(true)
        .build()
    AsyncImage(
        model = request,
        imageLoader = loader,
        contentDescription = null,
        contentScale = contentScale,
        modifier = modifier
    )
}


@RequiresApi(Build.VERSION_CODES.O)
@Preview(showBackground = true, showSystemUi = true)
@Composable
fun ApplicantDetailScreenPreview(){
    ApplicantDetailScreen()
}