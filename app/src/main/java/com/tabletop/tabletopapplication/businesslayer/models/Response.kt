package com.tabletop.tabletopapplication.businesslayer.models

import android.os.Parcelable
import com.google.gson.annotations.SerializedName

data class Response<T: Parcelable>(
    @SerializedName("data") var data: ArrayList<T> = arrayListOf()
) : java.io.Serializable