package com.example.examen_1b.data.DirectorNuevo

import androidx.lifecycle.LiveData
import com.example.examen_1b.data.Pelicula.Pelicula
import com.example.examen_1b.data.Pelicula.PeliculaDAO

class RepositorioDirector(private var directorDao: DirectorDAO) {
    //DIRECTOR
    val readAllData: LiveData<List<Director>> = directorDao.listaDirectores()

    fun ingresarDirector(director: Director){
        directorDao.ingresarDirector(director)
    }

    fun actualizarDirector(director: Director){
        directorDao.actualizarDirector(director)
    }

    fun eliminarDirector(director: Director){
        directorDao.eliminarDirecto(director)
    }

    //PELICULA
    /*val readPeliculaData: LiveData<List<Pelicula>> = peliculaDao.listaPeliculas()

    fun ingresarPelicula(pelicula: Pelicula){
        peliculaDao.ingresarPelicula(pelicula)
    }

    fun actualizarPelicula(pelicula: Pelicula){
        peliculaDao.actualizarPelicula(pelicula)
    }

    fun eliminarPelicula(pelicula: Pelicula){
        peliculaDao.eliminarPelicula(pelicula)
    }*/
}