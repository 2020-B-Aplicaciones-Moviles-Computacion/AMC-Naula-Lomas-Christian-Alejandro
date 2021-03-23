package com.example.reproductor_musica.Adaptadores

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.reproductor_musica.R

class BibliotecaAdapter (var biblioteca: List<Cancion>): RecyclerView.Adapter<BibliotecaAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return ViewHolder(layoutInflater.inflate(R.layout.listado_biblioteca, parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.render(biblioteca[position])
    }

    override fun getItemCount(): Int {
        return biblioteca.size
    }

    class ViewHolder(var view: View): RecyclerView.ViewHolder(view) {
        fun render(biblioteca: Cancion){
            val nombre = view.findViewById<TextView>(R.id.tv_nombre)
            nombre.text = biblioteca.nombre
            val artista = view.findViewById<TextView>(R.id.tv_artista)
            artista.text = biblioteca.artista
        }
    }
}