package com.example.myapplication.Director

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Director(
@PrimaryKey(autoGenerate = true)
val idDirector: Int?,
var nombre: String?,
var apellido: String?,
var nacionalidad: String?,
var edad: Int?,
var correo: String?
){
    override fun toString(): String {
        return "${nombre} ${apellido}"
    }
}
