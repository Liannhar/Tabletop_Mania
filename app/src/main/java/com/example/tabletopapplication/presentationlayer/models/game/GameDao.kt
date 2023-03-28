package com.example.tabletopapplication.presentationlayer.models.game

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.tabletopapplication.presentationlayer.models.DIce.Dice
import com.example.tabletopapplication.presentationlayer.models.Material.Material
import com.example.tabletopapplication.presentationlayer.models.Note.Note
import com.example.tabletopapplication.presentationlayer.models.Timer.Timer

@Dao
interface GameDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(game: Game):Long

    @Delete
    fun delete(game: Game)

    @Update
    fun update(game: Game)

    @Query("Select * from gameTable order by id ASC")
    fun getAllGame(): LiveData<List<Game>>

    @Query("Select * from gameTable Where id=:id")
    fun getOneGame(id:Long): Game

    @Query("SELECT n.id,n.gameId,n.noteDescription FROM gameTable AS g JOIN notesTable AS n ON g.id = n.gameId WHERE n.gameId = :id")
    fun getAllNoteOfGame(id:Long): LiveData<List<Note>>

    @Query("Select d.gameId,d.id from gameTable as g join diceTable as d on g.id=d.gameId Where d.gameId=:id")
    fun getAllDiceOfGame(id:Long): LiveData<List<Dice>>

    @Query("Select t.gameId,t.id from gameTable as g join timerTable as t on g.id=t.gameId Where t.gameId=:id")
    fun getAllTimerOfGame(id:Long): LiveData<List<Timer>>

    @Query("Select n.id,n.noteDescription,n.gameId from gameTable as g join notesTable as n on g.id=n.gameId Where n.gameId=:gameid AND n.id=:noteid ")
    fun getOneNoteOfGame(gameid:Long,noteid:Long): LiveData<Note>

    @Query("Select d.id,d.gameId from gameTable as g join diceTable as d on g.id=d.gameId Where d.gameId=:gameid AND d.id=:diceid")
    fun getOneDiceOfGame(gameid:Long,diceid:Long): LiveData<Dice>

    @Query("Select t.id,t.gameId from gameTable as g join timerTable as t on g.id=t.gameId Where t.gameId=:gameid AND t.id=:timerid")
    fun getOneTimerOfGame(gameid:Long,timerid:Long): LiveData<Timer>



    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(dice: Dice)

    @Delete
    fun delete(dice: Dice)

    @Update
    fun update(dice: Dice)

    @Query("Select * from diceTable order by id ASC")
    fun getAllDice(): LiveData<List<Dice>>

    @Query("Select * from diceTable Where id=:id")
    fun getOneDice(id:Long): LiveData<Dice>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(note : Note)

    @Delete
    fun delete(note: Note)

    @Update
    fun update(note: Note)

    @Query("Select * from notesTable order by id ASC")
    fun getAllNotes(): LiveData<List<Note>>

    @Query("Select * from notesTable Where id=:id")
    fun getOneNote(id:Long): LiveData<Note>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(timer: Timer)

    @Delete
    fun delete(timer: Timer)

    @Update
    fun update(timer: Timer)

    @Query("Select * from timerTable order by id ASC")
    fun getAllTimer(): LiveData<List<Timer>>

    @Query("Select * from timerTable Where id=:id")
    fun getOneTimer(id:Long): LiveData<Timer>
}