package com.haris.listingapp.utils

import android.graphics.drawable.PictureDrawable
import android.net.Uri
import android.view.View
import android.widget.ImageView
import androidx.core.view.isGone
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.haris.listingapp.utils.glide.SvgSoftwareLayerSetter

@BindingAdapter("isGone")
fun View.isGone(value: Boolean = false) {
    isGone = value
}

@BindingAdapter("imageSvg")
fun ImageView.loadSVG(url: String?) {
    if (url.isNullOrEmpty().not()) {
        Glide.with(context)
            .`as`(PictureDrawable::class.java)
            .load(Uri.parse(url.orEmpty().trim()))
            .diskCacheStrategy(DiskCacheStrategy.DATA)
            .transition(DrawableTransitionOptions.withCrossFade())
            .listener(SvgSoftwareLayerSetter())
            .into(this)
    }
}