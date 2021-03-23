package com.example.proyecto_2b

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Parcelable
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import com.example.proyecto_2b.Clases.Usuario
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class Nueva_Lista : AppCompatActivity() {

    lateinit var idUsuario: String
    lateinit var vengode: String
    lateinit var editar: String
    lateinit var nomEditar: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_nueva__lista)
        idUsuario = intent.getStringExtra("idUsuario").toString()
        vengode = intent.getStringExtra("vengode").toString()
        editar = intent.getStringExtra("editar").toString()
        nomEditar = intent.getStringExtra("nomEditar").toString()

        var crear = findViewById<Button>(R.id.btn_crear)
        var nombre = findViewById<TextView>(R.id.tv_nombre_lista5)
        var textes = findViewById<EditText>(R.id.et_nombre)

        if(editar != "no" && nomEditar != "no"){
            crear.text = "EDITAR"
            nombre.text = "Editar Lista de Reproducción"
            if(nomEditar != null){
                textes.setText(nomEditar)
            }
        }
        else{
            crear.text = "CREAR"
            nombre.text = "Nueva Lista de Reproducción"
        }


        crear
            .setOnClickListener {
                if(editar != "no" && nomEditar != "no"){
                    editarLista()
                    this.setResult(RESULT_OK)
                    this.finish()
                }
                else{
                    crearLista()
                    this.setResult(RESULT_OK)
                    this.finish()
                }

            }

        var cancelar = findViewById<Button>(R.id.btn_cancelar)
            .setOnClickListener {
                this.setResult(RESULT_CANCELED)
                this.finish()
            }
    }




    fun crearLista() {
        var text = findViewById<EditText>(R.id.et_nombre)
        var lista = hashMapOf<String, String>(
            Pair("nombre", text.text.toString())
        )
        var fb = Firebase.firestore
        var referencia = fb
                .collection("usuario")
                .document(idUsuario)
                .collection("ListasReproduccion")
            .document()
            .set(lista)
    }

    fun editarLista(){
        var text = findViewById<EditText>(R.id.et_nombre)
        var fb = Firebase.firestore
        var referencia = fb
                .collection("usuario")
                .document(idUsuario)
                .collection("ListasReproduccion")
                .document(editar)
                .update("nombre",text.text.toString())
                .addOnSuccessListener {
                    Log.i("editar", "ya crear ${editar} y ${idUsuario}")
                }
    }



}