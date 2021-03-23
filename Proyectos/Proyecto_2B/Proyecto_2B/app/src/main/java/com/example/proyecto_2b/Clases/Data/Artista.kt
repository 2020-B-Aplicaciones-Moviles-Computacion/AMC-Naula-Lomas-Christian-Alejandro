package com.example.proyecto_2b.Clases.Data

data class Artista(
    var id: String,
    var nombre: String,
    var pais: String,
    var coordenadas: ArrayList<Double>
    ){

    override fun toString(): String {
        return "${nombre}"
    }
}

