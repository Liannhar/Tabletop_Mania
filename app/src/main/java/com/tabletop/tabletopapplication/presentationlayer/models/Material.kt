package com.tabletop.tabletopapplication.presentationlayer.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

open class Material(
    open var id: Int = -1,
    open var name: String = "",
    open var description: String = "",
    open var image: String? = null
): java.io.Serializable