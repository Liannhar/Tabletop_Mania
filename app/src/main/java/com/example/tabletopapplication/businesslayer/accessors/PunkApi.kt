package com.example.tabletopapplication.businesslayer.accessors

import com.example.tabletopapplication.businesslayer.models.GameEntity
import retrofit2.http.GET

interface PunkApi {

    @GET("v2/beers")
    suspend fun getGameList(): List<GameEntity>
}