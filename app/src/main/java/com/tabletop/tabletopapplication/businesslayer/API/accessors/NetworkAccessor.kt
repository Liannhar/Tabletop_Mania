package com.tabletop.tabletopapplication.businesslayer.API.accessors

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object NetworkAccessor {
    private val retrofit = Retrofit.Builder()
        .baseUrl("http://158.160.37.110:1337/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    private val service = retrofit.create(
        Request::class.java)

    fun getService(): Request =
        service
}