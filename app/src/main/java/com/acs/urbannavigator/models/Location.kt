package com.acs.urbannavigator.models


import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
@Entity(tableName = "location")
data class Location(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    @ColumnInfo(name = "latitude")
    @SerializedName("latitude")
    val latitude: Double,
    @ColumnInfo(name = "longitude")
    @SerializedName("longitude")
    val longitude: Double
)

