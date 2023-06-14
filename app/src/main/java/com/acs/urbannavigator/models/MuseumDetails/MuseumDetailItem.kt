package com.acs.urbannavigator.models.MuseumDetails


import com.acs.urbannavigator.models.ContentProvider
import com.google.gson.annotations.SerializedName

data class MuseumDetailItem(
    @SerializedName("content")
    val content: List<Content>,
    @SerializedName("content_provider")
    val contentProvider: ContentProvider,
    @SerializedName("schedule")
    val schedule: Schedule,
    @SerializedName("size")
    val size: Int,
    @SerializedName("status")
    val status: String,
    @SerializedName("uuid")
    val uuid: String
)