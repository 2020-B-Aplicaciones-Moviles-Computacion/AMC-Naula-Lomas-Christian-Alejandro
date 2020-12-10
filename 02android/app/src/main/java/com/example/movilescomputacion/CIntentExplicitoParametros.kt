package com.example.movilescomputacion

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button

class CIntentExplicitoParametros : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_c_intent_explicito_parametros)

        val nombre = intent.getStringExtra("nombre")
        val apellido = intent.getStringExtra("apellido")
        val edad = intent.getIntExtra("edad", 0)

        Log.i("intent-explicito", "${nombre}")
        Log.i("intent-explicito", "${apellido}")
        Log.i("intent-explicito", "${edad}")

        if (nombre != null && apellido != null && edad != 0){
            Log.i("intent", "Nombre ${nombre + " " + apellido }" +
                "Edad: ${edad}")
        }

        val botonDevolverParametros = findViewById<Button>(R.id.btn_devolver_parametros)
        botonDevolverParametros.setOnClickListener {
            val nombreModificado = "Vicente"
            val edadModificada = 30
            val intentResponderParametros = Intent()
            intentResponderParametros.putExtra("nombre", nombreModificado)
            intentResponderParametros.putExtra("edad", edadModificada)

            this.setResult(
                RESULT_OK,
                intentResponderParametros
            )

            this.finish()
        }
    }
}