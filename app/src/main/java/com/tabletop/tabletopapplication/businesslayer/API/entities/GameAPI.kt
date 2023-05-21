package com.tabletop.tabletopapplication.businesslayer.API.entities

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import com.tabletop.tabletopapplication.businesslayer.ROOM.daos.GameMaterialDao
import com.tabletop.tabletopapplication.presentationlayer.models.Game
import kotlinx.parcelize.Parcelize

data class GameAPI(
    @SerializedName("id")           var id: Int = 0,
    @SerializedName("name")         var name: String = "Name",
    @SerializedName("description")  var description: String = "Description",
    @SerializedName("image")        var image: String? = null,
    @SerializedName("materials")    var materials: ArrayList<Int> = arrayListOf()
) : EntityAPI {

    fun toGame() = Game(id, name, description, image)

}
