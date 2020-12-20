package com.haris.listingapp.ui.base

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.haris.listingapp.App
import com.haris.listingapp.BuildConfig
import com.haris.listingapp.data.remote.RestCountriesService
import com.haris.listingapp.di.AppContractor
import com.haris.listingapp.utils.ApiStatus.*
import com.haris.listingapp.utils.Toast
import kotlinx.coroutines.launch
import retrofit2.Response
import java.net.ConnectException
import java.net.SocketTimeoutException
import java.net.UnknownHostException

open class BaseViewModel : ViewModel() {

    val medium: AppContractor by lazy { App.appContractor }

    private val _progressState = MutableLiveData(false)
    val progressState: LiveData<Boolean> get() = _progressState

    fun <T> apiLaunch(
        request: suspend (RestCountriesService) -> Response<T>,
        progress: Boolean = true,
        response: (T?) -> Unit = ::println,
    ) = viewModelScope.launch {
        // Network Check
        if (medium.networkHelper.hasNetworkConnection.not()) return@launch
        // Original Process
        if (progress) _progressState.postValue(true)
        try {
            with(request(medium.countriesRestService)) {
                body()?.let {
                    response(it)
                }
                errorBody()?.let {
                    when (code()) {
                        INTERNAL_SERVER_ERROR.code -> Toast.error(INTERNAL_SERVER_ERROR.message)
                        SERVER_NOT_FOUND.code -> Toast.error(SERVER_NOT_FOUND.message)
                        SERVICE_UNAVAILABLE.code -> Toast.error(SERVICE_UNAVAILABLE.message)
                        TIMEOUT_ERROR.code -> Toast.error(TIMEOUT_ERROR.message)
                        UNAUTHORIZED_ACCESS.code -> Toast.error(UNAUTHORIZED_ACCESS.message)
                    }
                    response(null)
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
            when (e) {
                is SocketTimeoutException -> Toast.error(TIMEOUT_ERROR.message)
                is UnknownHostException -> Toast.error(INTERNAL_SERVER_ERROR.message + " Please contact admin...")
                is ConnectException -> Toast.error(CONNECT_ERROR.message)
                else -> if (BuildConfig.DEBUG) Toast.error(e.message.toString() + " by Developer")
            }
        } finally {
            if (progress) _progressState.postValue(false)
        }
    }
}