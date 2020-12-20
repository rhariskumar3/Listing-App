package com.haris.listingapp.data.model

data class Country(
    val name: String,
    val alpha2Code: String,
    val alpha3Code: String,
    val callingCodes: List<String> = emptyList(),
    val capital: String,
    val region: String,
    val subregion: String,
    val population: Long,
    val latlng: List<Double> = emptyList(),
    val area: Double,
    val timezones: List<String> = emptyList(),
    val currencies: List<Currency> = emptyList(),
    val languages: List<Language> = emptyList(),
    val flag: String,
)

data class Currency(
    val code: String,
    val name: String,
    val symbol: String,
)

data class Language(
    val iso6391: String,
    val iso6392: String,
    val name: String,
    val nativeName: String,
)