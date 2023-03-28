package com.example.tabletopapplication.presentationlayer.models.game

import androidx.lifecycle.LiveData
import com.example.tabletopapplication.presentationlayer.models.DIce.Dice
import com.example.tabletopapplication.presentationlayer.models.Material.Material
import com.example.tabletopapplication.presentationlayer.models.Material.MaterialDao
import com.example.tabletopapplication.presentationlayer.models.Note.Note
import com.example.tabletopapplication.presentationlayer.models.Timer.Timer

class GameRepository(private val gameDao: GameDao) {

    val allGame: LiveData<List<Game>> = gameDao.getAllGame()

    fun getOneGame(id: Long): Game {
        return gameDao.getOneGame(id)
    }

    fun insert(game: Game):Long {
        return gameDao.insert(game)
    }

    fun delete(game: Game){
        gameDao.delete(game)
    }

    fun update(game: Game){
        gameDao.update(game)
    }

    fun getAllNoteOfGame(id:Long): LiveData<List<Note>> = gameDao.getAllNoteOfGame(id)

    fun getAllDiceOfGame(id:Long): LiveData<List<Dice>> = gameDao.getAllDiceOfGame(id)

    fun getAllTimerOfGame(id:Long): LiveData<List<Timer>> = gameDao.getAllTimerOfGame(id)

    fun getOneDiceOfGame(gameid:Long,materialid:Long): LiveData<Dice> = gameDao.getOneDiceOfGame(gameid,materialid)

    fun getOneNoteOfGame(gameid:Long,materialid:Long): LiveData<Note> =  gameDao.getOneNoteOfGame(gameid,materialid)

    fun getOneTimerOfGame(gameid:Long,materialid:Long): LiveData<Timer> = gameDao.getOneTimerOfGame(gameid,materialid)
}