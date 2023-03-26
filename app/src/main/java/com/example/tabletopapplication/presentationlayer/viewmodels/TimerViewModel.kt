package com.example.tabletopapplication.presentationlayer.models

import android.os.CountDownTimer
import android.widget.EditText
import android.widget.TextView
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.tabletopapplication.R
import com.example.tabletopapplication.businesslayer.managers.GameManager

class TimerViewModel : ViewModel() {

    fun timer(minuetsStr: String, secondsStr: String, textview: TextView): CountDownTimer {
        val minuets: Long = try {
            minuetsStr.toLong()
        } catch (error: Throwable) {
            0
        }
        val seconds: Long = try {
            secondsStr.toLong()
        } catch (error: Throwable) {
            0
        }
        var time: Long = (minuets * 60 + seconds) * 1000
        var minuetsText=minuets.toString()
        var secondsText=seconds.toString()
        if (seconds < 10) {
            secondsText="0$seconds"}
        if (minuets < 10) {
            minuetsText="0$minuets"}
        val text = "$minuetsText:$secondsText"
        textview.text = text
        val timer = object : CountDownTimer(time, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                time -= 1000
                val min = (millisUntilFinished + 100) / 60000
                val sec = ((millisUntilFinished + 100) - min * 60000) / 1000

                val sec_str: String = if (sec < 10) {
                    "0$sec"
                } else {
                    sec.toString()
                }

                val min_str: String = if (min < 10) {
                    "0$min"
                } else {
                    min.toString()
                }
                textview.text = "$min_str:$sec_str"
            }

            override fun onFinish() {
                textview.text = "00:00"
            }
        }
        return timer
    }
}



