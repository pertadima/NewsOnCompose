package com.codingle.newsoncompose.core.util

import android.content.Context
import android.content.Intent
import android.content.Intent.ACTION_VIEW
import android.net.Uri

object Util {
    fun Context.openBrowser(url: String) {
        val browserIntent = Intent(ACTION_VIEW, Uri.parse(url))
        startActivity(browserIntent)
    }
}