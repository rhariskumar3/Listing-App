package com.haris.listingapp

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import com.haris.listingapp.databinding.MainActivityBinding
import com.haris.listingapp.utils.dismissKeyboard

class MainActivity : AppCompatActivity() {

    private lateinit var binding: MainActivityBinding
    private lateinit var navController: NavController

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
    }
}