package com.example.movilescomputacion

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class FRecyclerViewAdaptadorNombreCedula(
    private val listaEntrenadores: List<BEntrenador>,
    private val contexto: GRecyclerView,
    private val recyclerView: RecyclerView
    ): androidx.recyclerview.widget.RecyclerView.Adapter<
        FRecyclerViewAdaptadorNombreCedula.MyViewHolder>()
{
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): FRecyclerViewAdaptadorNombreCedula.MyViewHolder {
        val itemView = LayoutInflater
            .from(parent.context)
            .inflate(
                R.layout.recycler_view_vista,
                parent,
                false
            )
        return MyViewHolder(itemView)
    }

    override fun onBindViewHolder(
        holder: FRecyclerViewAdaptadorNombreCedula.MyViewHolder,
        position: Int
    ) {
        val entrenador = listaEntrenadores[position]
        holder.nombreTextView.text = entrenador.nombre
        holder.cedulaTextView.text = entrenador.descripcion
        holder.accionButton.text = "Like ${entrenador.nombre}"
        holder.likesTextView.text = "0"
    }

    override fun getItemCount(): Int {
       return listaEntrenadores.size
    }

    inner class MyViewHolder(view: View): androidx.recyclerview.widget.RecyclerView.ViewHolder(view) {
        val nombreTextView: TextView
        val cedulaTextView: TextView
        val likesTextView: TextView
        val accionButton: Button
        var numerolikes = 0
        init{
            nombreTextView = view.findViewById(R.id.tv_nombre)
            cedulaTextView = view.findViewById(R.id.tv_cedula)
            likesTextView = view.findViewById(R.id.tv_likes)
            accionButton = view.findViewById(R.id.btn_dar_like)
            accionButton.setOnClickListener {
                this.anadirlike()
            }
        }
        fun anadirlike(){
            this.numerolikes = this.numerolikes + 1
            likesTextView.text = this.numerolikes.toString()
            contexto.aumentartotallikes()
        }
    }
}