package com.acs.urbannavigator.models.TourList


import com.acs.urbannavigator.models.ContentProvider
import com.acs.urbannavigator.models.Location
import com.google.gson.annotations.SerializedName

data class TourListItem(
    @SerializedName("content")
    val content: List<Content>,
    @SerializedName("content_provider")
    val contentProvider: ContentProvider,
    @SerializedName("location")
    val location: Location,
    @SerializedName("parent_uuid")
    val parentUuid: String,
    @SerializedName("trigger_zones")
    val triggerZones: List<TriggerZone>,
    @SerializedName("type")
    val type: String,
    @SerializedName("uuid")
    val uuid: String
)