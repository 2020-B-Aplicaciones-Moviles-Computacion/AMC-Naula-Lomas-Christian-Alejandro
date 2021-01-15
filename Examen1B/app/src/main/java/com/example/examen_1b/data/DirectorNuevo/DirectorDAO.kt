package com.example.examen_1b.data.DirectorNuevo

import androidx.lifecycle.LiveData
import androidx.room.*


@Dao
interface DirectorDAO {
    @Query("SELECT * FROM Director ORDER BY idDirector asc")
    fun listaDirectores(): LiveData<List<Director>>

    @Query("SELECT * FROM Director where idDirector = :id")
    fun directorById(id: Int): Director

    @Update
    fun actualizarDirector(director: Director)

    @Insert
    fun ingresarDirector(director: Director?)

    @Delete
    fun eliminarDirecto(director: Director)
}