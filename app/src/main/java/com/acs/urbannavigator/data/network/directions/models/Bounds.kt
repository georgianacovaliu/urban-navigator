package com.acs.urbannavigator.data.network.directions.models


import com.google.gson.annotations.SerializedName

data class Bounds(
    @SerializedName("northeast")
    val northeast: Northeast,
    @SerializedName("southwest")
    val southwest: Southwest
)