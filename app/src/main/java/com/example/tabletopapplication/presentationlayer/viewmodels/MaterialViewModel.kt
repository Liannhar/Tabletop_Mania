package com.example.tabletopapplication.presentationlayer.viewmodels

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tabletopapplication.businesslayer.managers.MaterialManager
import com.example.tabletopapplication.presentationlayer.models.LoadState
import com.example.tabletopapplication.presentationlayer.models.Material.Material
import com.example.tabletopapplication.presentationlayer.models.Material.MaterialDatabase
import com.example.tabletopapplication.presentationlayer.models.Material.MaterialRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MaterialViewModel(application: Application) : AndroidViewModel(application) {

    val repository : MaterialRepository
    val materialManager by lazy{ MaterialManager() }
    val state = MutableLiveData<LoadState>()

    init {
        val dao = MaterialDatabase.getDatabase(application).getMaterialDao()
        repository = MaterialRepository(dao)
    }

    fun deleteMaterial (material:Material) = viewModelScope.launch(Dispatchers.IO) {
        repository.delete(material)
    }

    fun updateMaterial(material:Material) = viewModelScope.launch(Dispatchers.IO) {
        repository.update(material)
    }

    fun addMaterial(material:Material) = viewModelScope.launch(Dispatchers.IO) {
        repository.insert(material)
    }

    fun getMaterial(id:Int) = repository.getOneMaterial(id)

    fun getAllMaterials() = repository.allMaterials

    fun getAllMaterialsFromApi(){
        viewModelScope.launch{
            //val materials = arrayListOf(1,2,3)
            materialManager.getRangeMaterials(1,3){
                result, error ->
                when{
                    result!= null -> {
                        result.forEach { addMaterial(Material(it.name,it.description,it.image_url)) }
                        //addMaterial(Material(result.name,result.description,result.image_url))
                    }
                    error!=null->null
                }
            }
        }
    }
}