package com.tabletop.tabletopapplication.presentationlayer.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.tabletop.tabletopapplication.businesslayer.API.managers.MaterialManager
import com.tabletop.tabletopapplication.businesslayer.API.common.LoadState
import com.tabletop.tabletopapplication.businesslayer.ROOM.entities.MaterialROOM
import com.tabletop.tabletopapplication.businesslayer.ROOM.repositories.MaterialRepository
import com.tabletop.tabletopapplication.businesslayer.ROOM.GameDatabase
import com.tabletop.tabletopapplication.presentationlayer.models.Material
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MaterialViewModel(application: Application) : AndroidViewModel(application) {

    private val repository : MaterialRepository
    private val materialManager by lazy{ MaterialManager() }
    val state = MutableLiveData<LoadState>()

    init {
        val dao = GameDatabase.getDatabase(application).getDbDao()
        repository = MaterialRepository(dao)
    }

    fun deleteMaterial (materialROOM: MaterialROOM) = viewModelScope.launch(Dispatchers.IO) {
        repository.delete(materialROOM)
    }

    fun updateMaterial(materialROOM: MaterialROOM) = viewModelScope.launch(Dispatchers.IO) {
        repository.update(materialROOM)
    }

    fun addMaterial(materialROOM: MaterialROOM) = viewModelScope.launch(Dispatchers.IO) {
        repository.insert(materialROOM)
    }

    suspend fun getMaterial(id:Int) = repository.getMaterialById(id) as Material

    suspend fun getAllMaterials() = repository.allMaterials

    fun getAllMaterialsFromApi(){
        viewModelScope.launch{
            //val materials = arrayListOf(1,2,3)
            materialManager.getRangeMaterials(1,4){
                result, error ->
                when{
                    result!= null -> {
                        //result.forEach { addMaterial(Material(it.name,it.description,it.image_url.,it.id.toInt())) }
                        //addMaterial(Material(result.name,result.description,result.image_url))
                    }
                    error!=null->null
                }
            }
        }
    }
}