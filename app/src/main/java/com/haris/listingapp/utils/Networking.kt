package com.haris.listingapp.utils

import android.content.Context
import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkCapabilities
import android.net.NetworkRequest
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.OnLifecycleEvent

enum class ApiStatus(val code: Int, val message: String) {
    SUCCESS(200, "Success"),
    UNAUTHORIZED_ACCESS(401, "OOPS!!! Your Access Key Expired"),
    SERVER_NOT_FOUND(404, "OOPS!!! Server Not Found, Please contact admin"),
    TIMEOUT_ERROR(408, "OOPS!!! Please Try again Later"),
    INTERNAL_SERVER_ERROR(500, "OOPS!!! Something went wrong"),
    SERVICE_UNAVAILABLE(503, "We are busy updating the Website for you and will be back shortly"),
    CONNECT_ERROR(0, "OOPS!!! Please check your internet connection")
}

class ConnectionStateMonitor(private val context: Context) : ConnectivityManager.NetworkCallback(),
    LifecycleObserver {

    private val connectivityManager: ConnectivityManager by lazy {
        context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    }

    private val networkRequest = NetworkRequest.Builder()
        .addCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
        .addCapability(NetworkCapabilities.NET_CAPABILITY_VALIDATED)
        .build()

    @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
    fun enable() = connectivityManager.registerNetworkCallback(networkRequest, this)

    @OnLifecycleEvent(Lifecycle.Event.ON_PAUSE)
    fun disable() = connectivityManager.unregisterNetworkCallback(this)

    override fun onAvailable(network: Network) {
        super.onAvailable(network)
        hasNetworkConnection = true
        listener?.callback(network, true)
    }

    override fun onUnavailable() {
        super.onUnavailable()
        hasNetworkConnection = false
        listener?.callback(null, false)
    }

    override fun onLost(network: Network) {
        super.onLost(network)
        hasNetworkConnection = false
        listener?.callback(network, false)
    }

    override fun onCapabilitiesChanged(network: Network, networkCapabilities: NetworkCapabilities) {
        super.onCapabilitiesChanged(network, networkCapabilities)
        networkCapabilities.also {
            when {
                it.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET) &&
                        it.hasCapability(NetworkCapabilities.NET_CAPABILITY_VALIDATED) -> {
                    hasNetworkConnection = true
                    listener?.callback(network, true)
                }
                else -> {
                    hasNetworkConnection = false
                    listener?.callback(network, false)
                }
            }
        }
    }

    var hasNetworkConnection: Boolean = false
        private set

    private var listener: Listener? = null

    fun interface Listener {
        fun callback(network: Network?, isConnected: Boolean)
    }

    fun listener(listener: Listener) {
        this.listener = listener
    }
}