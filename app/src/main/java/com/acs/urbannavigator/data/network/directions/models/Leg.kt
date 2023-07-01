package com.acs.urbannavigator.data.network.directions.models


import com.google.gson.annotations.SerializedName

data class Leg(
    @SerializedName("steps")
    val steps: List<Step>
)