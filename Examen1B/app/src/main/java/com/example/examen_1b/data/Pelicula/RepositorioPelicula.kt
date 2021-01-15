package com.example.examen_1b.data.Pelicula

import androidx.lifecycle.LiveData
import com.example.examen_1b.data.DirectorNuevo.DirectorDAO

class RepositorioPelicula(private var peliculaDao: PeliculaDAO){

    val readPeliculaData: LiveData<List<Pelicula>> = peliculaDao.listaPeliculas()

    fun PeliculaPorID(id: Int): List<Pelicula>{
        val readPeliId: List<Pelicula> = peliculaDao.peliculaById(id)
        return readPeliId
    }

    fun ingresarPelicula(pelicula: Pelicula){
        peliculaDao.ingresarPelicula(pelicula)
    }

    fun actualizarPelicula(pelicula: Pelicula){
        peliculaDao.actualizarPelicula(pelicula)
    }

    fun eliminarPelicula(pelicula: Pelicula){
        peliculaDao.eliminarPelicula(pelicula)
    }
}