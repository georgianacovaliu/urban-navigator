package com.acs.urbannavigator.data

import com.acs.urbannavigator.models.City
import com.acs.urbannavigator.models.Country
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path
import retrofit2.http.Query

interface ServiceInterface {
    @Headers(
        "Accept: application/izi-api-v1.8+json",
        "X-IZI-API-KEY: 3760230e-274a-4c0b-a01c-a023c5efb146"
    )
    @GET("/countries")
    fun getAllCountries(@Query("languages") languages: String): Call<Country>

    @Headers(
        "Accept: application/izi-api-v1.8+json",
        "X-IZI-API-KEY: 3760230e-274a-4c0b-a01c-a023c5efb146"
    )
    @GET("/countries/{cityUuid}/cities?languages=ro")
    fun getCountryCities(
        @Path("cityUuid") cityUuid: String,
        @Query("languages") languages: String
    ): Call<City>
}