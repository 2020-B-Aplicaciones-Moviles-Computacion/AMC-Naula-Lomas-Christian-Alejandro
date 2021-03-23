package com.example.proyecto_2b

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Parcelable
import android.util.Log
import android.widget.ArrayAdapter
import android.widget.ListView
import android.widget.TextView

class pantalla_Principal : AppCompatActivity() {

    //var idUsuario: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pantalla__principal)

        var idUsuario = intent.getStringExtra("idUsuario").toString()

        var todasLasCanciones = findViewById<TextView>(R.id.tv_todas_canciones)
            .setOnClickListener {
                var parametros = arrayListOf<Pair<String, *>>(
                        Pair("lista", ""),
                        Pair("vengo", "PP"),
                        Pair("idUsuario", idUsuario),
                        Pair("nombre", "Todas las Canciones")
                )
                irActividad(todas_canciones::class.java, parametros)
            }

        //Log.i("idUsuario", "${idUsuario}")

        var listasReproduccion = findViewById<TextView>(R.id.tv_lista_reproducción)
                .setOnClickListener {
                    var parametros2 = arrayListOf<Pair<String, *>>(
                            Pair("idUsuario", idUsuario)
                    )

                    irActividad(Listas_Reproduccion::class.java, parametros2)
                }

        var favorito= findViewById<TextView>(R.id.tv_lista_favoritos)
                .setOnClickListener {
                    var parametros = arrayListOf<Pair<String, *>>(
                            Pair("lista", ""),
                            Pair("vengo", "favoritos"),
                            Pair("idUsuario", idUsuario),
                            Pair("nombre", "Favoritos")
                    )
                    irActividad(todas_canciones::class.java, parametros)
                }

        var artistas = findViewById<TextView>(R.id.tv_artistas)
            .setOnClickListener {
                irActividad(Artistas::class.java)
            }

    }

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

}