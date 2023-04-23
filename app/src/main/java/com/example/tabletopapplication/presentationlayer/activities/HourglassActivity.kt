
import android.content.Context
import android.content.Intent
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.example.tabletopapplication.R
import kotlin.math.abs

class HourglassActivity : AppCompatActivity() {
    lateinit var sensManager: SensorManager
    private var timer: CountDownTimer? = null
    private var startFlag: Boolean = true
    private var sideFlag: Boolean = true
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_hourglass)
        val tvTimer1 = findViewById<TextView>(R.id.hourglass_cv_1_text)
        tvTimer1.text = (timerInterval / 1000).toString()
        val tvTimer3 = findViewById<TextView>(R.id.hourglass_cv_3_text)
        tvTimer3.text = "Крути телефон влево для запуска песочных часов!"
        val img = findViewById<ImageView>(R.id.hourglass_pic)
        sensManager = getSystemService(Context.SENSOR_SERVICE) as SensorManager
        val sens = sensManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER)

        val sensListener = object : SensorEventListener {
            override fun onSensorChanged(p0: SensorEvent?) {
                val value = p0?.values
                val xAxisD = value?.get(0)
                xAxisD?.let {
                    if (sideFlag && (it > 3.4)) {
                        tvTimer3.text = "Песочные часы запущены!"
                        Glide.with(applicationContext).load(R.drawable.hourglass).into(img)
                        if (startFlag) {
                            startHourglassTimer(timerInterval)
                            startFlag = false
                        }
                    }
                    else if (!sideFlag && (it < -3.4)) {
                        tvTimer3.text = "Песочные часы запущены!"
                        Glide.with(applicationContext).load(R.drawable.hourglass).into(img)
                        if (startFlag) {
                            startHourglassTimer(timerInterval)
                            startFlag = false
                        }
                    }
                }
            }

            override fun onAccuracyChanged(p0: Sensor?, p1: Int) {
                // Изменение точности измерений нас не интересует
            }
        }
        sensManager.registerListener(sensListener, sens, SensorManager.SENSOR_DELAY_NORMAL)

        findViewById<ImageView>(R.id.hourglass_goback).setOnClickListener {
            val intent = Intent(this, GamePreviewActivity::class.java)
            val gameId = intent.getLongExtra("gameId",-1)
            intent.putExtra("gameId",gameId)
            startActivity(intent)
        }
    }

    private fun startHourglassTimer(msec: Long) {
        val tvTimer1 = findViewById<TextView>(R.id.hourglass_cv_1_text)
        val tvTimer3 = findViewById<TextView>(R.id.hourglass_cv_3_text)
        val img = findViewById<ImageView>(R.id.hourglass_pic)
        timer?.cancel()
        timer = object: CountDownTimer(msec, refreshFunction) {
            override fun onTick(p0: Long) {
                tvTimer1.text = (p0 / 1000).toString()
            }

            override fun onFinish() {
                if (sideFlag) {
                    tvTimer3.text = "Песок закончился!\nКрути телефон вправо, чтобы запустить часы снова!"
                    sideFlag = false
                }
                else {
                    tvTimer3.text = "Песок закончился!\nКрути телефон влево, чтобы запустить часы снова!"
                    sideFlag = true
                }
                Glide.with(applicationContext).load(R.drawable.hourglass1).into(img)
                startFlag = true
            }

        }.start()
    }

    companion object {
        const val refreshFunction: Long = 1
        const val timerInterval: Long = 5000
    }
}