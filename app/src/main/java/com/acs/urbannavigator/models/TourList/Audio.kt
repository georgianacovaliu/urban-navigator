package com.acs.urbannavigator.models.TourList


import com.google.gson.annotations.SerializedName

data class Audio(
    @SerializedName("duration")
    val duration: Int,
    @SerializedName("hash")
    val hash: String,
    @SerializedName("order")
    val order: Int,
    @SerializedName("size")
    val size: Int,
    @SerializedName("type")
    val type: String,
    @SerializedName("uuid")
    val uuid: String
)