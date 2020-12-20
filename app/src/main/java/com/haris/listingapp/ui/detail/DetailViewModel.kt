package com.haris.listingapp.ui.detail

import android.view.View
import androidx.navigation.findNavController
import com.haris.listingapp.ui.base.BaseViewModel

class DetailViewModel : BaseViewModel() {

    fun back(view: View) {
        view.findNavController().popBackStack()
    }
}