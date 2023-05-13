package com.tabletop.tabletopapplication.presentationlayer.models.game

import androidx.lifecycle.LiveData
import com.tabletop.tabletopapplication.presentationlayer.models.DIce.Dice
import com.tabletop.tabletopapplication.presentationlayer.models.Hourglass.Hourglass
import com.tabletop.tabletopapplication.presentationlayer.models.Note.Note
import com.tabletop.tabletopapplication.presentationlayer.models.Timer.Timer
import kotlinx.coroutines.flow.Flow

class GameRepository(private val gameDao: GameDao) {

    val allGame: Flow<List<Game>> = gameDao.getAllGame()

    fun getOneGame(id: Long): Flow<Game> {
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

    fun getAllNoteOfGame(id:Long): Flow<List<Note>> = gameDao.getAllNoteOfGame(id)

    fun getAllDiceOfGame(id:Long): Flow<List<Dice>> = gameDao.getAllDiceOfGame(id)

    fun getAllHourglassOfGame(id:Long): Flow<List<Hourglass>> = gameDao.getAllHourglassOfGame(id)

    fun getAllTimerOfGame(id:Long): Flow<List<Timer>> = gameDao.getAllTimerOfGame(id)

    fun getOneDiceOfGame(gameid:Long,materialid:Long): Flow<Dice> = gameDao.getOneDiceOfGame(gameid,materialid)

    fun getOneNoteOfGame(gameid:Long,materialid:Long): Flow<Note> =  gameDao.getOneNoteOfGame(gameid,materialid)

    fun getOneTimerOfGame(gameid:Long,materialid:Long): Flow<Timer> = gameDao.getOneTimerOfGame(gameid,materialid)

    fun getDeleteDicesOfGame(count:Int): Flow<List<Dice>> = gameDao.getDeleteDicesOfGame(count)

    fun getDeleteNotesOfGame(count:Int): Flow<List<Note>> = gameDao.getDeleteNotesOfGame(count)
    fun getDeleteTimersOfGame(count:Int): Flow<List<Timer>> = gameDao.getDeleteTimersOfGame(count)
    fun getDeleteHourglassesOfGame(count:Int): Flow<List<Hourglass>> = gameDao.getDeleteHourglassesOfGame(count)

}