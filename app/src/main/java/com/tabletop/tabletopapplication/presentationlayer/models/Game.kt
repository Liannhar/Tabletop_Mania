package com.tabletop.tabletopapplication.presentationlayer.models

import android.os.Parcel
import android.os.Parcelable
import kotlinx.parcelize.Parcelize

open class Game(
    open var id: Int = -1,
    open var name: String = "",
    open var description: String = "",
    open var image: String? = null,
): java.io.Serializable {

    constructor(name: String, description: String, image: String?): this(0, name, description, image)

}