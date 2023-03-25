package com.example.tabletopapplication.presentationlayer.models

import com.example.tabletopapplication.R
import com.example.tabletopapplication.businesslayer.models.Material.Material

data class Game(
    val title: String = "Title",
    val description: String = "Description",
    val image: Int = R.drawable.black_rectangle,
    val materials: ArrayList<Material> = arrayListOf()
) : java.io.Serializable