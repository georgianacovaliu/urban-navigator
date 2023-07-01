package com.acs.urbannavigator.data.network.directions.models


import com.google.gson.annotations.SerializedName

data class OverviewPolyline(
    @SerializedName("points")
    val points: String
)