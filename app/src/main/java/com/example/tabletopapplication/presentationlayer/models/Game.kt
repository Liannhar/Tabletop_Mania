package com.example.tabletopapplication.presentationlayer.models

import com.example.tabletopapplication.R

data class Game(
    val title: String = "Title",
    val description: String = "Description",
    val image: Int = R.drawable.black_rectangle,
    val materials: ArrayList<Model> = arrayListOf()
) : java.io.Serializable