package com.example.vitesse.ui.components

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

/**
 * A custom themed alert dialog with confirm and dismiss buttons.
 *
 * @param onConfirm Lambda invoked when the confirm button is clicked.
 * @param onDismiss Lambda invoked when the dialog is dismissed or dismiss button clicked.
 * @param modifier Optional [Modifier] for styling the dialog container.
 * @param title The title text displayed at the top of the dialog.
 * @param text The main body text content of the dialog.
 * @param dismissText The text label for the dismiss button.
 * @param confirmText The text label for the confirm button.
 */
@Composable
fun VitesseAlertDialog(
    onConfirm: () -> Unit,
    onDismiss: () -> Unit,
    modifier: Modifier = Modifier,
    title: String,
    text: String,
    dismissText: String,
    confirmText: String,
) {
    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text(text = title) },
        titleContentColor = MaterialTheme.colorScheme.onPrimary,
        text = { Text(text) },
        textContentColor = MaterialTheme.colorScheme.onPrimary,
        confirmButton = {
            TextButton(
                onClick = onConfirm,
                colors = ButtonDefaults.textButtonColors(
                    contentColor = MaterialTheme.colorScheme.onPrimary
                )) { Text(confirmText)}
        },
        containerColor = MaterialTheme.colorScheme.primary,
        dismissButton = {
            TextButton(
                onClick = onDismiss,
                colors = ButtonDefaults.textButtonColors(
                    contentColor = MaterialTheme.colorScheme.onPrimary
                )) { Text(dismissText) }
        },
        modifier = modifier
    )
}