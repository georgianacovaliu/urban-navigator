package com.acs.urbannavigator.models.museumModel


import com.acs.urbannavigator.models.ContentProvider
import com.acs.urbannavigator.models.Image
import com.acs.urbannavigator.models.Location
import com.acs.urbannavigator.models.Reviews
import com.google.gson.annotations.SerializedName

data class MuseumItem(
    @SerializedName("content_provider")
    val contentProvider: ContentProvider,
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