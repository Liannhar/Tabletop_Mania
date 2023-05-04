package com.tabletop.tabletopapplication.businesslayer.API.entities

import android.os.Parcelable
import com.google.gson.annotations.SerializedName

data class Response<T: EntityAPI>(
    @SerializedName("data") var data: ArrayList<T> = arrayListOf()
) : java.io.Serializable