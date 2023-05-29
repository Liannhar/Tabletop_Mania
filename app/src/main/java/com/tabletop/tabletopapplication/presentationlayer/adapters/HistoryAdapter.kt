package com.tabletop.tabletopapplication.presentationlayer.adapters

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.tabletop.tabletopapplication.R
import com.tabletop.tabletopapplication.businesslayer.models.History


class HistoryAdapter(
    private val list: MutableList<History>
): RecyclerView.Adapter<HistoryAdapter.ViewHolder>() {

    class ViewHolder(view: View) :
        RecyclerView.ViewHolder(view) {
        private val date = view.findViewById<TextView>(R.id.date)
        private val winner = view.findViewById<TextView>(R.id.winner)
        private val score = view.findViewById<TextView>(R.id.score)

        fun bind(history: History) {
            date.text = history.date
            winner.text = history.winner
            score.text = history.score

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.card_history, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(list[position])
    }


    override fun getItemCount(): Int = list.size

    fun updateData(new_history: List<History>) {
        notifyItemRangeRemoved(0, list.size)
        list.clear()
        list.addAll(new_history)
        notifyItemRangeInserted(0, list.size)
    }
}
