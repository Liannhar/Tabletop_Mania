package com.tabletop.tabletopapplication.businesslayer.ROOM.repositories

import com.tabletop.tabletopapplication.businesslayer.ROOM.entities.DiceROOM
import com.tabletop.tabletopapplication.businesslayer.ROOM.entities.HourglassROOM
import com.tabletop.tabletopapplication.businesslayer.ROOM.entities.NoteROOM
import com.tabletop.tabletopapplication.businesslayer.ROOM.entities.TimerROOM
import com.tabletop.tabletopapplication.businesslayer.ROOM.entities.GameROOM
import com.tabletop.tabletopapplication.businesslayer.ROOM.daos.GameDao
import kotlinx.coroutines.flow.Flow

class GameRepository(private val gameDao: GameDao) {

    val getAllGames: Flow<List<GameROOM>> = gameDao.getAllGames()

    fun getGameById(id: Int): Flow<GameROOM> = gameDao.getGameById(id)

    fun getCountGames(): Flow<Int> = gameDao.getCountGames()

    fun insert(gameROOM: GameROOM){
        gameDao.insert(gameROOM)
    }

    fun delete(gameROOM: GameROOM){
        gameDao.delete(gameROOM)
    }

    fun update(gameROOM: GameROOM){
        gameDao.update(gameROOM)
    }

    fun getAllNoteOfGame(id:Int): Flow<List<NoteROOM>> = gameDao.getNotesOfGame(id)

    fun getAllDiceOfGame(id:Int): Flow<List<DiceROOM>> = gameDao.getDicesOfGame(id)

    fun getAllHourglassOfGame(id:Int): Flow<List<HourglassROOM>> = gameDao.getHourglassesOfGame(id)

    fun getAllTimerOfGame(id:Int): Flow<List<TimerROOM>> = gameDao.getTimersOfGame(id)

    fun getOneDiceOfGame(gameid:Int,materialid:Int): Flow<DiceROOM> = gameDao.getOneDiceOfGame(gameid,materialid)

    fun getOneNoteOfGame(gameid:Int,materialid:Int): Flow<NoteROOM> =  gameDao.getNoteOfGameById(gameid,materialid)

    fun getOneTimerOfGame(gameid:Int,materialid:Int): Flow<TimerROOM> = gameDao.getOneTimerOfGame(gameid,materialid)

}