package com.example.examen_1b

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import com.example.examen_1b.data.DirectorNuevo.Director


class Crear_Director : AppCompatActivity() {

    @SuppressLint("WrongViewCast")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_crear__director)

        val nombreDirector = findViewById<EditText>(R.id.txtcrear_Nombre)
        val apellidoDirector = findViewById<EditText>(R.id.txtcrear_Apellido)
        val nacionalidadDirector = findViewById<EditText>(R.id.txtcrear_Nacionalidad)
        val edadDirector = findViewById<EditText>(R.id.txtcrear_edad)
        val correoDirector = findViewById<EditText>(R.id.txtcrear_correo)

        val buttonAceptar = findViewById<Button?>(R.id.btncrear_aceptar)
            .setOnClickListener {
                val Nuevodirector= Director(null ,
                    nombreDirector.text.toString(),
                    apellidoDirector.text.toString(),
                    nacionalidadDirector.text.toString(),
                    edadDirector.text.toString().toInt(),
                    correoDirector.text.toString())

                val intentConRespuesta = Intent()
                intentConRespuesta.putExtra("result", Nuevodirector)
                setResult(RESULT_OK, intentConRespuesta)
                Log.i("PRUEBA", "Intent: ${Nuevodirector.toString()}")
                this.finish()
        }

        val buttonCancelar = findViewById<Button>(R.id.btncrear_cancelar)
            .setOnClickListener{
                this.setResult(RESULT_OK)
                this.finish()
            }

    }

}