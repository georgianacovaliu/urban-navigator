package com.acs.urbannavigator.data.database

import androidx.room.*
import com.acs.urbannavigator.models.FavoriteItem

@Dao
interface FavoriteItemDAO {
    @Query("SELECT * FROM favorite")
    fun getAll(): List<FavoriteItem>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertFavoriteItem(favoriteItem: FavoriteItem)

    @Query("DELETE FROM favorite WHERE uuid = :uuid")
    fun deleteEntryById(uuid: String)

    @Query("DELETE FROM favorite")
    fun deleteAll()
}