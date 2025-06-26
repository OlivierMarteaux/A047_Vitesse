package com.example.vitesse.ui.components.texts

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow

/**
 * Displays text styled with [MaterialTheme.typography.bodyMedium], supporting text overflow and max lines.
 *
 * @param text The string content to display.
 * @param modifier Optional [Modifier] to be applied to the text layout.
 * @param maxLines Maximum number of text lines to display before truncating with ellipsis.
 */
@Composable
fun TextBodyMedium (
    text:String,
    modifier: Modifier = Modifier,
    maxLines: Int = Int.MAX_VALUE
){
    Text(
        text = text,
        style = MaterialTheme.typography.bodyMedium,
        modifier = modifier,
        maxLines = maxLines,
        overflow = TextOverflow.Ellipsis
    )
}