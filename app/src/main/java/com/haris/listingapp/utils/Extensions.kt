package com.haris.listingapp.utils

import android.content.Context
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.core.view.isGone

/* For close keyboard
*  required: Current View */
fun View.dismissKeyboard() {
    val imm = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    imm.hideSoftInputFromWindow(windowToken, 0)
    isGone
}

/* For convert Long to simple denoted string like 1 cr, 1 lakh
*  required: Long
*  return: String */
fun Long.toDenotedString() = when {
    this >= 1_00_00_000 -> "${this / 1_00_00_000} Cr"
    this >= 1_00_000 -> "${this / 1_00_000} Lakh"
    else -> "$this"
}