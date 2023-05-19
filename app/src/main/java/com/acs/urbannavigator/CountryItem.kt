package com.acs.urbannavigator


import com.google.gson.annotations.SerializedName

data class CountryItem(
    @SerializedName("status")
    val status: String,
    @SerializedName("title")
    val title: String,
    @SerializedName("uuid")
    val uuid: String
)