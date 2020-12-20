package com.haris.listingapp.ui.base

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import com.google.android.material.snackbar.Snackbar
import com.haris.listingapp.App
import com.haris.listingapp.BuildConfig
import com.haris.listingapp.R
import com.haris.listingapp.databinding.MainActivityBinding
import com.haris.listingapp.di.AppContractor
import com.haris.listingapp.utils.dismissKeyboard

class MainActivity : AppCompatActivity() {

    private lateinit var binding: MainActivityBinding
    private lateinit var navController: NavController

    private val medium: AppContractor by lazy { App.appContractor }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.main_activity)

        val host: NavHostFragment = supportFragmentManager
            .findFragmentById(R.id.nav_host_fragment) as NavHostFragment? ?: return

        navController = host.navController

        navController.addOnDestinationChangedListener { _, destination, args ->
            if (BuildConfig.DEBUG) Log.i(
                "Navigation", "Navigated to ${destination.label}" + when {
                    args != null -> " with $args"
                    else -> ""
                }
            )
            currentFocus?.dismissKeyboard()
        }

        networking()
    }

    private fun networking() {
        val snack =
            Snackbar.make(binding.root, "No Internet Connection", Snackbar.LENGTH_INDEFINITE)
                .apply {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q)
                        setAction("Open Settings") { startActivity(Intent(Settings.Panel.ACTION_INTERNET_CONNECTIVITY)) }
                }
        medium.networkHelper.listener { _, isConnected ->
            when (isConnected) {
                true -> if (snack.isShown) snack.dismiss()
                false -> if (!snack.isShown) snack.show()
            }
        }
        lifecycle.addObserver(medium.networkHelper)
    }
}