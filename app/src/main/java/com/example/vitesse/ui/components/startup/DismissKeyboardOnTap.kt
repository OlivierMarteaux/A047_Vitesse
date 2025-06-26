package com.example.vitesse.ui.components.startup

import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalFocusManager

/**
 * A composable wrapper that dismisses the on-screen keyboard when the user taps outside of a focused input field.
 *
 * This is useful for improving user experience in forms or input-heavy screens where the keyboard
 * should be hidden when interacting with non-editable areas of the UI.
 *
 * @param modifier The [Modifier] to apply to the root [Box] layout. Defaults to [Modifier].
 * @param content The content composable to be displayed inside the tap-detecting container.
 *
 * Usage:
 * ```
 * DismissKeyboardOnTapOutside {
 *     Column {
 *         TextField(...)
 *         Button(...)
 *     }
 * }
 * ```
 */
@Composable
fun DismissKeyboardOnTapOutside(
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit
) {
    val focusManager = LocalFocusManager.current

    Box(
        modifier = modifier
            .fillMaxSize()
            .pointerInput(Unit) {
                detectTapGestures(onTap = {
                    focusManager.clearFocus()
                })
            }
    ) {
        content()
    }
}