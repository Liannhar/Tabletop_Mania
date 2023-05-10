package com.tabletop.tabletopapplication.presentationlayer.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.tabletop.tabletopapplication.businesslayer.API.managers.GameManager
import com.tabletop.tabletopapplication.businesslayer.ROOM.entities.DiceROOM
import com.tabletop.tabletopapplication.businesslayer.ROOM.repositories.DiceRepository
import com.tabletop.tabletopapplication.businesslayer.ROOM.entities.HourglassROOM
import com.tabletop.tabletopapplication.businesslayer.ROOM.repositories.HourglassRepository
import com.tabletop.tabletopapplication.businesslayer.ROOM.entities.NoteROOM
import com.tabletop.tabletopapplication.businesslayer.ROOM.repositories.NoteRepository
import com.tabletop.tabletopapplication.businesslayer.ROOM.entities.TimerROOM
import com.tabletop.tabletopapplication.businesslayer.ROOM.repositories.TimerRepository
import com.tabletop.tabletopapplication.businesslayer.ROOM.entities.GameROOM
import com.tabletop.tabletopapplication.businesslayer.ROOM.GameDatabase
import com.tabletop.tabletopapplication.businesslayer.ROOM.repositories.GameRepository
import com.tabletop.tabletopapplication.presentationlayer.models.Game
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.launch

class DBViewModel(application: Application) : AndroidViewModel(application) {

    val gameRepository: GameRepository
    val noteRepository: NoteRepository
    val timerRepository: TimerRepository
    val diceRepository: DiceRepository
    val hourglassRepository: HourglassRepository
    val materialManager by lazy { GameManager() }

    init {
        val db = GameDatabase.getDatabase(application)
        val dao = GameDatabase.getDatabase(application).getDbDao()
        gameRepository = GameRepository(dao)
        noteRepository = NoteRepository(dao)
        timerRepository = TimerRepository(dao)
        diceRepository = DiceRepository(dao)
        hourglassRepository = HourglassRepository(dao)
    }

    fun deleteGame(gameROOM: GameROOM) = viewModelScope.launch(Dispatchers.IO) {
        gameRepository.delete(gameROOM)
    }

    fun deleteDice(diceROOM: DiceROOM) = viewModelScope.launch(Dispatchers.IO) {
        diceRepository.delete(diceROOM)
    }

    fun deleteHourglass(hourglassROOM: HourglassROOM) = viewModelScope.launch(Dispatchers.IO) {
        hourglassRepository.delete(hourglassROOM)
    }

    fun deleteTimer(timerROOM: TimerROOM) = viewModelScope.launch(Dispatchers.IO) {
        timerRepository.delete(timerROOM)
    }

    fun deleteNote(noteROOM: NoteROOM) = viewModelScope.launch(Dispatchers.IO) {
        noteRepository.delete(noteROOM)
    }

    fun updateGame(gameROOM: GameROOM) = viewModelScope.launch(Dispatchers.IO) {
        gameRepository.update(gameROOM)
    }

    fun updateDice(diceROOM: DiceROOM) = viewModelScope.launch(Dispatchers.IO) {
        diceRepository.update(diceROOM)
    }

    fun updateTimer(timerROOM: TimerROOM) = viewModelScope.launch(Dispatchers.IO) {
        timerRepository.update(timerROOM)
    }

    fun updateNote(noteROOM: NoteROOM) = viewModelScope.launch(Dispatchers.IO) {
        noteRepository.update(noteROOM)
    }

    fun addGame(game: Game) = viewModelScope.launch(Dispatchers.IO) {
        gameRepository.insert(GameROOM(game))
    }

    fun addDice(diceROOM: DiceROOM) = viewModelScope.launch(Dispatchers.IO) {
        diceRepository.insert(diceROOM)
    }

    fun addHourglass(hourglassROOM: HourglassROOM) = viewModelScope.launch(Dispatchers.IO) {
        hourglassRepository.insert(hourglassROOM)
    }


    fun addTimer(timerROOM: TimerROOM) = viewModelScope.launch(Dispatchers.IO) {
        timerRepository.insert(timerROOM)
    }

    fun addNote(noteROOM: NoteROOM) = viewModelScope.launch(Dispatchers.IO) {
        noteRepository.insert(noteROOM)
    }

    fun addGameApi() {

    }

    suspend fun getAllGames() = gameRepository.getAllGames() as List<Game>
    fun getAllDice() = diceRepository.allDiceROOM
    fun getAllTimer() = timerRepository.allTimerROOM
    fun getAllNotes() = noteRepository.allNotes

    suspend fun getGame(id: Int) = gameRepository.getGameById(id) as Game?
    fun getDice(id: Int) = diceRepository.getOneDice(id)
    fun getTimer(id: Int) = timerRepository.getOneTimer(id)
    fun getNote(id: Int) = noteRepository.getOneNote(id)

    suspend fun getLastGame() = gameRepository.getLastGame() as Game?

    suspend fun getCountGames() = gameRepository.getCountGames()

    fun getAllNoteOfGame(id: Int) = gameRepository.getAllNoteOfGame(id).distinctUntilChanged()
    fun getAllDiceOfGame(id: Int) = gameRepository.getAllDiceOfGame(id).distinctUntilChanged()
    fun getAllHourglassOfGame(id: Int) = gameRepository.getAllHourglassOfGame(id).distinctUntilChanged()
    fun getAllTimerOfGame(id: Int) = gameRepository.getAllTimerOfGame(id).distinctUntilChanged()

    fun getOneNoteOfGame(gameid: Int, materialid: Int) =
        gameRepository.getOneNoteOfGame(gameid, materialid)

    fun getOneDiceOfGame(gameid: Int, materialid: Int) =
        gameRepository.getOneDiceOfGame(gameid, materialid)

    fun getOneTimerOfGame(gameid: Int, materialid: Int) =
        gameRepository.getOneTimerOfGame(gameid, materialid)

//    fun getAllGameFromApi() {
//        viewModelScope.launch {
//            val games = arrayListOf(1, 2, 3)
//            materialManager.getGames(games) { result, error ->
//                when {
//                    result != null -> {
//                        result.forEach { addGame(GameROOM(getCountGames(), it.name, it.description, it.image)) }
//                        //addMaterial(Material(result.name,result.description,result.image_url))
//                    }
//                    error != null -> null
//                }
//            }
//        }
//    }

}