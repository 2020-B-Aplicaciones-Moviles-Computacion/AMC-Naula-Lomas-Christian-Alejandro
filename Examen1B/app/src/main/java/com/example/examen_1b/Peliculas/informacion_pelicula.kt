package com.example.examen_1b.Peliculas

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import com.example.examen_1b.R
import com.example.examen_1b.data.DirectorNuevo.Director
import com.example.examen_1b.data.Pelicula.Pelicula

class informacion_pelicula : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_informacion_pelicula)

        val pelicula = intent.getParcelableExtra<Pelicula>("pelicula")
        val director = intent.getParcelableExtra<Director>("director")

        val idPelicula = findViewById<TextView>(R.id.txt_IdEditPeli)
        idPelicula.text = pelicula?.idPelicula?.toString()

        val nombrePelicula = findViewById<TextView>(R.id.etxt_InfonombrePelicula)
        nombrePelicula.text = pelicula?.nombre

        val generoPelicula = findViewById<TextView>(R.id.etxt_Infogenero_Pelicula)
        generoPelicula.text = pelicula?.genero

        val duracionPelicula = findViewById<TextView>(R.id.etxt_Infoduracion)
        duracionPelicula.text =  pelicula?.duracion.toString()

        val añoPelicula = findViewById<TextView>(R.id.etxt_InfoAno)
        añoPelicula.text = pelicula?.año.toString()

        val directorPelicula =  findViewById<TextView>(R.id.etxt_InfoDirector)
        directorPelicula.text = director.toString()

        val btnRegresar = findViewById<Button>(R.id.btn_RegresarInfoPeli)
            .setOnClickListener {
                this.setResult(RESULT_OK)
                this.finish()
            }

    }
}