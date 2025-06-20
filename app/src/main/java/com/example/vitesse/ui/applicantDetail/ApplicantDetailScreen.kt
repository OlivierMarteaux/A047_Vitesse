package com.example.vitesse.ui.applicantDetail

import android.content.Context
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
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
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
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
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil3.compose.rememberAsyncImagePainter
import com.example.vitesse.R
import com.example.vitesse.TextBodyLarge
import com.example.vitesse.TextBodyMedium
import com.example.vitesse.TextBodySmall
import com.example.vitesse.TextTitleMedium
import com.example.vitesse.VitesseIconButton
import com.example.vitesse.VitesseIconToggle
import com.example.vitesse.VitesseTopAppBar
import com.example.vitesse.data.model.Applicant
import com.example.vitesse.data.model.ExchangeRate
import com.example.vitesse.ui.AppViewModelProvider
import com.example.vitesse.ui.navigation.NavigationDestination
import extensions.callPhoneNumber
import extensions.getAge
import extensions.sendEmail
import extensions.sendSms
import extensions.toEurString
import extensions.toGbpString
import extensions.toLocalCurrencyString
import extensions.toLocalDateString
import utils.debugLog
import utils.openAppSettings
import java.util.Locale

object ApplicantDetailDestination : NavigationDestination {
    override val route = "applicant_detail"
    override val titleRes = R.string.applicant_detail_screen
    const val ApplicantIdArg = "applicantId"
    val routeWithArgs = "$route/{$ApplicantIdArg}"
}

@OptIn(ExperimentalMaterial3Api::class)
@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun ApplicantDetailScreen (
    modifier: Modifier = Modifier,
    navigateBack: () -> Unit = {},
    navigateToEditApplicant: (Int) -> Unit = {},
    viewModel: ApplicantDetailViewModel = viewModel(factory = AppViewModelProvider.Factory)
){
    val context = LocalContext.current
    val applicant = viewModel.uiState.applicant
    val exchangeRate = viewModel.uiState.exchangeRate
    var showDeleteConfirmationDialog by remember { mutableStateOf(false)}
    val showCallAlertDialog = viewModel.callAlertDialog

// FIXED: this function re-insert the just-deleted applicant when navigate back to home after deletion.
//  -> shall not be used
//    // Save when screen is disposed
//    DisposableEffect(Unit) {
//        onDispose {
//            viewModel.updateApplicantFavoriteState()
//        }
//    }

    if (showDeleteConfirmationDialog) {
        VitesseAlertDialog(
            onConfirm = {
                viewModel.deleteApplicant(applicant)
                navigateBack()
            },
            onDismiss = { showDeleteConfirmationDialog = false },
            modifier = modifier,
            title = stringResource(R.string.deletion),
            text = stringResource(R.string.are_you_sure_you_want_to_delete_this_candidate_this_action_cannot_be_undone),
            dismissText = stringResource(R.string.cancel),
            confirmText = stringResource(R.string.confirm)
        )
    }

    if (showCallAlertDialog) {
        VitesseAlertDialog(
            onConfirm = { viewModel.showCallAlertDialog(false); openAppSettings(context) },
            onDismiss = { viewModel.showCallAlertDialog(false) },
            modifier = modifier,
            title = stringResource(R.string.call_autorization_needed),
            text = stringResource(R.string.call_permission_needed_text),
            dismissText = stringResource(R.string.cancel),
            confirmText = stringResource(R.string.ok)
        )
    }

    Scaffold(
        modifier = modifier.fillMaxSize(),
        topBar = { VitesseTopAppBar(
            title = applicant.run { "$firstName ${lastName.uppercase()}" },
            modifier = Modifier,
            navigateBack = { viewModel.updateApplicantFavoriteState(); navigateBack() },
            actions = {
                VitesseIconToggle(
                    iconChecked = Icons.Outlined.Star,
                    iconUnchecked = ImageVector.vectorResource(R.drawable.star_24dp),
                    checked = viewModel.isFavorite,
                    onCheckedChange = { viewModel.toggleFavorite() },
                    modifier = modifier,
                    tooltip = { Text(text = stringResource(R.string.favorites)) }
                )
                VitesseIconButton(
                    icon = Icons.Outlined.Edit,
                    onClick = {
                        viewModel.updateApplicantFavoriteState()
                        navigateToEditApplicant(applicant.id)
                              },
                    modifier = modifier,
                    tooltip = { Text(text = stringResource(R.string.edit)) }
                )
                VitesseIconButton(
                    icon = Icons.Outlined.Delete,
                    onClick = { showDeleteConfirmationDialog = true },
                    modifier = modifier,
                    tooltip = { Text(text = stringResource(R.string.delete)) }
                )
            }
        ) },
    ){ topAppBarPadding ->
        ApplicantDetailBody(
            modifier = modifier.padding(topAppBarPadding),
            applicant = applicant,
            exchangeRate = exchangeRate,
            showCallAlertDialog = viewModel::showCallAlertDialog,
            context = context
        )
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun ApplicantDetailBody(
    modifier: Modifier = Modifier,
    applicant: Applicant,
    exchangeRate: ExchangeRate,
    showCallAlertDialog: (Boolean) -> Unit,
    context: Context
){
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(horizontal = 20.dp)
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally
    ){
        with (applicant) {
            Image(
                painter = rememberAsyncImagePainter(photoUri?:R.drawable.placeholder),
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .height(dimensionResource(R.dimen.image_height))
                    .padding(top = 7.dp, bottom = 22.dp),
                contentDescription = null
            )
            Row(
                horizontalArrangement = Arrangement.Center
            ){
                ApplicantDetailContact(
                    icon = Icons.Outlined.Call,
                    text = stringResource(R.string.call),
                    onClick = {
                        try { context.callPhoneNumber(phone) }
                        catch (e: Exception) { showCallAlertDialog(true) }
                    }
                )
                ApplicantDetailContact(
                    icon = ImageVector.vectorResource(R.drawable.chat_24px),
                    text = stringResource(R.string.sms),
                    onClick = { context.sendSms(phone) }
                )
                ApplicantDetailContact(
                    icon = Icons.Outlined.Email,
                    text = stringResource(R.string.email),
                    onClick = { context.sendEmail(email) }
                )
            }
            ApplicantDetailCard(header = stringResource(R.string.about)){
                TextBodyLarge(text = birthDate?.run{
                    stringResource(R.string.years_old, toLocalDateString(), getAge())
                }?:"")
                TextBodyMedium(
                    text = stringResource(R.string.birthday),
                    modifier = Modifier.padding(bottom = 11.dp)
                )
            }
            ApplicantDetailCard(header = stringResource(R.string.expected_salary)){
                TextBodyLarge(
                    text = salary.toLocalCurrencyString(),
                    modifier = Modifier.padding(bottom = 32.dp)
                )
                debugLog("LocalLanguage = ${Locale.getDefault().language}")
                val foreignCurrencySalary : String = when (Locale.getDefault().language) {
                    Locale.FRENCH.language -> (salary * exchangeRate.eur.gbp).toGbpString()
                    Locale.ENGLISH.language -> (salary * exchangeRate.gbp.eur).toEurString()
                    else -> (salary * exchangeRate.eur.gbp).toGbpString() // fallback to GBP
                }
                TextBodyMedium(text = (stringResource(R.string.or, foreignCurrencySalary)))
            }
            ApplicantDetailCard(
                header = stringResource(R.string.notes),
                modifier = Modifier.padding(bottom = 30.dp)
            ){
                TextBodyMedium(text = note)
            }
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
fun ApplicantDetailContact(
    icon: ImageVector,
    text: String,
    onClick:  () -> Unit,
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
                .background(
                    color = MaterialTheme.colorScheme.secondary,
                    shape = CircleShape
                )
                .padding(8.dp)
        )
        TextBodySmall(text = text)
    }
}

@Composable
fun VitesseAlertDialog(
    onConfirm: () -> Unit,
    onDismiss: () -> Unit,
    modifier: Modifier = Modifier,
    title: String,
    text: String,
    dismissText: String,
    confirmText: String,
) {
    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text(text = title) },
        text = { Text(text) },
        confirmButton = {
            TextButton(onClick = onConfirm, colors = ButtonDefaults.textButtonColors(contentColor = MaterialTheme.colorScheme.onPrimary)) {
                Text(confirmText)
            }
        },
        containerColor = MaterialTheme.colorScheme.primary,
        dismissButton = {
            TextButton(onClick = onDismiss, colors = ButtonDefaults.textButtonColors(contentColor = MaterialTheme.colorScheme.onPrimary)) {
                Text(dismissText)
            }
        },
        modifier = modifier
    )
}

//@Composable
//fun VitesseAsyncImage(
//    modifier: Modifier = Modifier,
//    photoUri: String? = null,
//    imageLoader: ImageLoader,
//    context: Context,
//    contentScale: ContentScale = ContentScale.Fit,
//    crossfade: Boolean = true
//){
//    val request = ImageRequest.Builder(context = context)
//        .data(photoUri?:R.drawable.placeholder)
//        .size(Size.ORIGINAL)
//        .scale(Scale.FILL)
//        .crossfade(crossfade)
//        .build()
//    AsyncImage(
//        model = request,
//        imageLoader = imageLoader,
//        contentDescription = null,
//        contentScale = contentScale,
//        modifier = modifier
//    )
//}