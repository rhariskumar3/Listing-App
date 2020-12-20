package com.haris.listingapp.ui.main

import android.location.Location
import androidx.annotation.WorkerThread
import androidx.databinding.ObservableBoolean
import androidx.databinding.ObservableField
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.OnLifecycleEvent
import androidx.lifecycle.viewModelScope
import com.haris.listingapp.data.model.Country
import com.haris.listingapp.ui.base.BaseViewModel
import kotlinx.coroutines.launch

class MainViewModel : BaseViewModel(), LifecycleObserver {

    val allCountries = medium.database.getAll()

    @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
    private fun countryData() {
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

    val weatherDataAvailable = ObservableBoolean(false)
    fun initWeatherData(location: Location) {
        when {
            location.latitude == 0.0 -> weatherDataAvailable.set(false)
            location.longitude == 0.0 -> weatherDataAvailable.set(false)
            else -> weatherData(location.latitude, location.longitude)
        }
    }

    val locationName = ObservableField("")
    val weatherName = ObservableField("")
    val currentTemperature = ObservableField("")
    val weatherIcon = ObservableField("")
    private fun weatherData(latitude: Double, longitude: Double) {
        viewModelScope.launch {
            try {
                medium.openWeatherRestService.currentWeather(latitude, longitude).apply {
                    locationName.set(name)
                    currentTemperature.set("${main.temp}°C")
                    weather.firstOrNull()?.let {
                        weatherName.set(it.main)
                        weatherIcon.set(it.image)
                        weatherDataAvailable.set(true)
                    }
                }
            } catch (e: Exception) {
                weatherDataAvailable.set(false)
            }
        }
    }
}