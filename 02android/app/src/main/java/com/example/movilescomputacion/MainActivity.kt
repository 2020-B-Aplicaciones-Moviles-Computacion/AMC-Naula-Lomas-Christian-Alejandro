package com.example.movilescomputacion

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
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

            val intentExplicito = Intent(
                this,
                CIntentExplicitoParametros::class.java
            )
            intentExplicito.putExtra("nombre", "christian")
            intentExplicito.putExtra("apellido", "naula")
            intentExplicito.putExtra("edad", 31)
            Log.i("intent-explicito", "${intentExplicito.extras}")
            startActivityForResult(intentExplicito, 102)

            /*val parametros = arrayListOf<Pair<String, *>>(
                Pair("nombre", "Christian"),
                Pair("apellido", "Naula"),
                Pair("edad", "22")
            )
            //irListView()
            irActividad(CIntentExplicitoParametros::class.java, parametros)*/
        }
    }

    fun irActividad(
        clase: Class<*>,
        parametros: ArrayList<Pair<String, *>>? = null
    ){
        val intentExplicito = Intent(
            this,
            clase
        )
        Log.i("intent-explicito", "${parametros}")
        if (parametros != null){
            parametros.forEach{
                val nombreVariable = it.first
                val valorVariable = it.second is Any
                intentExplicito.putExtra(nombreVariable, valorVariable)
            }
        }
        startActivity(intentExplicito)
    }


    override fun onActivityResult(
        requestCode: Int, //codigo de peticion - Codigo: 102
        resultCode: Int,  //codigo de reusltado - RESULT_OK o RESULT CANCELED
        data: Intent? // Datos opcionados Ej: nombre = Vicente y edad = 30
    ) {
        super.onActivityResult(requestCode, resultCode, data)
        when (requestCode) {
            102 -> {
                if(resultCode == RESULT_OK){
                    Log.i("intent-explicito", "Si actualizó los datos")
                    if (data != null) {
                        val nombre = data.getStringExtra("nombre")
                        val edad = data.getIntExtra("edad", 0)
                        Log.i("intent-explicito", "Nombre: ${nombre} Edad: ${edad}")
                    }
                }else{
                    Log.i("intent-explicito", "Usario no lleno los datos")
                }
            }
        }
    }

}

