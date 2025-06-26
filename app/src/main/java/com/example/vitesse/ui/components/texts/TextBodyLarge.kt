package com.example.vitesse.ui.components.texts

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

/**
 * Displays text styled with [MaterialTheme.typography.bodyLarge].
 *
 * @param text The string content to display.
 * @param modifier Optional [Modifier] to be applied to the text layout.
 */
@Composable
fun TextBodyLarge (
    text:String,
    modifier: Modifier = Modifier
){
    Text(
        text = text,
        style = MaterialTheme.typography.bodyLarge,
        modifier = modifier
    )
}