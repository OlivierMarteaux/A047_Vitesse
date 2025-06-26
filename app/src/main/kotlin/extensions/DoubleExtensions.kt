package extensions

import java.text.DecimalFormat
import java.text.NumberFormat
import java.util.Locale

/**
 * Formats the Double value as a local currency string based on the given [locale].
 * The formatted string contains no fraction digits.
 *
 * @receiver The numeric value to format.
 * @param locale The locale to format the currency for. Defaults to system default locale.
 * @return Formatted currency string without decimals (e.g., "$1000").
 */
fun Double.toLocalCurrencyString(locale: Locale = Locale.getDefault()): String {
    val currencyFormatter = NumberFormat.getCurrencyInstance(locale)
    currencyFormatter.maximumFractionDigits = 0
    return currencyFormatter.format(this)
}

/**
 * Formats the Double value as a GBP currency string with two decimals.
 * The returned string includes the £ symbol with a space separator.
 * No grouping (thousands separator) is used.
 *
 * @receiver The numeric value to format.
 * @return Formatted GBP currency string (e.g., "£ 1234.56").
 */
fun Double.toGbpString(): String {
    val formatter = NumberFormat.getInstance() as DecimalFormat
    formatter.maximumFractionDigits = 2
    formatter.maximumFractionDigits = 2
    formatter.isGroupingUsed = false // removes comma and space

    val formattedNumber = formatter.format(this)
    return "£ $formattedNumber" // add space between £ and number
}

/**
 * Formats the Double value as an EUR currency string with two decimals.
 * The returned string includes the € symbol with a space separator.
 * No grouping (thousands separator) is used.
 *
 * @receiver The numeric value to format.
 * @return Formatted EUR currency string (e.g., "€ 1234.56").
 */
fun Double.toEurString(): String {
    val formatter = NumberFormat.getInstance() as DecimalFormat
    formatter.maximumFractionDigits = 2
    formatter.maximumFractionDigits = 2
    formatter.isGroupingUsed = false // removes comma and space

    val formattedNumber = formatter.format(this)
    return "€ $formattedNumber" // add space between € and number
}


