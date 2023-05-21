package com.tabletop.tabletopapplication.presentationlayer.models

import android.os.Parcelable
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.tabletop.tabletopapplication.businesslayer.API.entities.MaterialAPI
import kotlinx.parcelize.Parcelize

open class Material(
    open var id: Int = -1,
    open var name: String = "",
    open var description: String = "",
    open var image: String? = null,

    open var extras: String = ""
): java.io.Serializable {

    constructor(name: String, description: String, image: String?, extras: String):
            this(0, name, description, image, extras)

}