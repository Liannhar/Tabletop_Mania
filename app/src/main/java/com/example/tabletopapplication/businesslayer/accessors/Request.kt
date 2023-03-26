package com.example.tabletopapplication.businesslayer.accessors

import com.example.tabletopapplication.businesslayer.models.GameEntity
import com.example.tabletopapplication.businesslayer.models.MaterialEntity
import com.example.tabletopapplication.businesslayer.models.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface Request {

    @GET("get/material/id/{material_id}")
    suspend fun getMaterial(
        @Path("material_id") id: Int
    ): Response<MaterialEntity>

    @GET("get/materials/start/{start_id}/end/{end_id}")
    suspend fun getRangeMaterials(
        @Path("start_id") startId: Int,
        @Path("end_id") endId: Int
    ): Response<MaterialEntity>

    @GET("get/game/id/{game_id}")
    suspend fun getGame(
        @Path("game_id") id: Int
    ): Response<GameEntity>
}