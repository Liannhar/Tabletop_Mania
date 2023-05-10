package com.tabletop.tabletopapplication.businesslayer.ROOM.repositories

import android.util.Log
import com.tabletop.tabletopapplication.businesslayer.ROOM.daos.DatabaseDao
import com.tabletop.tabletopapplication.businesslayer.ROOM.entities.DiceROOM
import com.tabletop.tabletopapplication.businesslayer.ROOM.entities.HourglassROOM
import com.tabletop.tabletopapplication.businesslayer.ROOM.entities.NoteROOM
import com.tabletop.tabletopapplication.businesslayer.ROOM.entities.TimerROOM
import com.tabletop.tabletopapplication.businesslayer.ROOM.entities.GameROOM
import com.tabletop.tabletopapplication.businesslayer.ROOM.daos.GameDao
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first

class GameRepository(private val gameDao: DatabaseDao) {

    suspend fun getAllGames() = gameDao.getAllGames().first()

    suspend fun getGameById(id: Int) = gameDao.getGameById(id).first()

    suspend fun getLastGame() = gameDao.getLastGame().first()

    suspend fun getCountGames() = gameDao.getCountGames().first()

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