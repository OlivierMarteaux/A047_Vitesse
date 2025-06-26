package com.example.vitesse.ui.components.texts

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color

/**
 * Displays text styled with [MaterialTheme.typography.bodySmall] with customizable text color.
 *
 * @param text The string content to display.
 * @param modifier Optional [Modifier] to be applied to the text layout.
 * @param color Text color to use, default is [Color.Black].
 */
@Composable
fun TextBodySmall (
    text:String,
    modifier: Modifier = Modifier,
    color: Color = Color.Black
){
    Text(
        text = text,
        style = MaterialTheme.typography.bodySmall,
        color = color,
        modifier = modifier
    )
}