package com.example.vitesse.ui.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.PrimaryTabRow
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SearchBar
import androidx.compose.material3.SearchBarDefaults
import androidx.compose.material3.Tab
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.vitesse.R
import com.example.vitesse.data.model.Applicant
import com.example.vitesse.ui.AppViewModelProvider
import com.example.vitesse.ui.navigation.NavigationDestination

object HomeDestination : NavigationDestination {
    override val route = "home"
    override val titleRes = R.string.home_screen
}

/**
 * Entry route for Home screen
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    viewModel: HomeViewModel = viewModel(factory = AppViewModelProvider.Factory)
) {
    val uiState by viewModel.uiState.collectAsState()

    Scaffold(
        modifier = modifier,
        floatingActionButton = {
            FloatingActionButton(
                onClick = { /* do something */ }
            ) {
                Icon(Icons.Filled.Add, "Localized description")
            }
        }

    ){innerPadding ->
        HomeBody(
            applicantList = uiState.allApplicantList,
            searchQuery = uiState.searchQuery,
            onItemClick = { /*TODO*/ },
            modifier = modifier.fillMaxSize(),
            contentPadding = innerPadding,
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun HomeBody(
    applicantList: List<Applicant>,
    searchQuery: String,
    onItemClick: (Int) -> Unit,
    modifier: Modifier = Modifier,
    contentPadding: PaddingValues = PaddingValues(0.dp),
    ){
//    Text(
//        text = "Hello!",
//        modifier = modifier.padding(contentPadding),
//    )
    Column(
        modifier = modifier.padding(contentPadding),
    ){
        val onActiveChange = { active: Boolean ->}
        val colors1 = SearchBarDefaults.colors()
        SearchBar(
            inputField = {
                SearchBarDefaults.InputField(
                    query = searchQuery,
                    onQueryChange = {},
                    onSearch = {query -> applicantList.filter { it.firstName.contains(query) || it.lastName.contains(query) }},
                    expanded = false,
                    onExpandedChange = onActiveChange,
                    enabled = true,
                    placeholder = { Text(text = stringResource(R.string.search)) },
                    leadingIcon = null,
                    trailingIcon = null,
                    interactionSource = null,
                )
            },
            expanded = false,
            onExpandedChange = onActiveChange,
            modifier = Modifier,
            shape = SearchBarDefaults.inputFieldShape,
            colors = colors1,
            tonalElevation = SearchBarDefaults.TonalElevation,
            shadowElevation = SearchBarDefaults.ShadowElevation,
            windowInsets = SearchBarDefaults.windowInsets,
            {
                Text(
                    text = "Hello!",
                    modifier = modifier.padding(contentPadding),
                    )
            },
        )

        val tabs = listOf(stringResource(R.string.all), stringResource(R.string.favorites))
        var selectedTabIndex by remember { mutableIntStateOf(0) }

        PrimaryTabRow(
            selectedTabIndex = selectedTabIndex,
            modifier = Modifier.fillMaxWidth(),

        ) {
            tabs.forEachIndexed { index, title ->
                Tab(
                    selected = selectedTabIndex == index,
                    onClick = { selectedTabIndex = index },
                    text = { Text(
                        text = title,
                        fontWeight = if (selectedTabIndex == index) FontWeight.Bold else FontWeight.Normal,
                    ) }
                )

            }
        }
        // Tab content
        when (selectedTabIndex) {
            0 -> ApplicantList(
                applicantList = applicantList,
                onItemClick = onItemClick,
                modifier = modifier,
            )

            1 -> ApplicantList(
                applicantList = applicantList.filter { it.isFavorite },
                onItemClick = onItemClick,
                modifier = modifier,
            )
        }
    }
}

@Composable
fun ApplicantCard(
    applicant: Applicant,
    modifier: Modifier = Modifier
){
    Row(
        modifier = modifier
    ){
        Image(
            painter = painterResource(R.drawable.ic_launcher_foreground),
            contentDescription = null,
            modifier = Modifier,
        )
        Column(
            modifier = Modifier,
        ){
            Text(
                text = "${applicant.firstName} ${applicant.lastName}",
                modifier = Modifier,
            )
            Text(
                text = applicant.note,
            )
        }
    }
}

@Composable
fun ApplicantList(
    applicantList: List<Applicant>,
    onItemClick: (Int) -> Unit,
    modifier: Modifier = Modifier,
){
    LazyColumn(
        modifier = modifier,
    ){
        items(applicantList){applicant ->
            ApplicantCard(
                applicant = applicant,
            )
        }
    }
}
