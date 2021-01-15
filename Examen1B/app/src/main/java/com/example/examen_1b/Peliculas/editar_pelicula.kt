package com.example.examen_1b.Peliculas

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import com.example.examen_1b.R
import com.example.examen_1b.data.DirectorNuevo.Director
import com.example.examen_1b.data.Pelicula.Pelicula
import kotlin.toString as toString1

class editar_pelicula : AppCompatActivity() {
    @SuppressLint("WrongViewCast")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_editar_pelicula)

        val pelicula = intent.getParcelableExtra<Pelicula>("pelicula")
        val director = intent.getParcelableExtra<Director>("director")

        val idPelicula = findViewById<TextView>(R.id.txt_IdEditPeli)
        idPelicula.text = pelicula?.idPelicula?.toString()

        val nombrePEli = findViewById<TextView>(R.id.etxt_EditnombrePelicula)
        nombrePEli.text = pelicula?.nombre

        val generoPelicula = findViewById<TextView>(R.id.etxt_Editgenero_Pelicula)
        generoPelicula.text = pelicula?.genero

        val duracionPelicula = findViewById<TextView>(R.id.etxt_Editduracion)
        duracionPelicula.text =  pelicula?.duracion?.toString()

        val añoPelicula = findViewById<TextView>(R.id.etxt_EditAno)
        añoPelicula.text = pelicula?.año?.toString()

        val btnAcpetar = findViewById<Button>(R.id.btn_AceptarEditPeli)
            .setOnClickListener {
                val peliculaEditada = Pelicula(
                    idPelicula.text.toString().toInt(),
                    nombrePEli.text.toString(),
                    generoPelicula.text.toString(),
                    duracionPelicula.text.toString().toInt(),
                    añoPelicula.text.toString().toInt(),
                    director?.idDirector
                )
                val intentConRespuesta = Intent()
                intentConRespuesta.putExtra("result", peliculaEditada)
                setResult(RESULT_OK, intentConRespuesta)
                this.finish()
            }

        val btnCancelar = findViewById<Button>(R.id.btn_CancelarEditPeli)
            .setOnClickListener{
                setResult(RESULT_OK)
                this.finish()
            }
    }
}