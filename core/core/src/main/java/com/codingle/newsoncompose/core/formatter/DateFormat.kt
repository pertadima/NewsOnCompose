package com.codingle.newsoncompose.core.formatter

import java.text.SimpleDateFormat
import java.util.Locale

object DateFormat {
    fun formatReadableDateLegacy(isoString: String): String {
        val isoFormat = SimpleDateFormat(RESPONSE_FORMAT, Locale.getDefault())

        val date = isoFormat.parse(isoString)
        val outputFormat = SimpleDateFormat(DISPLAY_FORMAT, Locale.getDefault())
        return outputFormat.format(date!!)
    }

    private const val RESPONSE_FORMAT = "yyyy-MM-dd'T'HH:mm:ss'Z'"
    private const val DISPLAY_FORMAT = "MMM dd, yyyy, hh:mm a"
}