package com.acs.urbannavigator.models.MuseumChildren


import com.acs.urbannavigator.models.ContentProvider
import com.acs.urbannavigator.models.Image
import com.google.gson.annotations.SerializedName

data class MuseumChildItem(
    @SerializedName("children_count")
    val childrenCount: Int,
    @SerializedName("content_provider")
    val contentProvider: ContentProvider,
    @SerializedName("images")
    val images: List<Image>,
    @SerializedName("summary")
    val summary: String,
    @SerializedName("title")
    val title: String,
    @SerializedName("type")
    val type: String,
    @SerializedName("uuid")
    val uuid: String
)