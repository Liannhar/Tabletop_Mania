package com.tabletop.tabletopapplication.businesslayer.models

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class MaterialEntity(
    @SerializedName("id")          var id: Int = 0,
    @SerializedName("name")        var name: String = "Name",
    @SerializedName("description") var description: String = "Description",
    @SerializedName("image")       var image_url: String? = null
) : Parcelable