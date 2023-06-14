package com.acs.urbannavigator.models.MuseumDetails


import com.acs.urbannavigator.models.Image
import com.google.gson.annotations.SerializedName

data class Content(
    @SerializedName("children")
    val children: List<Children>,
    @SerializedName("desc")
    val desc: String,
    @SerializedName("images")
    val images: List<Image>,
    @SerializedName("title")
    val title: String
)