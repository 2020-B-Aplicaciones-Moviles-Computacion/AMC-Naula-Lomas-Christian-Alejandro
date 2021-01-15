package com.example.examen_1b.Peliculas

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import com.example.examen_1b.R
import com.example.examen_1b.data.DirectorNuevo.Director
import com.example.examen_1b.data.Pelicula.Pelicula

class Crear_Peliculas : AppCompatActivity() {
    @SuppressLint("WrongViewCast")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_crear__peliculas)

        //val director = intent.getParcelableExtra<Director>("director")

        val nombrePeli = findViewById<EditText>(R.id.etxt_nombrePelicula)
        val generoPeli = findViewById<EditText>(R.id.etxt_genero_Pelicula)
        val duracionPeli = findViewById<EditText>(R.id.etxt_duracion)
        val anoPeli = findViewById<EditText>(R.id.etxt_ano)


        val btnAceptar = findViewById<Button>(R.id.btn_crearPeli)
            .setOnClickListener{
                val nuevaPelicula = Pelicula(
                    0,
                nombrePeli.text.toString(),
                generoPeli.text.toString(),
                duracionPeli.text.toString().toInt(),
                anoPeli.text.toString().toInt(),
                0)

                Log.i("PRUEBAPELICULA","${nuevaPelicula.toString()}")

                val intentConRespuesta = Intent()
                intentConRespuesta.putExtra("result", nuevaPelicula)
                setResult(RESULT_OK, intentConRespuesta)
                //Log.i("PRUEBA", "Intent: ${Nuevodirector.toString()}")
                this.finish()
            }

        val btnCancelar = findViewById<Button>(R.id.btn_cancelarCrearPeli)
            .setOnClickListener{
                setResult(RESULT_OK)
                this.finish()
            }
    }


}