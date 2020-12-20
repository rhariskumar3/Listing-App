package com.haris.listingapp.data.model

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.haris.listingapp.utils.toDenotedString
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity
data class Country(
    @PrimaryKey val name: String,
    val alpha2Code: String? = "",
    val alpha3Code: String? = "",
    val callingCodes: List<String> = emptyList(),
    val capital: String,
    val region: String,
    val subregion: String,
    val population: Long,
    val latlng: List<String> = emptyList(),
    val area: Double,
    val timezones: List<String> = emptyList(),
    val currencies: List<Currency> = emptyList(),
    val languages: List<Language> = emptyList(),
    val flag: String,
) : Parcelable {

    val currency: String
        get() = currencies.filter { it.name.isNotEmpty() }.joinToString { it.name }

    val peoples: String
        get() = population.toDenotedString()
}

@Parcelize
data class Currency(
    val code: String,
    val name: String,
    val symbol: String,
) : Parcelable

@Parcelize
data class Language(
    val iso6391: String? = "",
    val iso6392: String? = "",
    val name: String,
    val nativeName: String,
) : Parcelable