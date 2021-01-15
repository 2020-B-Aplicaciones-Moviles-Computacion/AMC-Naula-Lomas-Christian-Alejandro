package com.example.examen_1b.data.Pelicula

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.examen_1b.data.DirectorNuevo.Director

@Dao
interface PeliculaDAO {
    @Query("SELECT * FROM Pelicula ORDER BY idDirector asc")
    fun listaPeliculas(): LiveData<List<Pelicula>>

    @Query("SELECT * FROM Pelicula where idDirector like :id")
    fun peliculaById(id: Int): List<Pelicula>

    @Update
    fun actualizarPelicula(pelicula: Pelicula)

    @Insert
    fun ingresarPelicula(pelicula: Pelicula?)

    @Delete
    fun eliminarPelicula(pelicula: Pelicula)
}