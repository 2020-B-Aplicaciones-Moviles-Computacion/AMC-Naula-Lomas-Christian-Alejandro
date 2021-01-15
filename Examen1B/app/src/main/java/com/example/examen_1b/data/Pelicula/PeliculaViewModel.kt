package com.example.examen_1b.data.Pelicula

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.examen_1b.data.DirectorNuevo.DirectorDB
import com.example.examen_1b.data.DirectorNuevo.RepositorioDirector
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class PeliculaViewModel(application: Application): AndroidViewModel(application) {
    val readPeliculaData: LiveData<List<Pelicula>>

    private val repository: RepositorioPelicula
    //val returnedVal = MutableLiveData<LiveData<List<Pelicula>>>()

    init {
        val peliculaDao = DirectorDB.getDatabase(application).peliculaDao()
        repository = RepositorioPelicula(peliculaDao)
        readPeliculaData = repository.readPeliculaData
    }


    fun PeliculaPorID (id: Int): MutableLiveData<List<Pelicula>> {
        val result = MutableLiveData<List<Pelicula>>()
        viewModelScope.launch(Dispatchers.IO) {
            val lista = repository.PeliculaPorID(id)
            result.postValue(lista)
        }
        return result
    }

    fun ingresarPelicula (pelicula: Pelicula){
        viewModelScope.launch(Dispatchers.IO) {
            repository.ingresarPelicula(pelicula)
        }
    }

    fun actualizarPelicula (pelicula: Pelicula){
        viewModelScope.launch(Dispatchers.IO) {
            repository.actualizarPelicula(pelicula)
        }
    }
    fun eliminarPelicula (pelicula: Pelicula){
        viewModelScope.launch(Dispatchers.IO) {
            repository.eliminarPelicula(pelicula)
        }
    }
}