package com.example.tabletopapplication.presentationlayer.fragments

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import androidx.fragment.app.Fragment
import com.example.tabletopapplication.R

class SetTimeFragment : Fragment(R.layout.set_time_fragment) {
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val button: Button = view.findViewById(R.id.set_time_button)
        button.setOnClickListener {
            val minuets: String = try {
                view.findViewById<EditText>(R.id.minuets).text.toString()
            } catch (error: Throwable) {
                "00"
            }
            val seconds: String = try {
                view.findViewById<EditText>(R.id.seconds).text.toString()
            } catch (error: Throwable) {
                "00"
            }
            activity?.supportFragmentManager?.let {
                val frag = TimerFragment.newInstance().apply {
                    val extras = Bundle().apply {
                        putString("minuets", minuets)
                        putString("seconds",seconds)
                    }
                    arguments = extras
                }
                val transaction = it.beginTransaction()
                transaction.replace(R.id.timer_host, frag, TimerFragment.TAG)
                transaction.commit()
            }

        }
    }

    companion object {

        fun newInstance(): Fragment = SetTimeFragment()

        const val TAG = "GameListFragment"
    }
}