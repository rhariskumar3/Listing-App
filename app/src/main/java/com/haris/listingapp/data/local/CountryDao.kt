package com.haris.listingapp.data.local

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.haris.listingapp.data.model.Country
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.distinctUntilChanged

@Dao
interface CountryDao {
    @Query("SELECT * FROM country ORDER BY name")
    fun getAll(): LiveData<List<Country>>

    @Query("SELECT * FROM country WHERE name LIKE :country ORDER BY name")
    fun getByName(country: String): Flow<List<Country>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(countries: List<Country>)

    fun getDistinctCountriesByName(name: String): Flow<List<Country>> =
        getByName(name).distinctUntilChanged()
}