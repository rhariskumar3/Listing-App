package com.haris.listingapp.ui.splash

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.flow

class SplashViewModel : ViewModel() {

    val liveSplashState = flow {
        delay(3_000)
        emit(Unit)
    }.asLiveData()
}