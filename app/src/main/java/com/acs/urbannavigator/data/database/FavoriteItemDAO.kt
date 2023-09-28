package com.acs.urbannavigator.data.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.acs.urbannavigator.models.museumModel.MuseumItem

@Dao
interface MuseumItemDAO {
    @Query("SELECT * FROM favorite")
    fun getAll(): List<MuseumItem>

    @Insert
    fun insertAll(locations: List<MuseumItem>)

    @Delete
    fun delete(id: MuseumItem)

    @Query("DELETE FROM favorite")
    fun deleteAll()
}