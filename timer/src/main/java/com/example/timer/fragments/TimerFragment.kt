package com.example.timer.fragments

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.example.timer.viewModels.TimerViewModel
import com.tabletop.tabletopapplication.R

class TimerFragment : Fragment(R.layout.sand_clock_fragment) {
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        val viewId = arguments?.run {
            getInt("viewId")
        } ?: 0

        val timeButton: TextView = view.findViewById(R.id.time)
        val stopButton: Button = view.findViewById(R.id.stop_button)
        val setButton: Button = view.findViewById(R.id.set_button)
        var flag = true
         val viewModel = TimerViewModel()
        var minuets: String = arguments?.getString("minuets") ?: "00"
        var seconds: String = arguments?.getString("seconds") ?: "00"
        val time = "$minuets:$seconds"
        timeButton.text=time
        var timer=viewModel.timer(minuets,seconds,timeButton)



        timeButton.setOnClickListener {
            timer.cancel()
            if (!flag) {
                flag = !flag
            }
             minuets= arguments?.getString("minuets") ?: "00"
             seconds = arguments?.getString("seconds") ?: "00"
            timer=viewModel.timer(minuets,seconds,timeButton)
            timer.start()
        }
        stopButton.setOnClickListener {
            flag = !flag
            if (!flag) {
                timer.cancel()
            } else {
                minuets= timeButton.text[0].toString()+timeButton.text[1].toString()
                seconds= timeButton.text[3].toString()+timeButton.text[4].toString()
                timer=viewModel.timer(minuets,seconds,timeButton)
                timer.start()
            }
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
