package com.acs.urbannavigator.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.acs.urbannavigator.models.FavoriteItem
import com.acs.urbannavigator.models.Location
import com.acs.urbannavigator.models.countryModel.CountryItem

@Database(entities = [CountryItem::class, Location::class, FavoriteItem::class], version = 4)
abstract class LocalDatabase : RoomDatabase() {
    abstract fun countryDao(): CountryItemDAO
    abstract fun locationDao(): LocationDAO
    abstract fun favoriteDao(): FavoriteItemDAO
}