package com.example.firebase_ejemplo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.*
import com.example.firebase_ejemplo.dto.FirestoreRestauranteDto
import com.example.firebase_ejemplo.dto.FirestoreUsuarioDto
import com.example.firebase_ejemplo.dto.FirestoreUsuarioOrdenDto
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.type.Date
import java.sql.Timestamp

class COrdenes : AppCompatActivity() {

    val arregloRestaurantes = arrayListOf<FirestoreRestauranteDto>()
    var adaptadorRestaurantes: ArrayAdapter<Any>? = null

    var restauranteSeleccionado: FirestoreRestauranteDto? =  null

    val arregloTiposComida = arrayListOf<String>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_c_ordenes)
        if (adaptadorRestaurantes == null){
            adaptadorRestaurantes = ArrayAdapter(
                    this,
                    android.R.layout.simple_spinner_item,
                    arregloRestaurantes as List<FirestoreRestauranteDto>
            )
            adaptadorRestaurantes?.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            cargarRestaurantes()
        }

        val botonAnadirTipoComida = findViewById<Button>(R.id.btn_añadir_tipo_comida)
                .setOnClickListener {
                    agregarTipoComida()
                }

        val textViewTipoComida = findViewById<TextView>(R.id.tv_arreglo_tipo_comida)
        textViewTipoComida.setText("")

        val botonAnadirOrden = findViewById<Button>(R.id.btn_crear_orden)
                .setOnClickListener {
                    crearOrden()
                }


    }

    fun cargarRestaurantes(){
        val spinnerRestaurantes = findViewById<Spinner>(R.id.sp_restaurantes)
        spinnerRestaurantes.adapter = adaptadorRestaurantes
        spinnerRestaurantes.onItemSelectedListener = object : AdapterView.OnItemSelectedListener
        {
            override fun onItemSelected(
                    parent: AdapterView<*>?,
                    view: View?,
                    position: Int,
                    id: Long
            ){
                Log.i("firebase-firestore", "${position}, ${id}")
                Log.i("firebase-firestore", "${arregloRestaurantes[position]}")
                restauranteSeleccionado = arregloRestaurantes[position]
            }
            override fun onNothingSelected(parent: AdapterView<*>){
                Log.i("firebase-firestore", "No seleccionó nada")
            }
        }

        val db = Firebase.firestore
        val referencia = db.collection("restaurante")

        referencia.get()
                .addOnSuccessListener {
                    for (document in it){
                        Log.i("firebase-firestore", "${document.id} => ${document.data}")
                        val restaurante = document.toObject(FirestoreRestauranteDto::class.java)
                        restaurante.uid = document.id
                        arregloRestaurantes.add(restaurante)
                        adaptadorRestaurantes?.notifyDataSetChanged()
                    }
                }
                .addOnFailureListener {
                }
    }

    fun agregarTipoComida(){
        val etTipoComida = findViewById<EditText>(R.id.et_tipo_comida)

        val texto = etTipoComida.text.toString()
        arregloTiposComida.add(etTipoComida.text.toString())

        val textViewTipoComida = findViewById<TextView>(R.id.tv_arreglo_tipo_comida)
        val textoAnterior = textViewTipoComida.text.toString()
        textViewTipoComida.setText("${textoAnterior}, ${texto}")
        etTipoComida.setText("")
    }

    fun crearOrden(){

        if(restauranteSeleccionado != null && FirebaseAuth.getInstance().currentUser != null){
            var restaurante = restauranteSeleccionado
            val instaciaAuth = FirebaseAuth.getInstance()
            val usuario = FirestoreUsuarioOrdenDto(instaciaAuth.currentUser!!.uid)
            val editTextReview = findViewById<EditText>(R.id.et_review)

            val nuevaOrden = hashMapOf<String, Any?>(
                    "restaurante" to restauranteSeleccionado,
                    "usuario" to usuario,
                    "review" to editTextReview.text.toString().toInt(),
                    "tiposComida" to arregloTiposComida,
                    "fechaCreacion" to com.google.firebase.Timestamp(java.util.Date())
            )

            val db = Firebase.firestore
            val referencia = db.collection("orden")
                    .document()
                    .set(nuevaOrden)

            referencia
                    .addOnSuccessListener{ }
                    .addOnFailureListener{ }
        }
    }
}