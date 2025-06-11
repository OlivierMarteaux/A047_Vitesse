package com.example.vitesse.ui.addApplicant

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.outlined.Call
import androidx.compose.material.icons.outlined.Edit
import androidx.compose.material3.FabPosition
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.vitesse.R
import com.example.vitesse.TextBodyLarge
import com.example.vitesse.VitesseIcon
import com.example.vitesse.VitesseTopAppBar
import com.example.vitesse.ui.navigation.NavigationDestination

object AddApplicantDestination : NavigationDestination {
    override val route = "add_applicant"
    override val titleRes = R.string.add_applicant_screen
}

@Composable
fun AddApplicantScreen(
    modifier: Modifier = Modifier,
//    viewModel: AddApplicantViewModel = viewModel(factory = AppViewModelProvider.Factory),
    navigateBack: () -> Unit
) {
    TextBodyLarge(text = "text_exemple",)
//    AddOrEditApplicantBody(
//        modifier = modifier
//    )
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            VitesseTopAppBar(
                title = "Alice JOHNSON",
                modifier = Modifier,
                navigateBack = navigateBack,
            )
        },
        floatingActionButtonPosition = FabPosition.Center,
        floatingActionButton = {
            FloatingActionButton(
                containerColor = MaterialTheme.colorScheme.surfaceContainerHigh,
                contentColor = MaterialTheme.colorScheme.primary,
                onClick = {/*TODO*/},
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 16.dp)
            ) {
                Text("Save")
            }
        }
    ) { topAppBarPadding ->
        AddOrEditApplicantBody(
            modifier = modifier.padding(topAppBarPadding)
        ) {
        }
    }
}

@Composable
fun AddOrEditApplicantBody(
    modifier: Modifier = Modifier,
    onSave: () -> Unit
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
        AddOrEditApplicantCard(
            icon = Icons.Default.Person,
            label = stringResource(R.string.first_name)
        )
        AddOrEditApplicantCard(
            label = stringResource(R.string.last_name)
        )
        AddOrEditApplicantCard(
            icon = Icons.Outlined.Call,
            label = stringResource(R.string.phone_number)
        )
        AddOrEditApplicantCard(
            icon = ImageVector.vectorResource(id = R.drawable.chat_24px),
            label = stringResource(R.string.email)
        )
        AddOrEditApplicantCard(
            icon = ImageVector.vectorResource(id = R.drawable.cake_24dp),
            label = stringResource(R.string.birthday)
        )
        AddOrEditApplicantCard(
            icon = ImageVector.vectorResource(id = R.drawable.money_24dp),
            label = stringResource(R.string.salary_expectations)
        )
        AddOrEditApplicantCard(
            icon = Icons.Outlined.Edit,
            label = stringResource(R.string.notes)
        )
    }
}

@Composable
fun AddOrEditApplicantCard(
    modifier: Modifier = Modifier,
    icon: ImageVector? = null,
    label: String,
){
    Row(modifier = modifier.padding(bottom = 45.dp)
    ){
        icon?.let{ VitesseIcon(icon = icon,) }?: Spacer(Modifier.size(24.dp))
        OutlinedTextField(
            value = "",
            onValueChange = {},
            label = { Text(label) },
            singleLine = true,
            modifier = modifier
                .padding(start = 15.dp)
                .weight(1f)
                .height(50.dp),
        )
    }
}

@Preview
@Composable
fun AddApplicantScreenPreview() {
    AddApplicantScreen(navigateBack = {})
}
