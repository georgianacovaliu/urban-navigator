package com.acs.urbannavigator.models


import com.google.gson.annotations.SerializedName

data class Reviews(
    @SerializedName("rating_average")
    val ratingAverage: Double
)