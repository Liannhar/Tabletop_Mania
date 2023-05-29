package com.tabletop.tabletopapplication.presentationlayer.adapters

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.lifecycle.ViewModel
import androidx.recyclerview.widget.RecyclerView
import com.tabletop.tabletopapplication.R
import com.tabletop.tabletopapplication.businesslayer.models.History


class HistoryAdapter(private val list: MutableList<History>) :
    RecyclerView.Adapter<HistoryAdapter.HistoryViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HistoryViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.card_history, null)
        return HistoryViewHolder(view)
    }

    override fun onBindViewHolder(holder: HistoryViewHolder, position: Int) {
        holder.bind(list[position])
    }


    override fun getItemCount(): Int {
        return list.size
    }
    fun updateData(new_history: List<History>) {
        list.clear()
        list.addAll(new_history)
        notifyDataSetChanged()
        Log.d("ad","${new_history.size}")
    }
    class HistoryViewHolder(view: View) :
        RecyclerView.ViewHolder(view) {
        protected val date = view.findViewById<TextView>(R.id.date)
        protected val winner = view.findViewById<TextView>(R.id.winner)
        protected val score = view.findViewById<TextView>(R.id.score)

        fun bind(history: History) {
            date.text = history.date
            winner.text=history.winner
            score.text=history.score

        }
    }
}
