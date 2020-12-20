package com.haris.listingapp.data.remote

import com.google.gson.FieldNamingPolicy
import com.google.gson.GsonBuilder
import com.haris.listingapp.BuildConfig
import com.haris.listingapp.data.model.Weather
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query
import java.util.concurrent.TimeUnit

interface OpenWeatherService {

    @GET("data/2.5/weather")
    suspend fun currentWeather(
        @Query("lat") lat: Double,
        @Query("lon") lon: Double,
        @Query("units") units: String = "metric",
        @Query("appid") key: String = API_KEY,
    ): Weather

    companion object {
        private const val BASE_URL = "https://api.openweathermap.org/"

        private const val API_KEY = "8118ed6ee68db2debfaaa5a44c832918"

        fun create(): OpenWeatherService {
            val loggingInterceptor = HttpLoggingInterceptor().apply {
                level = when (BuildConfig.DEBUG) {
                    true -> HttpLoggingInterceptor.Level.BODY
                    false -> HttpLoggingInterceptor.Level.NONE
                }
            }

            val client = OkHttpClient()
                .newBuilder()
                .addInterceptor(loggingInterceptor)
                .connectTimeout(120, TimeUnit.SECONDS)
                .readTimeout(120, TimeUnit.SECONDS)
                .retryOnConnectionFailure(true)
                .build()

            val gson = GsonBuilder()
                .setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
                .create()

            return Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build()
                .create(OpenWeatherService::class.java)
        }
    }
}