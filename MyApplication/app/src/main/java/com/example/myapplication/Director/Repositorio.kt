package com.example.myapplication.Director

import androidx.lifecycle.LiveData

class Repositorio (private var directorDao: DirectorDAO) {
    val readAllData: LiveData<List<Director>> = directorDao.listaDirectores()

    fun ingresarDirector(director: Director){
        directorDao.ingresarDirector(director)
    }
}