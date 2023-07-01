package com.acs.urbannavigator.data.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.acs.urbannavigator.models.Location

@Dao
interface LocationDAO {
    @Query("SELECT * FROM location")
    fun getAll(): List<Location>

    @Insert
    fun insertAll(locations: List<Location>)

    @Delete
    fun delete(id: Location)

    @Query("DELETE FROM location")
    fun deleteAll()
}