package com.tabletop.tabletopapplication.presentationlayer.viewmodels

import android.app.Application
import android.app.GameManager
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.tabletop.tabletopapplication.businesslayer.ROOM.entities.GameROOM
import com.tabletop.tabletopapplication.businesslayer.ROOM.GameDatabase
import com.tabletop.tabletopapplication.businesslayer.ROOM.entities.MaterialROOM
import com.tabletop.tabletopapplication.businesslayer.ROOM.repositories.GameRepository
import com.tabletop.tabletopapplication.businesslayer.ROOM.repositories.MaterialRepository
import com.tabletop.tabletopapplication.presentationlayer.models.Game
import com.tabletop.tabletopapplication.presentationlayer.models.Material
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class DBViewModel(application: Application) : AndroidViewModel(application) {

    private val gameRepository: GameRepository
    private val materialRepository: MaterialRepository

    init {
        GameDatabase.getDatabase(application).getDbDao().apply {
            gameRepository = GameRepository(this)
            materialRepository = MaterialRepository(this)
        }
    }

    // Game
    fun add(game: Game) = viewModelScope.launch(Dispatchers.IO) {
        gameRepository.insert(GameROOM(game))
    }

    fun delete(game: Game) = viewModelScope.launch(Dispatchers.IO) {
        gameRepository.delete(GameROOM(game))
    }

    fun update(game: Game) = viewModelScope.launch(Dispatchers.IO) {
        gameRepository.update(GameROOM(game))
    }

    suspend fun getAllGames() = gameRepository.getAllGames() as List<Game>
    suspend fun getGame(id: Int) = gameRepository.getGameById(id) as Game?
    suspend fun getLastGame() = gameRepository.getLastGame() as Game?
    suspend fun getCountGames() = gameRepository.getCountGames()

    // Material
    fun add(material: Material) = viewModelScope.launch(Dispatchers.IO) {
        materialRepository.insert(MaterialROOM(material))
    }

    fun delete(material: Material) = viewModelScope.launch(Dispatchers.IO) {
        materialRepository.delete(MaterialROOM(material))
    }

    fun update(material: Material) = viewModelScope.launch(Dispatchers.IO) {
        materialRepository.update(MaterialROOM(material))
    }

    suspend fun getAllMaterials() = materialRepository.getAllMaterials() as List<Material>
    suspend fun getMaterial(id: Int) = materialRepository.getMaterialById(id) as Material?
    suspend fun getMaterial(name: String) = materialRepository.getMaterialByName(name) as Material?
    suspend fun getCountMaterials() = materialRepository.getCountMaterials()

    suspend fun updateMaterials(list: List<Material>) {

        for (material in getAllMaterials()) {
            if (list.none { it.id == material.id })
                delete(material)
        }

        list.forEach { material ->
            add(material)
        }
    }

    // GameMaterial
    fun addMaterialToGame(gameId: Int, material: Material) = viewModelScope.launch(Dispatchers.IO) {
        gameRepository.addMaterialToGame(gameId, MaterialROOM(material))
    }
    fun addMaterialToGame(game: Game, material: Material) =
        addMaterialToGame(game.id, material)
    fun addListMaterialToGame(game: Game, materials: List<Material>) = materials.forEach { material ->
        addMaterialToGame(game, material)
    }

    fun deleteMaterialFromGame(gameId: Int, materialId: Int) = viewModelScope.launch(Dispatchers.IO) {
        gameRepository.deleteMaterialFromGame(gameId, materialId)
    }

    fun deleteMaterialFromGame(game: Game, material: Material) =
        deleteMaterialFromGame(game.id, material.id)

    fun updateMaterialAtGame(gameId: Int, material: Material) = viewModelScope.launch(Dispatchers.IO) {
        gameRepository.updateMaterialAtGame(gameId, MaterialROOM(material))
    }
    fun updateMaterialAtGame(game: Game, material: Material) =
        updateMaterialAtGame(game.id, material)

    suspend fun getMaterialsByGame(id: Int) = gameRepository.getMaterialsByGameId(id) as List<Material>
}