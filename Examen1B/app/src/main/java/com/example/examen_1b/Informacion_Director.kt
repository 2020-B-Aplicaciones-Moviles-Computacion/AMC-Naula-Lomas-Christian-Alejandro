package com.example.examen_1b

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView
import com.example.examen_1b.data.DirectorNuevo.Director

class Informacion_Director : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_informacion__director)

        val director = intent.getParcelableExtra<Director>("director")

        //Log.i("PRUEBA", "INFOR INTENT ${director}")

        val idDirector = findViewById<TextView>(R.id.txtinfo_ID)
        idDirector.text = director?.idDirector.toString()

        val nombreDirector = findViewById<TextView>(R.id.txtinfo_Nombre)
        nombreDirector.text = director?.nombre.toString()

        val apellidoDirector = findViewById<TextView>(R.id.txtinfo_Apellido)
        apellidoDirector.text = director?.apellido.toString()

        val nacionalidadDirector = findViewById<TextView>(R.id.txtinfo_Nacionalidad)
        nacionalidadDirector.text = "${director?.nacionalidad.toString()}"

        val edadDirector = findViewById<TextView>(R.id.txtinfo_edad)
        edadDirector.text = "${director?.edad.toString()}"

        val correoDirector = findViewById<TextView>(R.id.txtinfo_correo)
        correoDirector.text = director?.correo.toString()

        val botonAceptar = findViewById<Button>(R.id.btninfo_aceptar)
            .setOnClickListener{
                this.setResult(RESULT_OK)
                this.finish()
            }
    }
}