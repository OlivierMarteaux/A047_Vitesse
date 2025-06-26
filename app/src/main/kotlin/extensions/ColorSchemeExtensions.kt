package extensions

import androidx.compose.material3.ColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
/**
 * Extension properties on [ColorScheme] providing custom color definitions
 * used throughout the Vitesse app's light theme.
 *
 * These colors represent the app-specific primary, secondary, and on-color variants
 * designed to maintain consistent branding and UI appearance.
 *
 * Each color is exposed as a [@Composable] read-only property to be used
 * seamlessly within Compose UI elements relying on the current [ColorScheme].
 *
 * Example usage:
 * ```
 * val primaryColor = MaterialTheme.colorScheme.vitessePrimaryLight
 * ```
 *
 * Colors included:
 * - [vitessePrimaryLight]: Primary brand color in light theme.
 * - [vitesseOnPrimaryLightDivider]: Divider color on primary backgrounds.
 * - [vitessePrimaryVariantLight]: Variant of primary color.
 * - [vitesseOnPrimaryLight]: Content color on primary backgrounds (usually white).
 * - [vitesseSecondaryLight]: Secondary brand color in light theme.
 * - [vitesseSecondaryVariantLight]: Variant of secondary color with transparency.
 * - [vitesseOnSecondaryBlackLight]: Black text or icon color on secondary backgrounds.
 * - [vitesseOnSecondaryGrayLight]: Semi-transparent gray on secondary backgrounds.
 * - [vitesseOnSecondaryWhiteLight]: White text or icon color on secondary backgrounds.
 */
val ColorScheme.vitessePrimaryLight @Composable get() = Color(0xFFDF9780)
val ColorScheme.vitesseOnPrimaryLightDivider @Composable get() = Color(0xFF5E3834)
val ColorScheme.vitessePrimaryVariantLight @Composable get() = Color(0xFF749FB8)
val ColorScheme.vitesseOnPrimaryLight @Composable get() = Color(0xFFFFFFFF)
val ColorScheme.vitesseSecondaryLight @Composable get() = Color(0xFFE8B952)
val ColorScheme.vitesseSecondaryVariantLight @Composable get() = Color(0x10E8B952)
val ColorScheme.vitesseOnSecondaryBlackLight @Composable get() = Color(0xFF000000)
val ColorScheme.vitesseOnSecondaryGrayLight @Composable get() = Color(0x3D000000)
val ColorScheme.vitesseOnSecondaryWhiteLight @Composable get() = Color(0xFFFFFFFF)