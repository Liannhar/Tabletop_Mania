package com.example.tabletopapplication.businesslayer.models

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class GameEntity(
    @SerializedName("id")          var id: Int,
    @SerializedName("name")        var name: String,
    @SerializedName("description") var description: String,
    @SerializedName("image")       var image: String?,
    @SerializedName("materials")   var materials: ArrayList<Int>
) : Parcelable
