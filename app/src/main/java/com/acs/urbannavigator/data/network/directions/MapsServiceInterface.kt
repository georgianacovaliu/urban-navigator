package com.acs.urbannavigator.data.network.directions

import com.acs.urbannavigator.data.network.directions.models.DirectionsResult
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface MapsServiceInterface {
    @GET("json?mode=walking&key=AIzaSyBCitz0sxb73WYPLFIXX2QcftHAj9zt-M8")
    fun getRez(
        @Query("origin") origin: String,
        @Query("destination") destination: String,
        @Query("waypoints") waypoints: String
    ): Call<DirectionsResult>
}