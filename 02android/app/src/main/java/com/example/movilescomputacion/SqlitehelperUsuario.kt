package com.example.movilescomputacion

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log
import androidx.core.content.contentValuesOf

class SqlitehelperUsuario (contexto: Context?
): SQLiteOpenHelper(contexto,
                    "moviles",
                    null,
                    1) {
    override fun onCreate(db: SQLiteDatabase?) {
        val scriptCrearTablaUsuario =
            """
                CREATE TABLE USUARIO (
                    id INTEGER PRIMARY KEY AUTOINCREMENT,
                    nombre VARCHAR (50),
                    descripcion VARCHAR (50)
                )
            """.trimIndent()
        Log.i("bbd", "creando la tabla de usuario")
        db?.execSQL(scriptCrearTablaUsuario)
    }

    override fun onUpgrade(p0: SQLiteDatabase?, p1: Int, p2: Int) {
        TODO("Not yet implemented")
    }

    fun consultarUsuarioPorId(id:Int): EUsuarioBDD{
        val scriptConsultarUsuario =
            "SELECT * FROM USUARIO WHERE id=${id}"
        val dbReadable = readableDatabase
        val resultado = dbReadable.rawQuery(scriptConsultarUsuario, null)


        val existeUsuario = resultado.moveToFirst() //devuelve un iterable

        val usuarioEncontrado = EUsuarioBDD(0, "","")

        if (existeUsuario){
            do {
                val id = resultado.getInt(0) //columna indice 0 -> ID
                val nombre =resultado.getString(1) //columna indice 1 -> NOMBRE
                val descripcion = resultado.getString(2) //columna indice 2 -> DESCRIPCION
                if (id != null){
                    usuarioEncontrado.id = id
                    usuarioEncontrado.nombre = nombre
                    usuarioEncontrado.descripcion = descripcion
                }
            }while (resultado.moveToNext())
        }
        resultado.close()
        dbReadable.close()
        return usuarioEncontrado
    }


    fun crearUsuarioFormulario(nombre: String,
                                descripcion: String
    ): Boolean {
        val conexionEscritura = writableDatabase
        val valoresAGuardar = ContentValues()
        valoresAGuardar.put("nombre", nombre)
        valoresAGuardar.put("descripcion", descripcion)

        val resultadoEscritura: Long = conexionEscritura
            .insert("USUARIO",
            null,
            valoresAGuardar)

        conexionEscritura.close()
        return if (resultadoEscritura.toInt() == -1) false else true
    }


    fun actualizarUsuarioFormulario(nombre: String, descripcion: String, idActualizar: Int) : Boolean{

        val conexionEscritura = writableDatabase
        val valoresAActualizar = ContentValues()
        valoresAActualizar.put("nombre", nombre)
        valoresAActualizar.put("descripcion", descripcion)
        val resultadoActualizacion = conexionEscritura
            .update("USUARIO",  //nombre tabla
                valoresAActualizar,  //valore a actualizar
                "id=?",  // clausula where
                arrayOf(idActualizar.toString())) //parametros clausula where.

        conexionEscritura.close()
        return if (resultadoActualizacion.toInt() == -1) false else true
    }

    fun eliminarUsuarioFormulario(id: Int): Boolean{

        val conexionEscritura = writableDatabase
        val resultadoElminacion = conexionEscritura
            .delete("USUARIO", //nombre tabla
            "id=?", //valore a actualizar
            arrayOf(id.toString())) //parametros clausula where.

        conexionEscritura.close()
        return if (resultadoElminacion.toInt() == -1) false else true
    }
}