package com.tabletop.tabletopapplication.presentationlayer.models

import com.google.gson.Gson

open class Material(
    open var id: Int = -1,
    open var name: String = "",
    open var description: String = "",
    open var image: String? = null,

    open var extras: String = ""
): java.io.Serializable {

    constructor(name: String, description: String, image: String?, extras: String):
            this(0, name, description, image, extras)

    fun <T> getExtras(classOf: Class<T>): T =
        Gson().fromJson(extras, classOf)

    fun <T> setExtras(value: T) {
        extras = Gson().toJson(value)
    }

}