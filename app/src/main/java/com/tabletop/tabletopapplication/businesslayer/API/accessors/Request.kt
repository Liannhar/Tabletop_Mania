package com.tabletop.tabletopapplication.businesslayer.API.accessors

import com.tabletop.tabletopapplication.businesslayer.API.entities.GameAPI
import com.tabletop.tabletopapplication.businesslayer.API.entities.MaterialAPI
import com.tabletop.tabletopapplication.businesslayer.API.entities.Response
import retrofit2.http.GET
import retrofit2.http.Path


interface Request {

    @GET("get/material/id/{material_id}")
    suspend fun getMaterial(
        @Path("material_id") id: Int
    ): Response<MaterialAPI>

    @GET("get/materials/start/{start_id}/end/{end_id}")
    suspend fun getRangeMaterials(
        @Path("start_id") startId: Int,
        @Path("end_id") endId: Int
    ): Response<MaterialAPI>

    @GET("get/game/id/{game_id}")
    suspend fun getGame(
        @Path("game_id") id: Int
    ): Response<GameAPI>
}