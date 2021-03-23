package com.example.proyecto_2b

import android.content.Intent
import android.media.MediaPlayer
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Parcelable
import android.util.Log
import android.view.View
import android.widget.*
import com.example.proyecto_2b.Clases.Data.Cancion_lista
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage
import java.io.File
import java.io.FileInputStream
import java.io.FileOutputStream

class todas_canciones : AppCompatActivity() {

    var cancionesLista = ArrayList<Cancion_lista>()
    var cancionesListaBusqueda = ArrayList<Cancion_lista>()


    lateinit var origen: String
    lateinit var lista: String
    lateinit var idUsuario: String
    lateinit var textBusqueda: String
    lateinit var nombreLista: String
    var nombre: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_todas_canciones)

        origen = intent.getStringExtra("vengo").toString()
        lista = intent.getStringExtra("lista").toString()
        idUsuario = intent.getStringExtra("idUsuario").toString()
        nombreLista = intent.getStringExtra("nombre").toString()

        nombre = nombreLista
        Log.i("ahoravamo", "${nombreLista}")

        var nombreLista = findViewById<TextView>(R.id.tv_nombre_lista)
        nombreLista.setText("${nombre}")

        if(origen == "PP"){
            obtenerCancionesPP()
        }
        else if(origen == "favoritos"){
            Log.i("vengo", "${lista}")
            obtenerCancionesFavoritos()
        }
        else{
            Log.i("vengoLista", "${nombreLista.toString()}")
            obtenerCancionesListas(lista!!)
        }

        var btnBusqueda = findViewById<Button>(R.id.btn_buscar)
                .setOnClickListener {
                    var EtBuscar = findViewById<EditText>(R.id.et_buscar)
                    textBusqueda = EtBuscar.text.toString()

                    obtenerCancionesBusqueda()

                }
    }

    override fun onResume() {
        super.onResume()
        cancionesLista.clear()
        cancionesListaBusqueda.clear()
    }

    fun obtenerCancionesBusqueda(){
        cancionesListaBusqueda.clear()
        var mensaje = findViewById<TextView>(R.id.tv_mensaje_cancion)

        var db = Firebase.firestore
        var resultado = db
                .collection("cancion")
                .whereEqualTo("nombre", "${textBusqueda}")
                .get()
                .addOnSuccessListener {
                    var lista:Int = 0
                    for (cancion in it){
                        var cancion = Cancion_lista(cancion.id,
                                cancion.get("nombre").toString(),
                                cancion.get("artista") as List<String>,
                                cancion.get("pista").toString(),
                                cancion.get("caratula").toString()
                        );
                        cancionesListaBusqueda.add(cancion)
                        lista = lista + 1
                    }
                    if(lista == 0){
                        Log.i("busqueda" ,"no encontro nadaaaaa ${cancionesLista}")
                        llenarListado(cancionesLista)
                        if(textBusqueda ==  ""){
                            mensaje.visibility = View.INVISIBLE
                        }
                        else{
                            mensaje.visibility = View.VISIBLE
                        }

                    }else{
                        Log.i("busqueda" ,"encontroooo  ${cancionesListaBusqueda}")
                        llenarListado(cancionesListaBusqueda)
                        mensaje.visibility = View.INVISIBLE
                    }

                }
                .addOnFailureListener {
                }

    }

    fun obtenerCancionesPP(){
        cancionesLista.clear()
        var db = Firebase.firestore
        var resultado = db
            .collection("cancion")
                .orderBy("nombre")
            .get()
            .addOnSuccessListener {
                for (cancion in it){
                    var cancion = Cancion_lista(cancion.id,
                                                cancion.get("nombre").toString(),
                                                cancion.get("artista") as List<String>,
                                                cancion.get("pista").toString(),
                                                cancion.get("caratula").toString()
                                                );
                    cancionesLista.add(cancion)
                }
                Log.i("lista-canciones" ,"${cancionesLista}")

                //initRecycler()
                llenarListado(cancionesLista)
            }
    }




    fun obtenerCancionesListas(origen:String){
        cancionesLista.clear()
        var db = Firebase.firestore
        var referencia = db
                .collection("usuario")
                .document(idUsuario)
                .collection("ListasReproduccion")
                .document(origen)
                .collection("cancion")
                .orderBy("nombre")
                .get()
                .addOnSuccessListener {
                    for (cancion in it){
                        if(cancion.get("nombre") != null){
                            var cancion = Cancion_lista(cancion.id,
                                    cancion.get("nombre")?.toString(),
                                    cancion.get("artista") as List<String>,
                                    cancion.get("pista").toString(),
                                    cancion.get("caratula").toString()
                            );
                            cancionesLista.add(cancion)
                            Log.i("nueva-lista","${cancion}")
                        }
                    }
                    llenarListado(cancionesLista)
                }
                .addOnFailureListener {
                }
    }


    fun obtenerCancionesFavoritos(){
        cancionesLista.clear()
        var db = Firebase.firestore
        var referencia = db
                .collection("usuario")
                .document(idUsuario)
                .collection("Favorito")
                .orderBy("nombre")
                .get()
                .addOnSuccessListener {
                    for (cancion in it){
                        if(cancion.get("nombre") != null){
                            var cancion = Cancion_lista(cancion.id,
                                    cancion.get("nombre")?.toString(),
                                    cancion.get("artista") as List<String>,
                                    cancion.get("pista").toString(),
                                    cancion.get("caratula").toString()
                            );
                            cancionesLista.add(cancion)
                            Log.i("nueva-lista","${cancion}")
                        }
                    }
                    llenarListado(cancionesLista)
                }
                .addOnFailureListener {
                }
    }

    fun llenarListado(lista: ArrayList<Cancion_lista>){
        val adaptador = ArrayAdapter(
            this,
            android.R.layout.simple_list_item_1,
                lista
        )

        var listView = findViewById<ListView>(R.id.lv_todas_canciones)

        listView.adapter = adaptador
        adaptador.notifyDataSetChanged()
        registerForContextMenu(listView)

        listView.setOnItemClickListener(AdapterView.OnItemClickListener { parent, view, position, id ->

            var parametros = arrayListOf<Pair<String, *>>(
                Pair("lista", cancionesLista),
                Pair("posicion", id.toInt()),
                    Pair("idUsuario", idUsuario),
                    Pair("nombre", "${nombre}")
            )

            irActividad(reproductor::class.java, parametros, 100)

        })
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

    override fun onActivityResult(
        requestCode: Int,  // Codigo de petición  - Codigo: 102
        resultCode: Int,  //  Codigo de Resultado - RESULT_OK o RESULT_CANCELED
        data: Intent?  // Datos (opcionales)
    ) {
        super.onActivityResult(requestCode, resultCode, data)

        when (requestCode) {
            100 ->{
                if(origen == "PP"){
                    cancionesLista = ArrayList()
                    obtenerCancionesPP()
                }
                else if (origen == "favoritos"){
                    cancionesLista.clear()
                    obtenerCancionesFavoritos()
                }
                else{
                    Log.i("vengo", "${lista}")
                    cancionesLista = ArrayList()
                    obtenerCancionesListas(lista!!)
                }
            }
        }

    }

}