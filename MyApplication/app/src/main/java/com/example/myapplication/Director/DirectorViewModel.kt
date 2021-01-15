package com.example.myapplication.Director

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.myapplication.data.User
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class DirectorViewModel(application: Application): AndroidViewModel(application)  {
    val readAllData: LiveData<List<Director>>
    private val repository: Repositorio
    init {
        val directorDao = DirectorDB.getDatabase(application).directorDao()
        repository = Repositorio(directorDao)
        readAllData = repository.readAllData
    }

    fun ingresarDirector (user: Director){
        viewModelScope.launch(Dispatchers.IO) {
            repository.ingresarDirector(user)
        }
    }
}