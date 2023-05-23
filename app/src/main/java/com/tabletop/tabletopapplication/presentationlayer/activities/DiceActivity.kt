package com.tabletop.tabletopapplication.presentationlayer.activities

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.FrameLayout
import android.widget.LinearLayout
import androidx.core.content.ContextCompat
import com.tabletop.tabletopapplication.R
import com.unity3d.player.UnityPlayer
import com.unity3d.player.UnityPlayerActivity


class DiceActivity : UnityPlayerActivity() {
    // Setup activity layout
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        addControlsToUnityFrame()
        val intent = intent
        handleIntent(intent)
    }

    override fun onNewIntent(intent: Intent) {
        super.onNewIntent(intent)
        handleIntent(intent)
        setIntent(intent)
    }

    fun handleIntent(intent: Intent?) {
        if (intent == null || intent.extras == null) return
        if (intent.extras!!.containsKey("doQuit")) if (mUnityPlayer == null) {
            finish()
        }
    }

    protected fun showMainActivity(setToColor: String?) {
        val intent = Intent(this, GamePreviewActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_REORDER_TO_FRONT or Intent.FLAG_ACTIVITY_SINGLE_TOP
        intent.putExtra("setColor", setToColor)
        startActivity(intent)
    }

    override fun onUnityPlayerUnloaded() {
        showMainActivity("")
    }

    fun addControlsToUnityFrame() {
        val layout: FrameLayout = mUnityPlayer
        /*val view = View(this)
        view.setBackgroundColor(ContextCompat.getColor(this, R.color.primary_color_app))
        view.layoutParams = ViewGroup.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,LinearLayout.LayoutParams.MATCH_PARENT)
        layout.addView(view)*/
        /*run {
            val myButton = Button(this)
            myButton.text = "Show Main"
            myButton.x = 10f
            myButton.y = 500f
            myButton.setOnClickListener { showMainActivity("") }
            layout.addView(myButton, 300, 200)
        }
        run {
            val myButton = Button(this)
            myButton.text = "Send Msg"
            myButton.x = 320f
            myButton.y = 500f
            myButton.setOnClickListener {
                UnityPlayer.UnitySendMessage(
                    "Cube",
                    "ChangeColor",
                    "yellow"
                )
            }
            layout.addView(myButton, 300, 200)
        }
        run {
            val myButton = Button(this)
            myButton.text = "Unload"
            myButton.x = 630f
            myButton.y = 500f
            myButton.setOnClickListener { mUnityPlayer.unload() }
            layout.addView(myButton, 300, 200)
        }*/
        run {
            val myButton = Button(this)
            myButton.text = "Finish"
            myButton.x = 700f
            myButton.y = 2000f
            myButton.setOnClickListener { showMainActivity("") }
            layout.addView(myButton, 300, 200)
        }
    }
}
