package com.acs.urbannavigator.data.network

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ServiceBuilder {

    var serviceBuilderInstance : ServiceInterface? = null

    private val client = OkHttpClient.Builder().build()

    private val retrofit = Retrofit.Builder()
        .baseUrl("https://api.izi.travel")
        .addConverterFactory(GsonConverterFactory.create())
        .client(client)
        .build()


    fun<T> buildService(service: Class<T>):T{
        if (serviceBuilderInstance != null){
            return retrofit.create(service)
        }
        return retrofit.create(service)
    }

    fun getInstance(): ServiceInterface {
        val i = serviceBuilderInstance
        if (i != null) {
            return i
        }
        serviceBuilderInstance = retrofit.create(ServiceInterface::class.java)

        return serviceBuilderInstance as ServiceInterface
    }
}