package com.acs.urbannavigator

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Headers

interface ServiceInterface {
    @Headers(
        "Accept: application/izi-api-v1.8+json",
        "X-IZI-API-KEY: 3760230e-274a-4c0b-a01c-a023c5efb146"
    )
    @GET("/countries?languages=ro")
    fun getAllCountries(): Call<Country>

    @GET("/cities?languages=ro")
    fun getCountryCities(): Call<Country>
}