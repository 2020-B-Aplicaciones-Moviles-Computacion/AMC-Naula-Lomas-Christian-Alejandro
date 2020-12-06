package com.example.movilescomputacion

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.ListView

class BListView : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_b_list_view)

        BaseDatosMemoria.cargaInicialDatos()

        //BEntrenador.cargaInicialDatos()


        val adaptador = ArrayAdapter(
            this, // contexto
            android.R.layout.simple_list_item_1,  // layput (xml visula) existente en Android
            //BaseDatosMemoria.arregloEnteros //arreglo
            BaseDatosMemoria.arregloEntenadores
        )

        val listView = findViewById<ListView>(R.id.lv_entrenador)

        listView.adapter = adaptador

        adaptador.notifyDataSetChanged()

        val botonAñadirLV = findViewById<Button>(R.id.btn_anadir_item_lv)
            botonAñadirLV.setOnClickListener {
                añadirListView(adaptador,
                    "Fernando",
                    "Experto en Bascketball",
                    BaseDatosMemoria.arregloEntenadores)
            }

    }

    fun añadirListView(
        adaptador: ArrayAdapter<String>,
        item: String,
        descripcion: String,
        arreglo: ArrayList<String>
    ){
        arreglo.add(BEntrenador(item, descripcion).toString())
        //arreglo.add(descripcion)
        adaptador.notifyDataSetChanged()
    }

}