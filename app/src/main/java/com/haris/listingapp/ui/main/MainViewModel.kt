package com.haris.listingapp.ui.main

import androidx.lifecycle.*
import com.haris.listingapp.data.model.Country
import com.haris.listingapp.data.remote.RestCountriesService
import kotlinx.coroutines.launch
import java.util.*

class MainViewModel : ViewModel(), LifecycleObserver {

    private var localCountries = listOf<Country>()

    private val _countries: MutableLiveData<List<Country>> = MutableLiveData(arrayListOf())
    val countries: LiveData<List<Country>> get() = _countries

    @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
    fun data() {
        viewModelScope.launch {
            val countries = RestCountriesService.create().allCountries()
            _countries.postValue(countries)
            localCountries = countries
        }
    }

    fun search(term: String = "") {
        _countries.postValue(localCountries.filter {
            it.name.toLowerCase(Locale.getDefault()).contains(term.toLowerCase(
                Locale.getDefault()))
        }.sortedBy { it.name })
    }
}