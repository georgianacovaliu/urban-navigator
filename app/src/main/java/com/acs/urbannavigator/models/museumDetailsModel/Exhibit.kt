package com.acs.urbannavigator.models.museumDetailsModel


import com.acs.urbannavigator.models.ContentProvider
import com.acs.urbannavigator.models.Image
import com.google.gson.annotations.SerializedName

data class Exhibit(
    @SerializedName("content_provider")
    val contentProvider: ContentProvider,
    @SerializedName("images")
    val images: List<Image>,
    @SerializedName("title")
    val title: String,
    @SerializedName("type")
    val type: String
)