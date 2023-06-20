package com.acs.urbannavigator.models.tourModel


import com.acs.urbannavigator.models.ContentProvider
import com.acs.urbannavigator.models.Image
import com.acs.urbannavigator.models.Location
import com.acs.urbannavigator.models.Reviews
import com.google.gson.annotations.SerializedName

data class TourItem(
    @SerializedName("category")
    val category: String,
    @SerializedName("content_provider")
    val contentProvider: ContentProvider,
    @SerializedName("distance")
    val distance: Int,
    @SerializedName("duration")
    val duration: Int,
    @SerializedName("images")
    val images: List<Image>,
    @SerializedName("location")
    val location: Location,
    @SerializedName("reviews")
    val reviews: Reviews,
    @SerializedName("summary")
    val summary: String,
    @SerializedName("title")
    val title: String,
    @SerializedName("uuid")
    val uuid: String
)