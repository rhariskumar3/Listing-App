package com.haris.listingapp.utils

import android.view.View
import android.widget.ImageView
import androidx.core.view.isGone
import androidx.databinding.BindingAdapter
import coil.load
import com.haris.listingapp.App

@BindingAdapter("isGone")
fun View.isGone(value: Boolean = false) {
    isGone = value
}

@BindingAdapter("imageSvg")
fun ImageView.loadSVG(url: String?) {
    if (url.isNullOrEmpty().not()) {
        load(url, App.appContractor.imageLoader)
    }
}