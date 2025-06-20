package com.example.vitesse.ui.home

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.consumeWindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.PrimaryTabRow
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SearchBar
import androidx.compose.material3.SearchBarDefaults
import androidx.compose.material3.Tab
import androidx.compose.material3.Text
import androidx.compose.material3.ripple
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil3.compose.rememberAsyncImagePainter
import com.example.vitesse.R
import com.example.vitesse.data.model.Applicant
import com.example.vitesse.ui.AppViewModelProvider
import com.example.vitesse.ui.components.texts.TextBodyLarge
import com.example.vitesse.ui.components.texts.TextBodyMedium
import com.example.vitesse.ui.home.HomeUiState.Empty
import com.example.vitesse.ui.home.HomeUiState.Error
import com.example.vitesse.ui.home.HomeUiState.Loading
import com.example.vitesse.ui.home.HomeUiState.Success
import com.example.vitesse.ui.navigation.NavigationDestination
import com.example.vitesse.ui.theme.Roboto

object HomeDestination : NavigationDestination {
    override val route = "home"
    override val titleRes = R.string.home_screen
}

/**
 * Entry route for Home screen
 */
@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    viewModel: HomeViewModel = viewModel(factory = AppViewModelProvider.Factory),
    navigateToApplicantDetail: (Int) -> Unit,
    navigateToAddApplicant: () -> Unit,
) {
    val query = viewModel.query
    val applicantList by viewModel.getApplicants(query).collectAsState(initial = listOf())

    Scaffold(
        modifier = modifier,
        floatingActionButton = {
            FloatingActionButton(
//                containerColor = MaterialTheme.colorScheme.surfaceContainerHigh,
//                contentColor = MaterialTheme.colorScheme.primary,
                onClick = navigateToAddApplicant,
                modifier = Modifier.padding(bottom = 16.dp)
            ) {
                Icon(Icons.Filled.Add, "Localized description")
            }
        }
    ){ innerPadding ->
        HomeBody(
            applicantList = applicantList,
            query = query,
            modifier = modifier
                .consumeWindowInsets(innerPadding)
                .fillMaxSize(),
            contentPadding = innerPadding,
            viewModel = viewModel,
            navigateToApplicantDetail = navigateToApplicantDetail,
        )
    }
}

@Composable
private fun HomeBody(
    applicantList: List<Applicant>,
    query: String,
    modifier: Modifier = Modifier,
    contentPadding: PaddingValues = PaddingValues(0.dp),
    viewModel: HomeViewModel,
    navigateToApplicantDetail: (Int) -> Unit,
    ){
    Column(
        modifier = modifier
            .padding(contentPadding)
            .fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
    ){
        ApplicantSearchBar(
            query = query,
            onQueryChange = viewModel::updateQuery,
            modifier = Modifier,
        )
        ApplicantTabs(
            homeUiState = viewModel.uiState,
            applicantList = applicantList,
            navigateToApplicantDetail = navigateToApplicantDetail,
            modifier = modifier,
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ApplicantSearchBar(
    query: String,
    onQueryChange: (String) -> Unit,
    modifier: Modifier = Modifier
){
    val onActiveChange = { _: Boolean ->}
    SearchBar(
        inputField = {
            SearchBarDefaults.InputField(
                query = query,
                onQueryChange = onQueryChange,
                onSearch = {},
                expanded = false,
                onExpandedChange = onActiveChange,
                enabled = true,
                placeholder = { Text(text = stringResource(R.string.search)) },
                leadingIcon = null,
                trailingIcon = { Icon(Icons.Filled.Search, stringResource(R.string.search)) },
                interactionSource = null,
                colors = SearchBarDefaults.inputFieldColors(cursorColor = MaterialTheme.colorScheme.onPrimaryContainer),
            )
        },
        expanded = false,
        onExpandedChange = onActiveChange,
        modifier = modifier.padding(horizontal = 24.dp),
        shape = SearchBarDefaults.inputFieldShape,
        colors = SearchBarDefaults.colors(MaterialTheme.colorScheme.surfaceContainerHigh),
        tonalElevation = SearchBarDefaults.TonalElevation,
        shadowElevation = SearchBarDefaults.ShadowElevation,
        windowInsets = SearchBarDefaults.windowInsets,
        content = {},
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ApplicantTabs(
    homeUiState: HomeUiState,
    applicantList: List<Applicant>,
    navigateToApplicantDetail: (Int) -> Unit,
    modifier: Modifier = Modifier,
){
    val tabs = listOf(stringResource(R.string.all), stringResource(R.string.favorites))
    var selectedTabIndex by remember { mutableIntStateOf(0) }

    PrimaryTabRow(
        selectedTabIndex = selectedTabIndex,
        modifier = Modifier
            .fillMaxWidth()
            .padding(10.dp),
        containerColor = Color.Transparent,
        ) {
        tabs.forEachIndexed { index, title ->
            Tab(
                selected = selectedTabIndex == index,
                onClick = { selectedTabIndex = index },
                text = { Text(
                    text = title,
//                    color = if (selectedTabIndex == index) MaterialTheme.colorScheme.primary
//                            else MaterialTheme.colorScheme.onSurfaceVariant,
                    fontFamily = Roboto,
                    fontWeight = FontWeight.Medium,
                    fontSize = 14.sp,
                    lineHeight = 20.sp,
                    letterSpacing = 0.1.sp
                ) }
            )
        }
    }
    // Tab content
    when (selectedTabIndex) {
        0 -> ApplicantCardList(
            homeUiState = homeUiState,
            applicantList = applicantList,
            navigateToApplicantDetail = navigateToApplicantDetail,
            modifier = modifier,
        )

        1 -> ApplicantCardList(
            homeUiState = homeUiState,
            applicantList = applicantList.filter{it.isFavorite},
            navigateToApplicantDetail = navigateToApplicantDetail,
            modifier = modifier,
        )
    }
}

@Composable
fun ApplicantCardList(
    homeUiState: HomeUiState,
    applicantList: List<Applicant>,
    navigateToApplicantDetail: (Int) -> Unit,
    modifier: Modifier = Modifier,
){
    when (homeUiState) {
        is Success -> {
            LazyColumn(modifier = modifier) {
                items(applicantList) { applicant ->
                    val interactionSource = remember { MutableInteractionSource() }
                    ApplicantCard(
                        applicant = applicant,
                        modifier = modifier.clickable(
                            interactionSource = interactionSource,
                            indication = ripple(color = MaterialTheme.colorScheme.primary),
                            ){navigateToApplicantDetail(applicant.id)},
                        )
                }
            }
        }
        is Loading -> {
            Column(
                modifier = modifier.fillMaxSize(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally,
                ) { CircularProgressIndicator() }
        }
        is Empty -> {
            Column(
                modifier = modifier.fillMaxSize(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally,
                ) {
                Text(
                    text = stringResource(R.string.no_candidate),
                    style = MaterialTheme.typography.bodyLarge
                )
            }
        }
        is Error -> {
            Column(
                modifier = modifier.fillMaxSize(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                Text(
                    text = "error",
                    style = MaterialTheme.typography.bodyLarge
                )
            }
        }
    }
}

@Composable
fun ApplicantCard(
    applicant: Applicant,
    modifier: Modifier = Modifier,
){
    with(applicant){
        Row(
            modifier = modifier.padding(start = 16.dp, top = 12.dp, end = 24.dp, bottom =12.dp),
        ){
            Image(
                painter = rememberAsyncImagePainter(photoUri?:R.drawable.placeholder),
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .size(56.dp)
                    .border(Dp.Hairline, MaterialTheme.colorScheme.outlineVariant)
            )
            Column(modifier = Modifier.padding(start = 16.dp)){
                TextBodyLarge("$firstName ${lastName.uppercase()}")
                TextBodyMedium(
                    text = note,
                    maxLines = 2,
                    )
            }
        }
    }
}