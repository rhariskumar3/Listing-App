package com.haris.listingapp.di

import android.content.Context
import coil.ImageLoader
import coil.decode.SvgDecoder
import coil.size.Precision
import com.haris.listingapp.App
import com.haris.listingapp.data.local.AppDatabase
import com.haris.listingapp.data.remote.OpenWeatherService
import com.haris.listingapp.data.remote.RestCountriesService
import com.haris.listingapp.utils.ConnectionStateMonitor
import com.haris.listingapp.utils.Toast

class AppContractor(app: App) {

    /* Application Level Context */
    private val context: Context by lazy {
        app.applicationContext
    }

    /* Application Level Room Database */
    private val _database by lazy { AppDatabase.getInstance(context) }
    val database by lazy { _database.countryDao() }

    /* Application Level Countries REST Service */
    val countriesRestService by lazy { RestCountriesService.create() }

    /* Application Level Open Weather REST Service */
    val openWeatherRestService by lazy { OpenWeatherService.create() }

    /* Application Level Network Manager */
    val networkHelper by lazy {
        ConnectionStateMonitor(app)
    }

    /* Application Level Coil Image Loader */
    val imageLoader by lazy {
        ImageLoader.Builder(context)
            .componentRegistry {
                add(SvgDecoder(context))
            }
            .precision(Precision.INEXACT)
            .build()
    }

    init {
        /* Toast Init */
        Toast(app)
    }
}