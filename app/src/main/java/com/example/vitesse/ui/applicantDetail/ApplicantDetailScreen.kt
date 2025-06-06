package com.example.vitesse.ui.applicantDetail

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Call
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
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
        modifier = modifier,
        topBar = { ApplicantDetailTopAppBar() },
    ){ topAppBarPadding ->
        Column(
            modifier = modifier
                .fillMaxSize()
                .padding(topAppBarPadding)
        ){
            Image(painter = painterResource(R.drawable.ic_launcher_foreground), contentDescription = null)
            Row(
                horizontalArrangement = Arrangement.SpaceAround
            ){
                Column(){
                    Icon(Icons.Filled.Call, contentDescription = null)
                    Text(text = "text_exemple")
                }
                Column(){
                    Icon(painter = painterResource(R.drawable.chat_24px), contentDescription = null)
                    Text(text = "text_exemple")
                }
                Column(){
                    Icon(Icons.Filled.Email, contentDescription = null)
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
        title = { Text(text = "Alice JOHNSON") },
        modifier = modifier,
        navigationIcon = { Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = null) },
        actions = {
            Icon(Icons.Filled.Star, contentDescription = null)
            Icon(Icons.Filled.Edit, contentDescription = null)
            Icon(Icons.Filled.Delete, contentDescription = null)
        }
    )
}