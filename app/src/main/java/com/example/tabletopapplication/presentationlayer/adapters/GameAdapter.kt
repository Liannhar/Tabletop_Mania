package com.example.tabletopapplication.presentationlayer.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.tabletopapplication.R
import com.example.tabletopapplication.businesslayer.models.GameEntity

@SuppressLint("NotifyDataSetChanged")
class GameAdapter: RecyclerView.Adapter<GameAdapter.Holder>() {
    private var items: List<GameEntity> = emptyList()

    fun submitItems(data: List<GameEntity>) {
        items = data
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GameAdapter.Holder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.game_element, parent, false)

        return Holder(view)
    }

    override fun onBindViewHolder(holder: GameAdapter.Holder, position: Int) = holder.bind(items[position])

    override fun getItemCount(): Int = items.size

    inner class Holder(view: View): RecyclerView.ViewHolder(view) {
         val image by lazy { view.findViewById<ImageView>(R.id.game_img) }
        val name = view.findViewById<TextView>(R.id.game_name)

        fun bind(item: GameEntity) {
            Glide.with(image).load(item.image_url).into(image)
            name.text = item.name
        }
    }

}