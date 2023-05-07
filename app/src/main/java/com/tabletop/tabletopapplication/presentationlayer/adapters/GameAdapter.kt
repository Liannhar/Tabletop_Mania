package com.tabletop.tabletopapplication.presentationlayer.adapters

import android.content.Intent
import android.graphics.BitmapFactory
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.activity.result.ActivityResultLauncher
import androidx.core.net.toUri
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.tabletop.tabletopapplication.R
import com.tabletop.tabletopapplication.presentationlayer.activities.GamePreviewActivity
import com.tabletop.tabletopapplication.presentationlayer.models.Game
import kotlinx.coroutines.coroutineScope


class GameAdapter(
    private var games: ArrayList<Game> = arrayListOf(),
    private val clickAction: ActivityResultLauncher<Intent>
) : RecyclerView.Adapter<GameAdapter.Holder>() {

    class Holder(
        view: View,
        private val clickAction: ActivityResultLauncher<Intent>
        ) : RecyclerView.ViewHolder(view) {

        private val element = view.findViewById<LinearLayout>(R.id.game_element__ll)
        private val name = view.findViewById<TextView>(R.id.game_name)
        private val image = view.findViewById<ImageView>(R.id.game_img)

        fun bind(item: Game) {
            name.text = item.name

            Glide.with(image)
                .load(item.image)
                .placeholder(R.drawable.baseline_downloading_24)
                .error(R.drawable.baseline_error_outline_24)
                .into(image)

            element.setOnClickListener {
                clickAction.launch(Intent(itemView.context, GamePreviewActivity::class.java).apply {
                    putExtra("id", item.id)
                })
            }

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.game_element, parent, false)
        return Holder(view, clickAction)
    }

    override fun onBindViewHolder(holder: Holder, position: Int) =
        holder.bind(games[position])

    override fun getItemCount(): Int = games.size

    fun addGame(game: Game) {
        games.add(game)
        notifyItemInserted(games.size - 1)
    }

    fun addListGames(games: List<Game>) {
        this.games.addAll(games)
        notifyItemRangeInserted(this.games.size - games.size, games.size)
    }

    fun replaceGame(position: Int, newGame: Game) {
        games[position] = newGame
        notifyItemChanged(position)
    }

    fun findPositionById(id: Int): Int {
        for (i in 0..games.size)
            if (games[i].id == id)
                return i
        return -1
    }
}