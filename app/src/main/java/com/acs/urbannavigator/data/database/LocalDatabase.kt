package com.acs.urbannavigator.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.acs.urbannavigator.models.countryModel.CountryItem

@Database(entities = [CountryItem::class], version = 1)
abstract class LocalDatabase : RoomDatabase() {
    abstract fun countryDao(): CountryItemDAO
}