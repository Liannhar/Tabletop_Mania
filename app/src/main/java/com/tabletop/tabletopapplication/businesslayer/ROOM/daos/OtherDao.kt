package com.tabletop.tabletopapplication.businesslayer.ROOM.daos

import androidx.room.*
import com.tabletop.tabletopapplication.businesslayer.ROOM.entities.*
import kotlinx.coroutines.flow.Flow

interface OtherDao {

    @Query("SELECT n.noteDescription,n.gameId,n.id FROM games AS g JOIN notesTable AS n ON g.id = n.gameId WHERE n.gameId = :id")
    fun getNotesOfGame(id: Int): Flow<List<NoteROOM>>

    @Query("Select d.gameId,d.id from games as g join diceTable as d on g.id=d.gameId Where d.gameId=:id")
    fun getDicesOfGame(id: Int): Flow<List<DiceROOM>>

    @Query("Select t.gameId,t.id from games as g join timerTable as t on g.id=t.gameId Where t.gameId=:id")
    fun getTimersOfGame(id: Int): Flow<List<TimerROOM>>

    @Query("Select h.id,h.gameId from games as g join hourglassTable as h on g.id=h.gameId Where h.gameId=:id ")
    fun getHourglassesOfGame(id: Int): Flow<List<HourglassROOM>>

    @Query("Select n.noteDescription,n.gameId,n.id from games as g join notesTable as n on g.id=n.gameId Where n.gameId=:gameid AND n.id=:noteid Order BY n.id ")
    fun getNoteOfGameById(gameid: Int, noteid: Int): Flow<NoteROOM>

    @Query("Select d.id,d.gameId from games as g join diceTable as d on g.id=d.gameId Where d.gameId=:gameid AND d.id=:diceid Order BY d.id")
    fun getOneDiceOfGame(gameid: Int, diceid: Int): Flow<DiceROOM>

    @Query("Select h.id,h.gameId from games as g join hourglassTable as h on g.id=h.gameId Where h.gameId=:gameid AND h.id=:hourglassid Order BY h.id")
    fun getOneHourglassOfGame(gameid: Int, hourglassid: Int): Flow<HourglassROOM>

    @Query("Select t.id,t.gameId from games as g join timerTable as t on g.id=t.gameId Where t.gameId=:gameid AND t.id=:timerid Order BY t.id")
    fun getOneTimerOfGame(gameid: Int, timerid: Int): Flow<TimerROOM>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(hourglassROOM: HourglassROOM)

    @Delete
    fun delete(hourglassROOM: HourglassROOM)

    @Update
    fun update(hourglassROOM: HourglassROOM)

    @Query("Select * from hourglassTable order by id ASC")
    fun getAllHourglass(): Flow<List<HourglassROOM>>

    @Query("Select * from hourglassTable Where id=:id")
    fun getOneHourglass(id: Int): Flow<HourglassROOM>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(diceROOM: DiceROOM)

    @Delete
    fun delete(diceROOM: DiceROOM)

    @Update
    fun update(diceROOM: DiceROOM)

    @Query("Select * from diceTable order by id ASC")
    fun getAllDice(): Flow<List<DiceROOM>>

    @Query("Select * from diceTable Where id=:id")
    fun getOneDice(id: Int): Flow<DiceROOM>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(noteROOM: NoteROOM)

    @Delete
    fun delete(noteROOM: NoteROOM)

    @Update
    fun update(noteROOM: NoteROOM)

    @Query("Select * from notesTable order by id ASC")
    fun getAllNotes(): Flow<List<NoteROOM>>

    @Query("Select * from notesTable Where id=:id")
    fun getOneNote(id: Int): Flow<NoteROOM>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(timerROOM: TimerROOM)

    @Delete
    fun delete(timerROOM: TimerROOM)

    @Update
    fun update(timerROOM: TimerROOM)

    @Query("Select * from timerTable order by id ASC")
    fun getAllTimer(): Flow<List<TimerROOM>>

    @Query("Select * from timerTable Where id=:id")
    fun getOneTimer(id: Int): Flow<TimerROOM>

}