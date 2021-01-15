package com.example.examen_1b.data.DirectorNuevo

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.example.examen_1b.data.Pelicula.Pelicula
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class DirectorViewModel(application: Application):AndroidViewModel (application) {
    val readAllData: LiveData<List<Director>>


    private val repository: RepositorioDirector
    init {
        val directorDao = DirectorDB.getDatabase(application).directorDao()
        repository = RepositorioDirector(directorDao)
        readAllData = repository.readAllData
    }

    fun ingresarDirector (director: Director){
        viewModelScope.launch(Dispatchers.IO) {
            repository.ingresarDirector(director)
        }
    }

    fun actualizarDirector (director: Director){
        viewModelScope.launch(Dispatchers.IO) {
            repository.actualizarDirector(director)
        }
    }

    fun eliminarDirector (director: Director){
        viewModelScope.launch(Dispatchers.IO) {
            repository.eliminarDirector(director)
        }
    }

}