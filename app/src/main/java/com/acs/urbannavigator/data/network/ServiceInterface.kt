package com.acs.urbannavigator.data.network

import com.acs.urbannavigator.models.cityModel.City
import com.acs.urbannavigator.models.countryModel.Country
import com.acs.urbannavigator.models.museumModel.Museum
import com.acs.urbannavigator.models.museumDetailsModel.MuseumDetail
import com.acs.urbannavigator.models.tourModel.Tour
import com.acs.urbannavigator.models.tourListModel.TourList
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
    @GET("/countries/{countryUuid}/cities")
    fun getCountryCities(
        @Path("countryUuid") countryUuid: String,
        @Query("languages") languages: String
    ): Call<City>

    @Headers(
        "Accept: application/izi-api-v1.8+json",
        "X-IZI-API-KEY: 3760230e-274a-4c0b-a01c-a023c5efb146"
    )
    @GET("/cities/{cityUuid}/children")
    fun getMuseumsAndTours(
        @Path("cityUuid") cityUuid: String,
        @Query("languages") languages: String,
        @Query("type") type: String
    ): Call<Museum>

    @Headers(
        "Accept: application/izi-api-v1.8+json",
        "X-IZI-API-KEY: 3760230e-274a-4c0b-a01c-a023c5efb146"
    )
    @GET("/cities/{cityUuid}/children")
    fun getTours(
        @Path("cityUuid") cityUuid: String,
        @Query("languages") languages: String,
        @Query("type") type: String
    ): Call<Tour>

    @Headers(
        "Accept: application/izi-api-v1.8+json",
        "X-IZI-API-KEY: 3760230e-274a-4c0b-a01c-a023c5efb146"
    )
    @GET("/mtgobjects/{tourUuid}/children?form=full&type=tourist_attraction")
    fun getTourList(
        @Path("tourUuid") cityUuid: String,
        @Query("languages") languages: String,
    ): Call<TourList>

    @Headers(
        "Accept: application/izi-api-v1.8+json",
        "X-IZI-API-KEY: 3760230e-274a-4c0b-a01c-a023c5efb146"
    )
    @GET("/mtgobjects/{museumUuid}")
    fun getMuseumDetails(
        @Path("museumUuid") cityUuid: String,
        @Query("languages") languages: String,
    ): Call<MuseumDetail>
}