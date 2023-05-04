package com.tabletop.tabletopapplication.presentationlayer.adapters

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat.startActivityForResult
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.tabletop.tabletopapplication.R
import com.tabletop.tabletopapplication.presentationlayer.activities.GamePreviewActivity
import com.tabletop.tabletopapplication.presentationlayer.activities.GameSelectionActivity
import com.tabletop.tabletopapplication.presentationlayer.models.Game


class GameAdapter(
    private var games: ArrayList<Game> = arrayListOf()
) : RecyclerView.Adapter<GameAdapter.Holder>() {

    class Holder(view: View) : RecyclerView.ViewHolder(view) {

        private val context = view.context
        private val element = view.findViewById<LinearLayout>(R.id.game_element__ll)
        private val name = view.findViewById<TextView>(R.id.game_name)
        private val image = view.findViewById<ImageView>(R.id.game_img)

        fun bind(item: Game) {
            name.text = item.name

            Glide.with(context)
                .load(item.image)
                .error(R.drawable.baseline_error_outline_24)
                .into(image)

            element.setOnClickListener {
                when (context) {
                    is GameSelectionActivity -> {
                        val intent = Intent(context, GamePreviewActivity::class.java).apply {
                            context.getSharedPreferences(
                                "MyPrefsFile",
                                AppCompatActivity.MODE_PRIVATE
                            ).edit().putInt("currentGameId", item.id).apply()

                        }

                        startActivityForResult(context, intent, 1, Bundle())
                        context.finish()
                    }
                }
            }

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.game_element, parent, false)
        return Holder(view)
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

    fun findGameById(id: Int): Int {
        for (i in 0..games.size)
            if (games[i].id == id)
                return i
        return -1
    }
}