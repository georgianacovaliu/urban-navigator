package com.acs.urbannavigator.models.museumDetailsModel


import com.acs.urbannavigator.models.Image
import com.google.gson.annotations.SerializedName

data class MuseumContent(
    @SerializedName("children")
    val exhibits: List<Exhibit>,
    @SerializedName("desc")
    val desc: String,
    @SerializedName("images")
    val images: List<Image>,
    @SerializedName("title")
    val title: String
)