package com.example.movilescomputacion

class BaseDatosMemoria {
    companion object{
        val arregloEntenadores = arrayListOf<String>()

        fun cargaInicialDatos(){
            arregloEntenadores.add(BEntrenador("Juan", "ornitologo", null).toString())
            arregloEntenadores.add(BEntrenador("Diana", "montañero", null).toString())
        }
    }
}