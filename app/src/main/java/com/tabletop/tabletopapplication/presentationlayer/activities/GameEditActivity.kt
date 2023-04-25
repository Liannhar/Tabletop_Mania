package com.tabletop.tabletopapplication.presentationlayer.activities

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.WindowManager
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.tabletop.tabletopapplication.R
import com.tabletop.tabletopapplication.presentationlayer.adapters.ModelAdapter
import com.tabletop.tabletopapplication.presentationlayer.models.Model
import com.tabletop.tabletopapplication.presentationlayer.viewmodels.GameDBViewModel
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

class GameEditActivity : AppCompatActivity(R.layout.activity_edit_game) {



    private val gameDBViewModel by lazy{ ViewModelProvider(this)[GameDBViewModel::class.java]}


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)
        val prefs = getSharedPreferences("MyPrefsFile", MODE_PRIVATE)
        val gameId = prefs.getLong("currentGameId", -1)
        val gameCount = prefs.getInt("gameCount", -1)
        val editGameTitle = findViewById<TextView>(R.id.activity_edit_game__title)
        val editGameImage = findViewById<ImageView>(R.id.activity_edit_game__image)
        val editGameDescription = findViewById<TextView>(R.id.activity_edit_game__description)
        val differentMaterialsadapter by lazy{ ModelAdapter(this,gameDBViewModel)}
        val materials = arrayListOf<Model>()

        lifecycleScope.launch(){
            gameDBViewModel.getGame(gameId).collect(){
                editGameTitle.text = it.name
                editGameDescription.text = it.description
                Glide.with(this@GameEditActivity).load(it.image).into(editGameImage)
            }
        }


        fillRecycler(gameId,materials,differentMaterialsadapter)
        //drawDB(gameId,materials)
        findViewById<RecyclerView>(R.id.activity_edit_game__rv).apply {
            layoutManager = LinearLayoutManager(context)
            adapter = differentMaterialsadapter
        }

        findViewById<ImageView>(R.id.activity_edit_game__save_button).setOnClickListener {
            val intent = Intent(this, GamePreviewActivity::class.java)
            startActivity(intent)
            finish()
        }

        findViewById<ImageView>(R.id.activity_edit_game__back_button).setOnClickListener {
            deleteMaterials(gameCount,gameId)

            val intent = Intent(this, GamePreviewActivity::class.java)
            startActivity(intent)
            finish()
        }

        findViewById<ImageView>(R.id.activity_edit_game__add_button).setOnClickListener {
            val intent = Intent(this, ChooseMaterialActivity::class.java)
            startActivity(intent)
            finish()
        }

        findViewById<CardView>(R.id.activity_edit_game__edit_button).setOnClickListener {
            val intent = Intent(this, GameEditPropertiesActivity::class.java)
            startActivity(intent)
            finish()
        }

    }
    /*private fun drawDB(gameId:Long, materials:ArrayList<Model>) {
         val idMaterial = intent.getLongExtra("idMaterial", -1)
         when (intent.getLongExtra("typeMaterial", -1)) {
             4L -> {
                 gameDBViewModel.getOneDiceOfGame(gameId,idMaterial).observe(this) {dice ->
                     differentMaterialsadapter.setItem(dice)
                 }
             }
             5L ->{
                 gameDBViewModel.getOneNoteOfGame(gameId,idMaterial).observe(this) {note ->
                     differentMaterialsadapter.setItem(note)
                 }
             }
             6L->{
                 gameDBViewModel.getOneTimerOfGame(gameId,idMaterial).observe(this) {timer ->
                     differentMaterialsadapter.setItem(timer)
                 }
             }
             else -> {}
         }
     }*/

    private fun deleteMaterials(gameCount:Int,gameId: Long){
        lifecycleScope.launch(){
            gameDBViewModel.getDeleteTimersOfGame(gameCount-1).first().forEach{gameDBViewModel.deleteTimer(it)}
            gameDBViewModel.getDeleteNotesOfGame(gameCount-1).first().forEach{gameDBViewModel.deleteNote(it)}
            gameDBViewModel.getDeleteDicesOfGame(gameCount-1).first().forEach{gameDBViewModel.deleteDice(it)}
            gameDBViewModel.getDeleteHourglassesOfGame(gameCount-1).first().forEach{gameDBViewModel.deleteHourglass(it)}
            val game = gameDBViewModel.getGame(gameId).first()
            Log.i("AAAAAA",game.count.toString())
            game.count=gameCount
            gameDBViewModel.updateGame(game)
        }
    }

    private fun fillRecycler(gameId:Long,materials:ArrayList<Model>,differentMaterialsadapter:ModelAdapter) {
        lifecycleScope.launch(){
            var m:List<Model> =   gameDBViewModel.getAllTimerOfGame(gameId).first()
            materials.addAll(m)
            m =   gameDBViewModel.getAllDiceOfGame(gameId).first()
            materials.addAll(m)
            m =   gameDBViewModel.getAllNoteOfGame(gameId).first()
            materials.addAll(m)
            m =   gameDBViewModel.getAllHourglassOfGame(gameId).first()
            materials.addAll(m)

            materials.sortByDescending { it.positionAdd }
            differentMaterialsadapter.setItems(materials)
        }

    }

}