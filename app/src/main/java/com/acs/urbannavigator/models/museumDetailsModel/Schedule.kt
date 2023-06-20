package com.acs.urbannavigator.models.museumDetailsModel


import com.google.gson.annotations.SerializedName

data class Schedule(
    @SerializedName("fri")
    val fri: List<String>,
    @SerializedName("mon")
    val mon: Any,
    @SerializedName("sat")
    val sat: List<String>,
    @SerializedName("sun")
    val sun: List<String>,
    @SerializedName("thu")
    val thu: List<String>,
    @SerializedName("tue")
    val tue: Any,
    @SerializedName("wed")
    val wed: List<String>
)