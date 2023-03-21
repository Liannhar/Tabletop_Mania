package com.example.tabletopapplication.businesslayer.accessors

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object NetworkAccessor {
    private val retrofit = Retrofit.Builder()
        .baseUrl("http://127.0.0.1:8000/")
        .addConverterFactory(GsonConverterFactory.create())
        .build();

    private val service = retrofit.create(Request::class.java)

    fun getService(): Request = service
}