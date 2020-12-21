package com.haris.listingapp.data.local

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.haris.listingapp.data.model.Currency
import com.haris.listingapp.data.model.Language
import java.lang.reflect.Type

/* Type converter class for store lists in database */
class Converters {
    @TypeConverter
    fun fromStringToList(value: String?): List<String> {
        val listType: Type = object : TypeToken<List<String?>?>() {}.type
        return Gson().fromJson(value, listType) ?: emptyList()
    }

    @TypeConverter
    fun fromListToString(list: List<String?>?) = Gson().toJson(list) ?: ""

    @TypeConverter
    fun fromStringToListCurrency(value: String?): List<Currency> {
        val listType: Type = object : TypeToken<List<Currency?>?>() {}.type
        return Gson().fromJson(value, listType) ?: emptyList()
    }

    @TypeConverter
    fun fromCurrencyListToString(list: List<Currency?>?) = Gson().toJson(list) ?: ""

    @TypeConverter
    fun fromStringToListLanguage(value: String?): List<Language> {
        val listType: Type = object : TypeToken<List<Language?>?>() {}.type
        return Gson().fromJson(value, listType) ?: emptyList()
    }

    @TypeConverter
    fun fromLanguageListToString(list: List<Language?>?) = Gson().toJson(list) ?: ""
}