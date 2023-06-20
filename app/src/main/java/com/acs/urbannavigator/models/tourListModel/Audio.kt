package com.acs.urbannavigator.models.tourListModel


import com.google.gson.annotations.SerializedName

data class Audio(
    @SerializedName("duration")
    val duration: Int,
    @SerializedName("type")
    val type: String,
    @SerializedName("uuid")
    val uuid: String
)