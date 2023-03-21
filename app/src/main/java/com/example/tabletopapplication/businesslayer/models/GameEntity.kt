package com.example.tabletopapplication.businesslayer.models

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class GameEntity(
    @SerializedName("image_url")
    val image_url: String,
   @SerializedName("name")
    val name: String,



): Serializable
