package com.acs.urbannavigator.models


import com.google.gson.annotations.SerializedName

data class CountryItem(
    @SerializedName("status")
    val status: String,
    @SerializedName("title")
    val title: String,
    @SerializedName("uuid")
    val uuid: String,
    @SerializedName("country_code")
    val countryCode: String
)