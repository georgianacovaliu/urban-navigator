package com.acs.urbannavigator.models


import com.google.gson.annotations.SerializedName

data class Image(
    @SerializedName("type")
    val type: String,
    @SerializedName("uuid")
    val uuid: String
)