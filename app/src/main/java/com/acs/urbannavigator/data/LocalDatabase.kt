package com.acs.urbannavigator.data

import androidx.room.Database
import androidx.room.RoomDatabase
import com.acs.urbannavigator.models.CountryItem
import com.acs.urbannavigator.models.CountryItemDAO

@Database(entities = [CountryItem::class], version = 1)
abstract class LocalDatabase : RoomDatabase() {
    abstract fun countryDao(): CountryItemDAO
}