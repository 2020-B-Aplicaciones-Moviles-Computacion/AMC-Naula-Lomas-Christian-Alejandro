package com.example.movilescomputacion

class BaseDatosMemoria {
    companion object{
        val arregloEntenadores = arrayListOf<String>()

        fun cargaInicialDatos(){
            /*arregloEntenadores.add("Entrenador: Juan, Descripcion: Experto Natación")
            arregloEntenadores.add("Entrenador: Diana, Descripcion: Experto Atletismo")*/
            arregloEntenadores.add(BEntrenador("Juan", "Experto Natacion").toString())
            arregloEntenadores.add(BEntrenador("Diana", "Experto Atletismo").toString())
        }
    }
}