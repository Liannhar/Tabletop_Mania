
import android.content.Context
import android.content.Intent
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.example.tabletopapplication.R
import kotlin.math.abs

class HourglassActivity : AppCompatActivity() {
    lateinit var sensManager: SensorManager
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_hourglass)
        val tvSens = findViewById<TextView>(R.id.hourglass_cv_3_text)
        val img = findViewById<ImageView>(R.id.hourglass_pic)
        sensManager = getSystemService(Context.SENSOR_SERVICE) as SensorManager
        val sens = sensManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER)
        val sensListener = object : SensorEventListener {
            override fun onSensorChanged(p0: SensorEvent?) {
                val value = p0?.values
                val xAxisD = value?.get(0)
                xAxisD?.let {
                    if (abs(it) > 3.4) {
                        tvSens.text = "Песочные часы запущены!"
                        Glide.with(applicationContext).load(R.drawable.hourglass).into(img)
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
}