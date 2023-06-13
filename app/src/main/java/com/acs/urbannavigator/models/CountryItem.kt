package com.acs.urbannavigator.models


import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity
data class CountryItem(
    @ColumnInfo(name = "title")
    @SerializedName("title")
    val title: String,

    @PrimaryKey
    @SerializedName("uuid")
    val uuid: String,

    @ColumnInfo(name = "country_code")
    @SerializedName("country_code")
    val countryCode: String
)