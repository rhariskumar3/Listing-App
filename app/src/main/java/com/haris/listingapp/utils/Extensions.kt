package com.haris.listingapp.utils

import android.content.Context
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.core.view.isGone


fun View.dismissKeyboard() {
    val imm = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    imm.hideSoftInputFromWindow(windowToken, 0)
    isGone
}

fun Long.toDenotedString() = when {
    this >= 1_00_00_000 -> "${this / 1_00_00_000} Cr"
    this >= 1_00_000 -> "${this / 1_00_000} Lakh"
    else -> "$this"
}