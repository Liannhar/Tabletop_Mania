package com.example.tabletopapplication.businesslayer.models

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class GameEntity(
    @SerializedName("id")          var id: Int = 0,
    @SerializedName("name")        var name: String = "Name",
    @SerializedName("description") var description: String = "Description",
    @SerializedName("image")       var image: String? = null,
    @SerializedName("materials")   var materials: ArrayList<Int> = arrayListOf()
) : Parcelable
