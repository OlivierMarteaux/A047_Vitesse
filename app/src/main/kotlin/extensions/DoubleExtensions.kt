package extensions

import java.text.DecimalFormat
import java.text.NumberFormat
import java.util.Locale

fun Double.toLocalCurrencyString(locale: Locale = Locale.getDefault()): String {
    val currencyFormatter = NumberFormat.getCurrencyInstance(locale)
    currencyFormatter.maximumFractionDigits = 0
    return currencyFormatter.format(this)
}

fun Double.toGbpString(): String {
    val formatter = NumberFormat.getInstance() as DecimalFormat
    formatter.maximumFractionDigits = 2
    formatter.maximumFractionDigits = 2
    formatter.isGroupingUsed = false // removes comma and space

    val formattedNumber = formatter.format(this)
    return "£ $formattedNumber" // add space between £ and number
}

fun Double.toEurString(): String {
    val formatter = NumberFormat.getInstance() as DecimalFormat
    formatter.maximumFractionDigits = 2
    formatter.maximumFractionDigits = 2
    formatter.isGroupingUsed = false // removes comma and space

    val formattedNumber = formatter.format(this)
    return "€ $formattedNumber" // add space between € and number
}


