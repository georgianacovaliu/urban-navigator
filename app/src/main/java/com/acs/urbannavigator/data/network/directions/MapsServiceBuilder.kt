package com.acs.urbannavigator.data.network.directions

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object MapsServiceBuilder {

    var serviceBuilderInstance : MapsServiceInterface? = null

    private val client = OkHttpClient.Builder().build()

    private val retrofit = Retrofit.Builder()
        .baseUrl("https://maps.googleapis.com/maps/api/directions/")
        .addConverterFactory(GsonConverterFactory.create())
        .client(client)
        .build()


    fun<T> buildService(service: Class<T>):T{
        if (serviceBuilderInstance != null){
            return retrofit.create(service)
        }
        return retrofit.create(service)
    }

    fun getInstance(): MapsServiceInterface {
        val i = serviceBuilderInstance
        if (i != null) {
            return i
        }
        serviceBuilderInstance = retrofit.create(MapsServiceInterface::class.java)

        return serviceBuilderInstance as MapsServiceInterface
    }
}