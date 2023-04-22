package com.tabletop.tabletopapplication.presentationlayer.fragments

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.tabletopapplication.R
import com.example.tabletopapplication.presentationlayer.models.TimerViewModel
import com.tabletop.tabletopapplication.presentationlayer.activities.GameEditActivity
import com.tabletop.tabletopapplication.presentationlayer.activities.GamePreviewActivity

class TimerFragment : Fragment(R.layout.sand_clock_fragment) {
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        val timeButton: TextView = view.findViewById(R.id.time)
        val stopButton: Button = view.findViewById(R.id.stop_button)
        val setButton: Button = view.findViewById(R.id.set_button)
        var flag = true
         val viewModel by viewModels<TimerViewModel>()
        var minuets: String = arguments?.getString("minuets") ?: "00"
        var seconds: String = arguments?.getString("seconds") ?: "00"
        val time = "$minuets:$seconds"
        timeButton.text=time
        var timer=viewModel.timer(minuets,seconds,timeButton)



        timeButton.setOnClickListener {
            timer.cancel()
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
            onSetClick()
        }

        when(context) {
            is GamePreviewActivity -> {
            }
            is GameEditActivity -> {

            }
        }

    }


    fun onSetClick() {
        activity?.supportFragmentManager?.let {


            val transaction = it.beginTransaction()
            transaction.replace(R.id.timer_host, SetTimeFragment.newInstance(), SetTimeFragment.TAG)
            transaction.commit()
        }

    }

    companion object {

        fun newInstance(): Fragment = TimerFragment()

        const val TAG = "GameListFragment"

    }
}
