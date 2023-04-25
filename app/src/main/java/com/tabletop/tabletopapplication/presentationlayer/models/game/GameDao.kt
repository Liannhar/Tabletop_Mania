package com.tabletop.tabletopapplication.presentationlayer.models.game

import androidx.lifecycle.LiveData
import androidx.room.*
import com.tabletop.tabletopapplication.presentationlayer.models.DIce.Dice
import com.tabletop.tabletopapplication.presentationlayer.models.Hourglass.Hourglass
import com.tabletop.tabletopapplication.presentationlayer.models.Material.Material
import com.tabletop.tabletopapplication.presentationlayer.models.Note.Note
import com.tabletop.tabletopapplication.presentationlayer.models.Timer.Timer
import kotlinx.coroutines.flow.Flow

@Dao
interface GameDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(game: Game):Long

    @Delete
    fun delete(game: Game)

    @Update
    fun update(game: Game)

    @Query("Select * from gameTable order by id ASC")
    fun getAllGame(): Flow<List<Game>>

    @Query("Select * from gameTable Where id=:id")
    fun getOneGame(id:Long): Flow<Game>

    @Query("SELECT n.noteDescription,n.gameId,n.id,n.positionAdd FROM gameTable AS g JOIN notesTable AS n ON g.id = n.gameId WHERE n.gameId = :id")
    fun getAllNoteOfGame(id:Long): Flow<List<Note>>

    @Query("Select d.gameId,d.id,d.positionAdd from gameTable as g join diceTable as d on g.id=d.gameId Where d.gameId=:id")
    fun getAllDiceOfGame(id:Long): Flow<List<Dice>>

    @Query("Select t.gameId,t.id,t.positionAdd from gameTable as g join timerTable as t on g.id=t.gameId Where t.gameId=:id")
    fun getAllTimerOfGame(id:Long): Flow<List<Timer>>

    @Query("Select h.id,h.gameId,h.positionAdd from gameTable as g join hourglassTable as h on g.id=h.gameId Where h.gameId=:id ")
    fun getAllHourglassOfGame(id:Long): Flow<List<Hourglass>>

    @Query("Select n.noteDescription,n.gameId,n.id,n.positionAdd from gameTable as g join notesTable as n on g.id=n.gameId Where n.gameId=:gameid AND n.id=:noteid Order BY n.id ")
    fun getOneNoteOfGame(gameid:Long,noteid:Long): Flow<Note>

    @Query("Select d.id,d.gameId,d.positionAdd from gameTable as g join diceTable as d on g.id=d.gameId Where d.gameId=:gameid AND d.id=:diceid Order BY d.id")
    fun getOneDiceOfGame(gameid:Long,diceid:Long): Flow<Dice>

    @Query("Select h.id,h.gameId,h.positionAdd from gameTable as g join hourglassTable as h on g.id=h.gameId Where h.gameId=:gameid AND h.id=:hourglassid Order BY h.id")
    fun getOneHourglassOfGame(gameid:Long,hourglassid:Long): Flow<Hourglass>

    @Query("Select t.id,t.gameId,t.positionAdd from gameTable as g join timerTable as t on g.id=t.gameId Where t.gameId=:gameid AND t.id=:timerid Order BY t.id")
    fun getOneTimerOfGame(gameid:Long,timerid:Long): Flow<Timer>

    @Query("Select t.id,t.gameId,t.positionAdd from gameTable as g join timerTable as t on g.id=t.gameId Where t.positionAdd>:count")
    fun getDeleteTimersOfGame(count:Int): Flow<List<Timer>>

    @Query("Select n.noteDescription,n.gameId,n.id,n.positionAdd from gameTable as g join notesTable as n on g.id=n.gameId Where n.positionAdd>:count")
    fun getDeleteNotesOfGame(count:Int): Flow<List<Note>>

    @Query("Select d.id,d.gameId,d.positionAdd from gameTable as g join diceTable as d on g.id=d.gameId Where d.positionAdd>:count")
    fun getDeleteDicesOfGame(count:Int): Flow<List<Dice>>

    @Query("Select h.id,h.gameId,h.positionAdd from gameTable as g join hourglassTable as h on g.id=h.gameId Where h.positionAdd>:count")
    fun getDeleteHourglassesOfGame(count:Int): Flow<List<Hourglass>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(hourglass: Hourglass)

    @Delete
    fun delete(hourglass: Hourglass)

    @Update
    fun update(hourglass: Hourglass)

    @Query("Select * from hourglassTable order by id ASC")
    fun getAllHourglass(): Flow<List<Hourglass>>

    @Query("Select * from hourglassTable Where id=:id")
    fun getOneHourglass(id:Long):Flow<Hourglass>
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(dice: Dice)

    @Delete
    fun delete(dice: Dice)

    @Update
    fun update(dice: Dice)

    @Query("Select * from diceTable order by id ASC")
    fun getAllDice(): Flow<List<Dice>>

    @Query("Select * from diceTable Where id=:id")
    fun getOneDice(id:Long):Flow<Dice>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(note : Note)

    @Delete
    fun delete(note: Note)

    @Update
    fun update(note: Note)

    @Query("Select * from notesTable order by id ASC")
    fun getAllNotes(): Flow<List<Note>>

    @Query("Select * from notesTable Where id=:id")
    fun getOneNote(id:Long): Flow<Note>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(timer: Timer)

    @Delete
    fun delete(timer: Timer)

    @Update
    fun update(timer: Timer)

    @Query("Select * from timerTable order by id ASC")
    fun getAllTimer(): Flow<List<Timer>>

    @Query("Select * from timerTable Where id=:id")
    fun getOneTimer(id:Long):Flow<Timer>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(material: Material)

    @Delete
    fun delete(material: Material)

    @Update
    fun update(material: Material)

    @Query("Select * from materialsTable order by id ASC")
    fun getAllMaterials(): Flow<List<Material>>

    @Query("Select * from materialsTable Where id=:id")
    fun getOneMaterial(id:Long): Material
}