package com.haris.listingapp.di

import android.content.Context
import com.haris.listingapp.App
import com.haris.listingapp.data.local.AppDatabase
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

    /* Application Level Network Manager */
    val networkHelper by lazy {
        ConnectionStateMonitor(app)
    }

    init {
        /* Toast Init */
        Toast(app)
    }
}