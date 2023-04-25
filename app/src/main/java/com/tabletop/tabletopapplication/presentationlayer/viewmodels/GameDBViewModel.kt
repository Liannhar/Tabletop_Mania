package com.tabletop.tabletopapplication.presentationlayer.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.tabletop.tabletopapplication.businesslayer.managers.GameManager
import com.tabletop.tabletopapplication.presentationlayer.models.DIce.Dice
import com.tabletop.tabletopapplication.presentationlayer.models.DIce.DiceRepository
import com.tabletop.tabletopapplication.presentationlayer.models.Hourglass.Hourglass
import com.tabletop.tabletopapplication.presentationlayer.models.Hourglass.HourglassRepository
import com.tabletop.tabletopapplication.presentationlayer.models.Note.Note
import com.tabletop.tabletopapplication.presentationlayer.models.Note.NoteRepository
import com.tabletop.tabletopapplication.presentationlayer.models.Timer.Timer
import com.tabletop.tabletopapplication.presentationlayer.models.Timer.TimerRepository
import com.tabletop.tabletopapplication.presentationlayer.models.game.Game
import com.tabletop.tabletopapplication.presentationlayer.models.game.GameDatabase
import com.tabletop.tabletopapplication.presentationlayer.models.game.GameRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.launch

class GameDBViewModel(application: Application) : AndroidViewModel(application)  {
    val gameRepository : GameRepository
    val noteRepository : NoteRepository
    val timerRepository : TimerRepository
    val diceRepository : DiceRepository
    val hourglassRepository : HourglassRepository
    val materialManager by lazy{ GameManager() }

    init {
        val dao = GameDatabase.getDatabase(application).getGameDao()
        gameRepository = GameRepository(dao)
        noteRepository = NoteRepository(dao)
        timerRepository = TimerRepository(dao)
        diceRepository = DiceRepository(dao)
        hourglassRepository= HourglassRepository(dao)
    }

    fun deleteGame (game: Game) = viewModelScope.launch(Dispatchers.IO) {
        gameRepository.delete(game)
    }

    fun deleteDice (dice: Dice) = viewModelScope.launch(Dispatchers.IO) {
        diceRepository.delete(dice)
    }
    fun deleteHourglass (hourglass: Hourglass) = viewModelScope.launch(Dispatchers.IO) {
        hourglassRepository.delete(hourglass)
    }

    fun deleteTimer (timer: Timer) = viewModelScope.launch(Dispatchers.IO) {
        timerRepository.delete(timer)
    }

    fun deleteNote (note: Note) = viewModelScope.launch(Dispatchers.IO) {
        noteRepository.delete(note)
    }

    fun updateGame(game: Game) = viewModelScope.launch(Dispatchers.IO) {
        gameRepository.update(game)
    }

    fun updateDice(dice:Dice) = viewModelScope.launch(Dispatchers.IO) {
        diceRepository.update(dice)
    }

    fun updateTimer(timer: Timer) = viewModelScope.launch(Dispatchers.IO) {
        timerRepository.update(timer)
    }

    fun updateNote(note: Note) = viewModelScope.launch(Dispatchers.IO) {
        noteRepository.update(note)
    }

    fun addGame(game: Game) = viewModelScope.launch(Dispatchers.IO) {
        gameRepository.insert(game)
    }

    fun addDice(dice:Dice) = viewModelScope.launch(Dispatchers.IO) {
        diceRepository.insert(dice)
    }

    fun addHourglass(hourglass: Hourglass) = viewModelScope.launch(Dispatchers.IO) {
        hourglassRepository.insert(hourglass)
    }


    fun addTimer(timer: Timer) = viewModelScope.launch(Dispatchers.IO) {
        timerRepository.insert(timer)
    }

    fun addNote(note: Note) = viewModelScope.launch(Dispatchers.IO) {
        noteRepository.insert(note)
    }

    fun addGameApi(){

    }

    fun getGame(id:Long) = gameRepository.getOneGame(id)

    fun getDice(id:Long) = diceRepository.getOneDice(id)

    fun getTimer(id:Long) = timerRepository.getOneTimer(id)

    fun getNote(id:Long) = noteRepository.getOneNote(id)

    fun getAllGame() = gameRepository.allGame

    fun getAllDice() = diceRepository.allDice

    fun getAllTimer() = timerRepository.allTimer

    fun getAllNotes() = noteRepository.allNotes

    fun getAllNoteOfGame(id:Long) = gameRepository.getAllNoteOfGame(id).distinctUntilChanged()

    fun getAllDiceOfGame(id:Long) = gameRepository.getAllDiceOfGame(id).distinctUntilChanged()

    fun getAllHourglassOfGame(id:Long) = gameRepository.getAllHourglassOfGame(id).distinctUntilChanged()

    fun getAllTimerOfGame(id:Long) = gameRepository.getAllTimerOfGame(id).distinctUntilChanged()

    fun getDeleteTimersOfGame(count:Int) = gameRepository.getDeleteTimersOfGame(count)
    fun getDeleteNotesOfGame(count:Int) = gameRepository.getDeleteNotesOfGame(count)
    fun getDeleteDicesOfGame(count:Int) = gameRepository.getDeleteDicesOfGame(count)
    fun getDeleteHourglassesOfGame(count:Int) = gameRepository.getDeleteHourglassesOfGame(count)

    fun getOneNoteOfGame(gameid:Long,materialid:Long)  = gameRepository.getOneNoteOfGame(gameid,materialid)

    fun getOneDiceOfGame(gameid:Long,materialid:Long)  = gameRepository.getOneDiceOfGame(gameid,materialid)

    fun getOneTimerOfGame(gameid:Long,materialid:Long) = gameRepository.getOneTimerOfGame(gameid,materialid)

    fun getAllGameFromApi(){
        viewModelScope.launch{
            val games = arrayListOf(1,2,3)
            materialManager.getGames(games){
                    result, error ->
                when{
                    result!= null -> {
                        result.forEach { addGame(Game(it.name,it.description,it.image, count = 0) )}
                        //addMaterial(Material(result.name,result.description,result.image_url))
                    }
                    error!=null->null
                }
            }
        }
    }

}