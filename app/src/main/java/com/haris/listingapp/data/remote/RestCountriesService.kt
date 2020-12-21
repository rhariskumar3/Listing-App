package com.haris.listingapp.data.remote

import com.google.gson.FieldNamingPolicy
import com.google.gson.GsonBuilder
import com.haris.listingapp.BuildConfig
import com.haris.listingapp.data.model.Country
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import java.util.concurrent.TimeUnit

interface RestCountriesService {

    /* Returns list of all countries */
    @GET("rest/v2/all")
    suspend fun allCountries(): Response<List<Country>>

    companion object {
        private const val BASE_URL = "https://restcountries.eu/"

        fun create(): RestCountriesService {
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
                .create(RestCountriesService::class.java)
        }
    }
}