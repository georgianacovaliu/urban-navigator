package com.acs.urbannavigator.models.MuseumDetails


import com.acs.urbannavigator.models.ContentProvider
import com.acs.urbannavigator.models.Image
import com.google.gson.annotations.SerializedName

data class Children(
    @SerializedName("content_provider")
    val contentProvider: ContentProvider,
    @SerializedName("images")
    val images: List<Image>,
    @SerializedName("title")
    val title: String,
    @SerializedName("type")
    val type: String,
)