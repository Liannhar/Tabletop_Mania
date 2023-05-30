package com.tabletop.tabletopapplication.presentationlayer.models


data class History(
    var date: String = "-",
    var winner: String = "-",
    var score: String = "-"
) : java.io.Serializable