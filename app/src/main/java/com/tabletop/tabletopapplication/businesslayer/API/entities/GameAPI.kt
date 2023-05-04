package com.tabletop.tabletopapplication.businesslayer.API.entities

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import com.tabletop.tabletopapplication.presentationlayer.models.Game
import kotlinx.parcelize.Parcelize

@Parcelize
data class GameAPI(
    @SerializedName("id")           override var id: Int = 0,
    @SerializedName("name")         override var name: String = "Name",
    @SerializedName("description")  override var description: String = "Description",
    @SerializedName("image")        override var image: String? = null,
    @SerializedName("materials")             var materials: ArrayList<Int> = arrayListOf()
) : Game(), EntityAPI, Parcelable
