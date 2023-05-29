package com.example.hourglass.activities

import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.os.Bundle
import android.os.CountDownTimer
import android.util.Log
import android.widget.Button
import android.widget.EdgeEffect
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.example.hourglass.R


class HourglassActivity : AppCompatActivity() {
    lateinit var sensManager: SensorManager
    private lateinit var timer: CountDownTimer
    private fun startHourglassTimer(msec: Long) {
        val tvTimer1 = findViewById<TextView>(R.id.hourglass_cv_1_text)
        val tvTimer3 = findViewById<TextView>(R.id.hourglass_cv_3_text)
        val img = findViewById<ImageView>(R.id.hourglass_pic)


        timer = object : CountDownTimer(msec, refreshFunction) {
            override fun onTick(p0: Long) {
                tvTimer1.text = (p0 / 1000).toString()
            }

            override fun onFinish() {
                tvTimer3.text = "Time is up!"
                Glide.with(applicationContext).load(R.drawable.hourglass1).into(img)
            }
        }.start()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        var FirstTurnOverFlag = false
        var UpsideDownTimerInProgressFlag = false
        var NormalTimerInProgressFlag = false
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_hourglass)
        var timerInterval: Long = 0
        val tvTimer1 = findViewById<TextView>(R.id.hourglass_cv_1_text)
        tvTimer1.text = (timerInterval / 1000).toString()
        val tvTimer3 = findViewById<TextView>(R.id.hourglass_cv_3_text)
        val img = findViewById<ImageView>(R.id.hourglass_pic)
        sensManager = getSystemService(Context.SENSOR_SERVICE) as SensorManager
        val sens = sensManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER)
        val saveButton = findViewById<Button>(R.id.save_button_timeHG)



        val sensListener = object : SensorEventListener {
            override fun onSensorChanged(p0: SensorEvent?) {
                val a = windowManager.defaultDisplay.rotation
                if (a == 2 && !UpsideDownTimerInProgressFlag) {
                    if (FirstTurnOverFlag)
                        timer.cancel()
                    tvTimer3.text = "Time is running out..."
                    Glide.with(applicationContext).load(R.drawable.hourglass).into(img)
                    startHourglassTimer(timerInterval)
                    FirstTurnOverFlag = true
                    UpsideDownTimerInProgressFlag = true
                    NormalTimerInProgressFlag = false

                } else if (a == 0 && FirstTurnOverFlag && !NormalTimerInProgressFlag) {
                    timer.cancel()

                    tvTimer3.text = "Time is running out..."
                    Glide.with(applicationContext).load(R.drawable.hourglass).into(img)

                    startHourglassTimer(timerInterval)
                    UpsideDownTimerInProgressFlag = false
                    NormalTimerInProgressFlag = true

                }

            }


            override fun onAccuracyChanged(p0: Sensor?, p1: Int) {
                // Изменение точности измерений нас не интересует
            }
        }
        sensManager.registerListener(sensListener, sens, SensorManager.SENSOR_DELAY_NORMAL)

        findViewById<ImageView>(R.id.hourglass_goback).setOnClickListener {
            setResult(RESULT_OK)
            finish()
        }

        saveButton.setOnClickListener {
            val seconds = findViewById<EditText>(R.id.seconds_et).text.run {
                takeIf { isNotEmpty() }?.toString()?.toLong()
            } ?: 0
            timerInterval=seconds*1000
            tvTimer1.text = (timerInterval / 1000).toString()
        }
    }


    companion object {
        const val refreshFunction: Long = 1
        // const val timerInterval: Long = 5000
    }
}
