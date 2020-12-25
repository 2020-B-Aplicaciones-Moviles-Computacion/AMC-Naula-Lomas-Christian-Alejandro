package com.example.movilescomputacion

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class GRecyclerView : AppCompatActivity() {

    var totallikes = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_g_recycler_view)

        val listaEntrenador = arrayListOf<BEntrenador>()
        val ligaPokemon = DLiga("Kanto", "Liga Kanto")
        listaEntrenador.add(
            BEntrenador("Alejandro", "1724723679", ligaPokemon)
        )
        listaEntrenador.add(
            BEntrenador("Alex", "1713230280", ligaPokemon)
        )

        val recyclerViewEntrenador = findViewById<RecyclerView>(
            R.id.rv_entrenadores
        )
        this.iniciarRecyclerView(
            listaEntrenador,
            this,
            recyclerViewEntrenador
        )
    }

    fun iniciarRecyclerView(
        lista: List<BEntrenador>,
        actividad: GRecyclerView,
        recyclerView: androidx.recyclerview.widget.RecyclerView
    ) {
        val adaptador = FRecyclerViewAdaptadorNombreCedula(
            lista,
            actividad,
            recyclerView
        )
        recyclerView.adapter = adaptador
        recyclerView.itemAnimator = androidx.recyclerview.widget.DefaultItemAnimator()
        recyclerView.layoutManager = androidx.recyclerview.widget.LinearLayoutManager(actividad)
        adaptador.notifyDataSetChanged()
    }

    fun aumentartotallikes(){
        totallikes = this.totallikes + 1
        val textView = findViewById<TextView>(
            R.id.yv_total_likes
        )
        textView.text = totallikes.toString()
    }


}