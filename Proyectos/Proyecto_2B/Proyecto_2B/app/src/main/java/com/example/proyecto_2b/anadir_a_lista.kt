package com.example.proyecto_2b

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Parcelable
import android.util.Log
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.ListView
import com.example.proyecto_2b.Clases.Data.Cancion_lista
import com.example.proyecto_2b.Clases.Data.ListaReproduccion
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class anadir_a_lista : AppCompatActivity() {

    var listaReproduccion = ArrayList<ListaReproduccion>()
    var cancion: Cancion_lista? = null
    var idLista: String? = null
    lateinit var idUsuario: String
    lateinit var vengode: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_anadir_a_lista)

        cancion = intent.getParcelableExtra<Cancion_lista>("cancion")
        idUsuario = intent.getStringExtra("idUsuario").toString()
        vengode = "vengode"
        //idLista = intent.getStringExtra("id")

        obtenerlistas()

        var irCrearLista = findViewById<Button>(R.id.btn_Crear_lista)
                .setOnClickListener {
                    var parametros = arrayListOf<Pair<String, *>>(
                            Pair("idUsuario", idUsuario),
                    )
                    irActividad(Nueva_Lista::class.java, parametros, 100)
                }
    }


    fun obtenerlistas(){
        var db = Firebase.firestore
        var referencia = db
                .collection("usuario")
                .document(idUsuario)
            .collection("ListasReproduccion")
            .get()
            .addOnSuccessListener {
                for (lista in it){
                    Log.i("nueva-lista","${lista.data}");
                    var nuevaLista = ListaReproduccion(
                        lista.id.toString(),
                        lista.get("nombre").toString())
                    listaReproduccion.add(nuevaLista)
                }
                llenarListado()
            }
            .addOnFailureListener {

            }
    }

    fun llenarListado() {
        val adaptador = ArrayAdapter(
            this,
            android.R.layout.simple_list_item_1,
            listaReproduccion
        )
        var listView = findViewById<ListView>(R.id.lv_listas_reproduccion)
        listView.adapter = adaptador
        adaptador.notifyDataSetChanged()
        registerForContextMenu(listView)
        listView.setOnItemClickListener(AdapterView.OnItemClickListener { parent, view, position, id ->
            Log.i("cancion", "${listaReproduccion[id.toInt()].nombre}")

            crearLista(listaReproduccion[id.toInt()].id!!)
            this.finish()

        })
    }

    private fun crearLista(idLista:String) {
        var fb = Firebase.firestore
        var referencia = cancion?.id?.let {
            fb
                .collection("usuario")
                .document(idUsuario)
                    .collection("ListasReproduccion")
                    .document(idLista!!)
                    .collection("cancion")
                    .document(it)
                    .set(cancion!!)
        }
    }

    override fun onActivityResult(
            requestCode: Int,  // Codigo de petición  - Codigo: 102
            resultCode: Int,  //  Codigo de Resultado - RESULT_OK o RESULT_CANCELED
            data: Intent?  // Datos (opcionales)
    ) {
        super.onActivityResult(requestCode, resultCode, data)

        when (requestCode) {
            100 ->{
                if (resultCode == RESULT_OK){
                    listaReproduccion = ArrayList()
                    obtenerlistas()
                }
            }
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

                tipoDato = it.second is ArrayList<*> // instanceOf()
                if (tipoDato == true) {
                    intentExplicito.putExtra(nombreVariable, valorVariable as ArrayList<Parcelable>)
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