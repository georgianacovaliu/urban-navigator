package com.acs.urbannavigator.models

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query

@Dao
interface CountryItemDAO {
    @Query("SELECT * FROM countryitem")
    fun getAll(): List<CountryItem>

    @Insert
    fun insertAll(vararg countries: CountryItem)

    @Delete
    fun delete(user: CountryItem)
}