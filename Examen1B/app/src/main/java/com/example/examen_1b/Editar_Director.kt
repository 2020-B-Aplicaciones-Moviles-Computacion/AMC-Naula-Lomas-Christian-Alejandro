package com.example.examen_1b

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import com.example.examen_1b.data.DirectorNuevo.Director

class Editar_Director : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_editar__director)

        val director = intent.getParcelableExtra<Director>("director")

        val idDirector = findViewById<TextView>(R.id.txt_ID)
        idDirector.text = director?.idDirector.toString()
        val idDirectorEntero:Int = (idDirector.text as String).toInt()

        val nombreDirector = findViewById<TextView>(R.id.txtedit_Nombre)
        nombreDirector.text = director?.nombre.toString()

        val apellidoDirector = findViewById<TextView>(R.id.txtedit_Apellido)
        apellidoDirector.text = director?.apellido.toString()

        val nacionalidadDirector = findViewById<TextView>(R.id.txtedit_Nacionalidad)
        nacionalidadDirector.text = director?.nacionalidad.toString()

        val edadDirector = findViewById<TextView>(R.id.txtedit_edad)
        edadDirector.text = director?.edad.toString()

        val correoDirector = findViewById<TextView>(R.id.txtedir_correo)
        correoDirector.text = director?.correo.toString()



        val botonAceptar = findViewById<Button>(R.id.btnedit_aceptar)
            .setOnClickListener{
                val directorEditado = Director(idDirector.text.toString().toInt(),
                    nombreDirector.text.toString(),
                    apellidoDirector.text.toString(),
                    nacionalidadDirector.text.toString(),
                    edadDirector.text.toString().toInt(),
                    correoDirector.text.toString())

                val intentConRespuesta = Intent()
                intentConRespuesta.putExtra("result", directorEditado)
                setResult(RESULT_OK, intentConRespuesta)
                //Log.i("PRUEBA", "Intent: ${directorEditado.toString()}")
                this.finish()
            }

        val botonCancelar = findViewById<Button>(R.id.btn_cancelar)
            .setOnClickListener{
                this.setResult(RESULT_OK)
                //nombre.setText("Nuevo")
                this.finish()
            }

    }

}