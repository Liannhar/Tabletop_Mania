package com.tabletop.tabletopapplication.businesslayer.accessors

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object NetworkAccessor {
    private val retrofit = Retrofit.Builder()
        .baseUrl("https://tabletop-mania-api.onrender.com/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    private val service = com.tabletop.tabletopapplication.businesslayer.accessors.NetworkAccessor.retrofit.create(
        com.tabletop.tabletopapplication.businesslayer.accessors.Request::class.java)

    fun getService(): com.tabletop.tabletopapplication.businesslayer.accessors.Request =
        com.tabletop.tabletopapplication.businesslayer.accessors.NetworkAccessor.service
}