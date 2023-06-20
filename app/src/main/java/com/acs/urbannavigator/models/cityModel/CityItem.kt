package com.acs.urbannavigator.models.cityModel
import com.acs.urbannavigator.models.Image
import com.google.gson.annotations.SerializedName

data class CityItem(
    @SerializedName("images")
    val images: List<Image>,
    @SerializedName("status")
    val status: String,
    @SerializedName("title")
    val title: String,
    @SerializedName("uuid")
    val uuid: String
)
