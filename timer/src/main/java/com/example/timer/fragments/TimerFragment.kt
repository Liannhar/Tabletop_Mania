package com.example.timer.fragments

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.example.timer.R
import com.tabletop.tabletopapplication.presentationlayer.common.Time
import com.tabletop.tabletopapplication.presentationlayer.common.Timer

class TimerFragment : Fragment(R.layout.sand_clock_fragment) {
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        val viewId = arguments?.run {
            getInt("viewId")
        } ?: 0

        val timeButton: TextView = view.findViewById(R.id.time)
        val stopButton: Button = view.findViewById(R.id.stop_button)
        val setButton: Button = view.findViewById(R.id.set_button)
        var minutes = arguments?.getInt("minuets") ?: 0
        var seconds = arguments?.getInt("seconds") ?: 0
        val time = "$minutes:$seconds"
        timeButton.text = time

        var mTimer = Timer(Time(minutes, seconds), 1)
        mTimer.observe(viewLifecycleOwner) {
            timeButton.text = it.toString()
        }

        timeButton.setOnClickListener {
            mTimer.start()
        }
        stopButton.setOnClickListener {
            mTimer.stop()
        }

        setButton.setOnClickListener {
            parentFragmentManager
                .beginTransaction()
                .replace(viewId, SetTimeFragment().apply {
                    arguments = Bundle().apply {
                        putInt("viewId", viewId)
                    }
                })
                .commit()
        }
    }
}
