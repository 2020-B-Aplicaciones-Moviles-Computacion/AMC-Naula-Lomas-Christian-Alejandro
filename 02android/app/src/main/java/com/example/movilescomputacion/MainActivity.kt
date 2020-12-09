package com.example.movilescomputacion

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val buttonCicloVida = findViewById<Button>(R.id.button_ir_ciclo_vida)
        buttonCicloVida.setOnClickListener {
            //irCicloVida()
            irActividad(ACicloVida::class.java)
        }

        val buttonListView = findViewById<Button>(R.id.bnt_ir_list_view)
        buttonListView.setOnClickListener {
            //irListView()
            irActividad(BListView::class.java)
        }

        val buttonIntentExplicitoParameters = findViewById<Button>(R.id.btn_ir_intent_explicito_parametros)
        buttonIntentExplicitoParameters.setOnClickListener {
            val parametros = arrayListOf<ArrayList<*>>(
                arrayListOf("nombre", "Christian"),
                arrayListOf("apeliido", "Naula"),
                arrayListOf("edad", "22")
            )
            //irListView()
            irActividad(CIntentExplicitoParametros::class.java)
        }
    }

    fun irActividad(
        clase: Class<*>,
        parametros: ArrayList<ArrayList<*>>?
    ){
        val intentExplicito = Intent(
            this,
            clase
        )
        intentExplicito.putExtra("nombre", "Christian")
        intentExplicito.putExtra("apellido", "Naula")
        intentExplicito.putExtra("edad", "22")
        startActivity(intentExplicito)
    }
}

//btn_ir_intent_explicito_parametros