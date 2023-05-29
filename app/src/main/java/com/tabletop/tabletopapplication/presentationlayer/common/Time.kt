package com.tabletop.tabletopapplication.presentationlayer.common

class Time(
    var minutes: Int = 0,
    var seconds: Int = 0
) {

    constructor(time: Time): this(time.minutes, time.seconds)

    constructor(value: String): this(
        value.substring(0 until value.indexOf(":")).toInt(),
        value.substring(value.indexOf(":") + 1 until value.length).toInt()
    )

    override fun toString() =
        (if (minutes < 10) "0$minutes" else "$minutes") +
            ":" +
        (if (seconds < 10) "0$seconds" else "$seconds")

    fun distribute(value: Int) = Time(value / 60, value % 60)
    fun toSeconds() = minutes * 60 + seconds

    operator fun minus(value: Int?): Time {
        val result = distribute(toSeconds() - (value ?: 0))

        minutes = result.minutes
        seconds = result.seconds

        return this
    }
}