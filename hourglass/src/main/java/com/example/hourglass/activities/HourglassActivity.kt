package com.example.hourglass.activities

import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.os.Bundle
import android.os.CountDownTimer
import android.util.Log
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
                tvTimer3.text = "Time is over!"
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
        val timerInterval: Long = 10000
        val tvTimer1 = findViewById<TextView>(R.id.hourglass_cv_1_text)
        tvTimer1.text = (timerInterval / 1000).toString()
        val tvTimer3 = findViewById<TextView>(R.id.hourglass_cv_3_text)
        tvTimer3.text = "Крути телефон влево для запуска песочных часов!"
        val img = findViewById<ImageView>(R.id.hourglass_pic)
        sensManager = getSystemService(Context.SENSOR_SERVICE) as SensorManager
        val sens = sensManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER)


        val sensListener = object : SensorEventListener {
            override fun onSensorChanged(p0: SensorEvent?) {
                val a = windowManager.defaultDisplay.rotation
                Log.d("lol", "$a")
                if (a == 2 && !UpsideDownTimerInProgressFlag) {
                    if (FirstTurnOverFlag)
                        timer.cancel()
                    tvTimer3.text = "Песочные часы запущены!"
                    Glide.with(applicationContext).load(R.drawable.hourglass).into(img)
                    startHourglassTimer(timerInterval)
                    FirstTurnOverFlag = true
                    UpsideDownTimerInProgressFlag = true
                    NormalTimerInProgressFlag=false

                } else if (a == 0 && FirstTurnOverFlag && !NormalTimerInProgressFlag) {
                    timer.cancel()

                    tvTimer3.text = "Песочные часы запущены!"
                    Glide.with(applicationContext).load(R.drawable.hourglass).into(img)

                    startHourglassTimer(timerInterval)
                    UpsideDownTimerInProgressFlag = false
                    NormalTimerInProgressFlag=true

                }

            }
            /*if (sideFlag && (it > 8)) {
                        tvTimer3.text = "Песочные часы запущены!"
                        Glide.with(applicationContext).load(R.drawable.hourglass).into(img)
                        if (startFlag) {
                            startHourglassTimer(timerInterval)
                            startFlag = false
                        }
                    } else if (!sideFlag && (it < -8)) {
                        tvTimer3.text = "Песочные часы запущены!"
                        Glide.with(applicationContext).load(R.drawable.hourglass).into(img)
                        if (startFlag) {
                            startHourglassTimer(timerInterval)
                            startFlag = false
                        }
                    }*/

            override fun onAccuracyChanged(p0: Sensor?, p1: Int) {
                // Изменение точности измерений нас не интересует
            }
        }
        sensManager.registerListener(sensListener, sens, SensorManager.SENSOR_DELAY_NORMAL)

        findViewById<ImageView>(R.id.hourglass_goback).setOnClickListener {
            setResult(RESULT_OK)
            finish()
        }
    }


    companion object {
        const val refreshFunction: Long = 1
        // const val timerInterval: Long = 5000
    }
}
