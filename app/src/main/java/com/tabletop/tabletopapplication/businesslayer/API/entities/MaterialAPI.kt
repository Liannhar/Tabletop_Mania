package com.tabletop.tabletopapplication.businesslayer.API.entities

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import com.tabletop.tabletopapplication.presentationlayer.models.Material
import kotlinx.parcelize.Parcelize

data class MaterialAPI(
    @SerializedName("id")           var id: Int = 0,
    @SerializedName("name")         var name: String = "Name",
    @SerializedName("description")  var description: String = "Description",
    @SerializedName("image")        var image: String? = null
) : EntityAPI {

    fun toMaterial() = Material(id, name, description, image)

}