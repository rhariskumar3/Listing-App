package com.haris.listingapp

import android.app.Application
import com.haris.listingapp.di.AppContractor

class App : Application() {

    override fun onCreate() {
        super.onCreate()
        contractor = AppContractor(this)
    }

    companion object {
        private lateinit var contractor: AppContractor

        /* Global medium for reach Globally initiated components like toast, logger .. */
        val appContractor by lazy {
            contractor
        }
    }
}