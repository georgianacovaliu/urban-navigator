package com.acs.urbannavigator.models.tourListModel


import com.google.gson.annotations.SerializedName

data class TriggerZone(
    @SerializedName("circle_altitude")
    val circleAltitude: Double,
    @SerializedName("circle_latitude")
    val circleLatitude: Double,
    @SerializedName("circle_longitude")
    val circleLongitude: Double,
    @SerializedName("circle_radius")
    val circleRadius: Double,
    @SerializedName("type")
    val type: String
)