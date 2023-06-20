package com.acs.urbannavigator.models.museumDetailsModel


import com.acs.urbannavigator.models.ContentProvider
import com.google.gson.annotations.SerializedName

data class MuseumDetailItem(
    @SerializedName("content")
    val content: List<MuseumContent>,
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