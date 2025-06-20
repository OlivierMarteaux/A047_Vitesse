package com.example.vitesse.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.unit.dp
import coil3.compose.rememberAsyncImagePainter
import com.example.vitesse.R

@Composable
fun VitesseImage(
    photoUri: String?,
    onClick: () -> Unit = {}
){
    Image(
        painter = rememberAsyncImagePainter(photoUri?: R.drawable.placeholder),
        contentScale = ContentScale.Crop,
        modifier = Modifier
            .clickable {onClick()}
            .height(dimensionResource(R.dimen.image_height))
            .padding(top = 7.dp, bottom = 22.dp),
        contentDescription = null
    )
}