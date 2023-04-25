package com.tabletop.tabletopapplication.presentationlayer.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.tabletop.tabletopapplication.businesslayer.managers.MaterialManager
import com.tabletop.tabletopapplication.presentationlayer.models.LoadState
import com.tabletop.tabletopapplication.presentationlayer.models.Material.Material
import com.tabletop.tabletopapplication.presentationlayer.models.Material.MaterialRepository
import com.tabletop.tabletopapplication.presentationlayer.models.game.GameDatabase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MaterialViewModel(application: Application) : AndroidViewModel(application) {

    private val repository : MaterialRepository
    private val materialManager by lazy{ MaterialManager() }
    val state = MutableLiveData<LoadState>()

    init {
        val dao = GameDatabase.getDatabase(application).getGameDao()
        repository = MaterialRepository(dao)
    }

    fun deleteMaterial (material: Material) = viewModelScope.launch(Dispatchers.IO) {
        repository.delete(material)
    }

    fun updateMaterial(material:Material) = viewModelScope.launch(Dispatchers.IO) {
        repository.update(material)
    }

    fun addMaterial(material:Material) = viewModelScope.launch(Dispatchers.IO) {
        repository.insert(material)
    }

    fun getMaterial(id:Long) = repository.getOneMaterial(id)

    suspend fun getAllMaterials() = repository.allMaterials

    fun getAllMaterialsFromApi(){
        viewModelScope.launch{
            //val materials = arrayListOf(1,2,3)
            materialManager.getRangeMaterials(1,4){
                result, error ->
                when{
                    result!= null -> {
                        //result.forEach { addMaterial(Material(it.name,it.description,it.image_url.,it.id.toLong())) }
                        //addMaterial(Material(result.name,result.description,result.image_url))
                    }
                    error!=null->null
                }
            }
        }
    }
}