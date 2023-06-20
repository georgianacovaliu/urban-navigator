package com.acs.urbannavigator.models.tourListModel


import com.acs.urbannavigator.models.Image
import com.google.gson.annotations.SerializedName

data class Content(
    @SerializedName("audio")
    val audio: List<Audio>,
    @SerializedName("desc")
    val desc: String,
    @SerializedName("images")
    val images: List<Image>,
    @SerializedName("summary")
    val summary: String,
    @SerializedName("title")
    val title: String
)