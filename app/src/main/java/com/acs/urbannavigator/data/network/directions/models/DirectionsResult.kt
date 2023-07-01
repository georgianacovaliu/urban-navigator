package com.acs.urbannavigator.data.network.directions.models


import com.google.gson.annotations.SerializedName

data class DirectionsResult(
    @SerializedName("routes")
    val routes: List<Route>,
)