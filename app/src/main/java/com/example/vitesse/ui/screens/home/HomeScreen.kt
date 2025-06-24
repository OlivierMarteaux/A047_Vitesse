package com.example.vitesse.ui.screens.home

import android.os.Build
import androidx.annotation.RequiresApi
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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.vitesse.R
import com.example.vitesse.data.model.Applicant
import com.example.vitesse.ui.AppViewModelProvider
import com.example.vitesse.ui.components.VitesseImage
import com.example.vitesse.ui.components.texts.TextBodyLarge
import com.example.vitesse.ui.components.texts.TextBodyMedium
import com.example.vitesse.ui.components.texts.TextTitleSmall
import com.example.vitesse.ui.navigation.NavigationDestination
import com.example.vitesse.ui.screens.home.HomeUiState.Empty
import com.example.vitesse.ui.screens.home.HomeUiState.Error
import com.example.vitesse.ui.screens.home.HomeUiState.Loading
import com.example.vitesse.ui.screens.home.HomeUiState.Success

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
                onClick = navigateToAddApplicant,
                modifier = Modifier.padding(bottom = 16.dp)
            ) { Icon(Icons.Filled.Add, stringResource(R.string.add_an_applicant)) }
        }
    ){ innerPadding ->
        HomeBody(
            applicantList = applicantList,
            query = query,
            modifier = Modifier
                .consumeWindowInsets(innerPadding)
                .fillMaxSize(),
            contentPadding = innerPadding,
            navigateToApplicantDetail = navigateToApplicantDetail,
            onQueryChange = viewModel::updateQuery,
            uiState = viewModel.uiState,
        )
    }
}

@Composable
private fun HomeBody(
    applicantList: List<Applicant>,
    query: String,
    modifier: Modifier = Modifier,
    contentPadding: PaddingValues = PaddingValues(0.dp),
    navigateToApplicantDetail: (Int) -> Unit,
    onQueryChange: (String) -> Unit,
    uiState: HomeUiState
    ){
    Column(
        modifier = modifier
            .padding(contentPadding)
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
    ){
        HomeSearchBar(
            query = query,
            onQueryChange = onQueryChange,
        )
        HomeTabs(
            homeUiState = uiState,
            applicantList = applicantList,
            navigateToApplicantDetail = navigateToApplicantDetail,
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeSearchBar(
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
                placeholder = { Text(stringResource(R.string.search)) },
                trailingIcon = { Icon(Icons.Filled.Search, stringResource(R.string.search)) },
                colors = SearchBarDefaults.inputFieldColors(cursorColor = MaterialTheme.colorScheme.onPrimaryContainer),
            )
        },
        expanded = false,
        onExpandedChange = onActiveChange,
        modifier = modifier.padding(horizontal = 24.dp),
        colors = SearchBarDefaults.colors(MaterialTheme.colorScheme.surfaceContainerHigh),
        content = {},
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeTabs(
    homeUiState: HomeUiState,
    applicantList: List<Applicant>,
    navigateToApplicantDetail: (Int) -> Unit,
    modifier: Modifier = Modifier,
){
    val tabs = listOf(stringResource(R.string.all), stringResource(R.string.favorites))
    var selectedTabIndex by remember { mutableIntStateOf(0) }

    PrimaryTabRow(
        selectedTabIndex = selectedTabIndex,
        modifier = modifier
            .fillMaxWidth()
            .padding(10.dp),
        containerColor = Color.Transparent,
        ) {
        tabs.forEachIndexed { index, title ->
            Tab(
                selected = selectedTabIndex == index,
                onClick = { selectedTabIndex = index },
                text = { TextTitleSmall(title) }
            )
        }
    }
    // Tab content
    when (selectedTabIndex) {
        0 -> HomeCardList(
            homeUiState = homeUiState,
            applicantList = applicantList,
            navigateToApplicantDetail = navigateToApplicantDetail,
        )

        1 -> HomeCardList(
            homeUiState = homeUiState,
            applicantList = applicantList.filter{it.isFavorite},
            navigateToApplicantDetail = navigateToApplicantDetail,
        )
    }
}

@Composable
fun HomeCardList(
    homeUiState: HomeUiState,
    applicantList: List<Applicant>,
    navigateToApplicantDetail: (Int) -> Unit,
    modifier: Modifier = Modifier,
){
    when (homeUiState) {
        is Success -> {
            LazyColumn(modifier) {
                items(applicantList) { applicant ->
                    val interactionSource = remember { MutableInteractionSource() }
                    HomeCard(
                        applicant = applicant,
                        modifier = Modifier.clickable(
                            interactionSource = interactionSource,
                            indication = ripple(color = MaterialTheme.colorScheme.primary),
                            ){ navigateToApplicantDetail(applicant.id) },
                        )
                }
            }
        }
        is Loading -> { HomeStateColumn(modifier) { CircularProgressIndicator() } }
        is Empty -> { HomeStateColumn(modifier) { TextBodyLarge(stringResource(R.string.no_candidate)) } }
        is Error -> { HomeStateColumn(modifier) { TextBodyLarge(stringResource(R.string.error))} }
    }
}

@Composable
fun HomeCard(
    applicant: Applicant,
    modifier: Modifier = Modifier,
){
    with(applicant){
        Row(
            modifier = modifier
                .padding(start = 16.dp, top = 12.dp, end = 24.dp, bottom =12.dp)
                .fillMaxWidth(),
        ){
            VitesseImage(
                photoUri = photoUri,
                modifier = Modifier.size(56.dp)
            )
            Column(modifier = Modifier.padding(start = 16.dp)){
                TextBodyLarge("$firstName ${lastName.uppercase()}")
                TextBodyMedium(text = note, maxLines = 2)
            }
        }
    }
}

@Composable
fun HomeStateColumn(
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit
) {
    Column(
        modifier = modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) { content() }
}