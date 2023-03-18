package com.example.tabletopapplication.common

import com.example.tabletopapplication.R

data class Game(
    val title: String = "Title",
    val description: String = "Description",
    val image: Int = R.drawable.black_rectangle,
    val materials: ArrayList<Material> = arrayListOf()
) : java.io.Serializable