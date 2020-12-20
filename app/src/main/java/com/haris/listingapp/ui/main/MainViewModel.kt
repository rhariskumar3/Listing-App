package com.haris.listingapp.ui.main

import androidx.annotation.WorkerThread
import androidx.lifecycle.*
import com.haris.listingapp.data.model.Country
import com.haris.listingapp.ui.base.BaseViewModel
import kotlinx.coroutines.launch

class MainViewModel : BaseViewModel(), LifecycleObserver {

    val allCountries = medium.database.getAll()

    @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
    private fun data() {
        viewModelScope.launch {
            apiLaunch({ it.allCountries() }, progress = allCountries.value.orEmpty().isEmpty(), {
                addDataToDatabase(it.orEmpty())
            })
        }
    }

    @WorkerThread
    private fun addDataToDatabase(list: List<Country>) {
        viewModelScope.launch {
            medium.database.insertAll(list)
        }
    }
}