package com.example.vitesse.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.unit.dp
import coil3.compose.rememberAsyncImagePainter
import coil3.request.ImageRequest
import coil3.request.crossfade
import coil3.request.error
import coil3.request.fallback
import coil3.request.placeholder
import com.example.vitesse.R

@Composable
fun VitesseImage(
    photoUri: String?,
    onClick: () -> Unit = {}
){
    val context = LocalContext.current

    val imagePainter = rememberAsyncImagePainter(
        model = ImageRequest.Builder(context)
            .data(photoUri)
            .crossfade(true)
            .placeholder(R.drawable.placeholder) // shown while loading
            .error(R.drawable.placeholder)       // shown on error
            .fallback(R.drawable.placeholder)    // shown if `photoUri` is null
            .build()
    )
//    Box (
//        modifier = Modifier
//            .width(dimensionResource(R.dimen.image_width))
//            .height(dimensionResource(R.dimen.image_height))
//            .clickable { onClick() }
//            .clip(RoundedCornerShape(8.dp)) // Optional: rounded edges
//    ){
        Image(
//            painter = rememberAsyncImagePainter(photoUri ?: R.drawable.placeholder),
            painter = imagePainter,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .clickable { onClick() }
                .width(dimensionResource(R.dimen.image_width))
                .height(dimensionResource(R.dimen.image_height))
                .padding(top = 7.dp, bottom = 22.dp),
            contentDescription = null
        )
}