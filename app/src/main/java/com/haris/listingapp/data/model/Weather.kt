package com.haris.listingapp.data.model

data class Weather(
    val weather: List<WeatherElement> = emptyList(),
    val main: Main,
    val dt: Long,
    val name: String,
)

data class Main(
    val temp: Double,
    val feelsLike: Double,
    val tempMin: Double,
    val tempMax: Double,
    val pressure: Double,
    val humidity: Double,
)

data class WeatherElement(
    val id: Long,
    val main: String,
    val description: String,
    val icon: String,
) {
    val image: String
        get() = "http://openweathermap.org/img/wn/$icon@4x.png"
}