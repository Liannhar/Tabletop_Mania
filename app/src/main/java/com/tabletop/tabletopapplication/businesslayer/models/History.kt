package com.tabletop.tabletopapplication.businesslayer.models

import com.google.gson.annotations.SerializedName

class History: java.io.Serializable {
    @SerializedName("date")
    var date = ""
    fun Date() = date

    @SerializedName("winner")
    var winner = ""
    fun Winner() = winner

    @SerializedName("score")
    var score = ""
    fun Score() = score
}