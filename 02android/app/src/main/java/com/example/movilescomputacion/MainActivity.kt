package com.example.movilescomputacion

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Parcelable
import android.util.Log
import android.widget.Button
import java.sql.Date

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
            /*intentExplicito.putExtra("nombre", "christian")
            intentExplicito.putExtra("apellido", "naula")
            intentExplicito.putExtra("edad", 31)
            Log.i("intent-explicito", "${intentExplicito.extras}")
            startActivityForResult(intentExplicito, 102)*/

            val liga = DLiga("Kanto", "Pokemon")
            val entrenador = BEntrenador("ash", "pueblo paleta", liga)
            //val entrenador = BEntrenador("ash", "pueblo paleta")
            val parametros = arrayListOf<Pair<String, *>>(
                Pair("nombre", "Christian"),
                Pair("apellido", "Naula"),
                Pair("edad", 22),
                Pair("ash", entrenador)
            )
            irActividad(CIntentExplicitoParametros::class.java, parametros, 102)
        }

        EBaseDeDatos.TablaUsuario = SqlitehelperUsuario(this)
        val usuarioEcontrado = EBaseDeDatos.TablaUsuario?.consultarUsuarioPorId(1)
        Log.i("bdd", "ID:${usuarioEcontrado?.id} Nombre:${usuarioEcontrado?.nombre} " +
                "Descripcion:${usuarioEcontrado?.descripcion} ")

        if (usuarioEcontrado?.id == 0){
            val resultado = EBaseDeDatos.TablaUsuario?.crearUsuarioFormulario("Christian", "Estudiante")
            if (resultado != null){
                Log.i("bdd", "Creado Correctamente")
            } else {
                Log.i("bdd", "Hubo Errores")
            }
        }else{
            val resultado = EBaseDeDatos.TablaUsuario
                ?.actualizarUsuarioFormulario("Vicente", "descripcion", 1)
            if (resultado != null){
                if (resultado){
                    Log.i("bdd", "Actualizado Correctamente")
                }else{
                    Log.i("bdd", "Errores")
                }
            }
        }

        val botonIrIntentConRespuesta = findViewById<Button>(
            R.id.btn_ir_intent_con_respuesta)
        botonIrIntentConRespuesta.setOnClickListener {
            irActividad(FIntentConRespuesta::class.java)
        }

        val botonIrRecyclerView = findViewById<Button>(
            R.id.btn_ir_recycler_view
        )
        botonIrRecyclerView.setOnClickListener {
            irActividad(GRecyclerView::class.java)
        }

        val botonIRHttp = findViewById<Button>(
            R.id.btn_ir_http
        )
        botonIRHttp.setOnClickListener {
            irActividad(HHttpActivity::class.java)
        }

    } // fin Oncreate


    fun irActividad(
        clase: Class<*>,
        parametros: ArrayList<Pair<String, *>>? = null,
        codigo: Int? = null
    ) {
        val intentExplicito = Intent(
            this,
            clase
        )
        if (parametros != null) {
            parametros.forEach {
                val nombreVariable = it.first
                val valorVariable = it.second

                var tipoDato = false

                tipoDato = it.second is String // instanceOf()
                if (tipoDato == true) {
                    intentExplicito.putExtra(nombreVariable, valorVariable as String)
                }

                tipoDato = it.second is Int // instanceOf()
                if (tipoDato == true) {
                    intentExplicito.putExtra(nombreVariable, valorVariable as Int)
                }

                tipoDato = it.second is Parcelable // instanceOf()
                if (tipoDato == true) {
                    intentExplicito.putExtra(nombreVariable, valorVariable as Parcelable)
                }

            }
        }
        if (codigo != null) {
            startActivityForResult(intentExplicito, codigo)
        } else {
            startActivity(intentExplicito)
        }


    }



    override fun onActivityResult(
        requestCode: Int,  // Codigo de petición  - Codigo: 102
        resultCode: Int,  //  Codigo de Resultado - RESULT_OK o RESULT_CANCELED
        data: Intent?  // Datos (opcionales)  Ej: nombre = Vicente y edad = 30
    ) {
        super.onActivityResult(requestCode, resultCode, data)
        Log.i("intent-exlicito", "${requestCode} ${resultCode} ${data}")
        when (requestCode) {
            102 -> { // 102

                if (resultCode == RESULT_OK) {  //  RESULT_OK
                    Log.i("intent-explicito", "SI actualizo los datos")

                    if (data != null) {
                        val nombre = data.getStringExtra("nombre")
                        val edad = data.getIntExtra("edad", 0)
                        Log.i("intent-explicito", "Nombre: ${nombre} Edad: ${edad}")
                    } else {
                        // AQUI ES EL OTRO BLOQUE SIN PARAMETROS DE RESPUESTA PERO OK
                    }


                } else { // RESULT_CANCELED
                    Log.i("intent-explicito", "Usuario no lleno los datos")
                }

            }
        }
    }
}

