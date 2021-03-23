package com.example.reproductor_musica

import android.content.Intent
import android.graphics.drawable.LayerDrawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Parcelable
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.ListView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.reproductor_musica.Adaptadores.BibliotecaAdapter
import com.example.reproductor_musica.Adaptadores.Cancion

var listadoCanciones : List<Cancion> = listOf(
    Cancion("chill", "de Christian"),
    Cancion("POP", "de Christian"),
    Cancion("HIP-HOP", "de Christian"),
    Cancion("REGGAE", "de Christian"),
    Cancion("RAP", "de Christian"),

    Cancion("chill", "de Christian"),
    Cancion("POP", "de Christian"),
    Cancion("HIP-HOP", "de Christian"),
    Cancion("REGGAE", "de Christian"),
    Cancion("RAP", "de Christian"),

)

class Biblioteca : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_biblioteca)

        initRV()

        var btnPrincipal = findViewById<ImageView>(R.id.iv_inicio)
            .setOnClickListener {
                irActividad(MainActivity::class.java)
            }

        var btnBuscar = findViewById<ImageView>(R.id.iv_buscar)
            .setOnClickListener {
                irActividad(Buscar::class.java)
            }


    }

    fun initRV(){
        var rcview = findViewById<RecyclerView>(R.id.rv_listado)
        rcview.layoutManager = LinearLayoutManager(this)
        val adapter = BibliotecaAdapter(listadoCanciones)
        rcview.adapter = adapter
    }

    /*fun llenarListado(){
        val adaptador = ArrayAdapter(
            this,
            android.R.layout.simple_list_item_1,
            listadoCanciones
        )

        var listView = findViewById<ListView>(R.id.lv_todas_canciones)

        listView.adapter = adaptador
        adaptador.notifyDataSetChanged()
        registerForContextMenu(listView)
    }*/


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