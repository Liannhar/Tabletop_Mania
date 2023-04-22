package com.tabletop.tabletopapplication.presentationlayer.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.tabletop.tabletopapplication.businesslayer.managers.GameManager
import com.tabletop.tabletopapplication.presentationlayer.models.game.Game
import com.tabletop.tabletopapplication.presentationlayer.models.game.GameDatabase
import com.tabletop.tabletopapplication.presentationlayer.models.game.GameRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class GameDBViewModel(application: Application) : AndroidViewModel(application)  {
    val repository : GameRepository
    val materialManager by lazy{ GameManager() }

    init {
        val dao = GameDatabase.getDatabase(application).getGameDao()
        repository = GameRepository(dao)
    }

    fun deleteGame (game: Game) = viewModelScope.launch(Dispatchers.IO) {
        repository.delete(game)
    }

    fun updateGame(game: Game) = viewModelScope.launch(Dispatchers.IO) {
        repository.update(game)
    }

    fun addGame(game: Game):MutableLiveData<Long>{
        val res = MutableLiveData<Long>()
        viewModelScope.launch(Dispatchers.IO) {
            val result =  repository.insert(game)
            res.postValue(result)
        }
        return res
    }

    fun addGameApi(){

    }

    fun getGame(id:Long) = repository.getOneGame(id)

    fun getAllGame() = repository.allGame

    fun getAllNoteOfGame(id:Long) = repository.getAllNoteOfGame(id)

    fun getAllDiceOfGame(id:Long) = repository.getAllDiceOfGame(id)

    fun getAllTimerOfGame(id:Long) = repository.getAllTimerOfGame(id)

    fun getOneNoteOfGame(gameid:Long,materialid:Long)  = repository.getOneNoteOfGame(gameid,materialid)

    fun getOneDiceOfGame(gameid:Long,materialid:Long)  = repository.getOneDiceOfGame(gameid,materialid)

    fun getOneTimerOfGame(gameid:Long,materialid:Long) = repository.getOneTimerOfGame(gameid,materialid)

    fun getAllGameFromApi(){
        viewModelScope.launch{
            val games = arrayListOf(1,2,3)
            materialManager.getGames(games){
                    result, error ->
                when{
                    result!= null -> {
                        result.forEach { addGame(Game(it.name,it.description,it.image) )}
                        //addMaterial(Material(result.name,result.description,result.image_url))
                    }
                    error!=null->null
                }
            }
        }
    }

}