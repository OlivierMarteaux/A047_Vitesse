package extensions

import java.text.Normalizer

/**
 * Checks if the string is a valid email format.
 *
 * Uses a regex pattern to validate basic email structure.
 *
 * @receiver The string to validate.
 * @return True if the string matches a valid email pattern, false otherwise.
 */
fun String.isValidEmail(): Boolean {
    val emailRegex = Regex("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$")
    return this.matches(emailRegex)
}

/**
 * Removes accents and diacritical marks from the string, returning a normalized ASCII representation.
 *
 * @receiver The string potentially containing accented characters.
 * @return The string with accents removed.
 */
fun String.stripAccents(): String {
    val normalized = Normalizer.normalize(this, Normalizer.Form.NFD)
    return normalized.replace("\\p{InCombiningDiacriticalMarks}+".toRegex(), "")
}