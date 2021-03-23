package com.example.proyecto_2b

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Parcelable
import android.util.Log
import android.view.ContextMenu
import android.view.MenuItem
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.ListView
import com.example.proyecto_2b.Clases.Data.Cancion_lista
import com.example.proyecto_2b.Clases.Data.ListaReproduccion
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import java.lang.reflect.Field

class Listas_Reproduccion : AppCompatActivity() {

    var listaReproduccion = ArrayList<ListaReproduccion>()
    var itemseleccionado: ListaReproduccion? = null
    lateinit var idUsuario: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_listas__reproduccion)

        idUsuario = intent.getStringExtra("idUsuario").toString()

        var parametros2 = arrayListOf<Pair<String, *>>(
                Pair("idUsuario", idUsuario),
                Pair("editar", "no"),
                Pair("nomEditar", "no")
        )

        obtenerlistas()

        var crear = findViewById<ImageView>(R.id.im_crear_lista)
            .setOnClickListener {
                irActividad(Nueva_Lista::class.java, parametros2, 100)
            }
    }



    fun llenarBase(){
        var artistas: ArrayList<String> = arrayListOf("SOJA")
        var cancion = Cancion_lista("4HBnb28gRVhfL0WY1eQw",
                "Strength To Survive",
                artistas,
                "soja-strength-to-survive-official-video.mp3",
                "51FenYL-cML._SY355_.jpg"
                )

        val nuevalista = hashMapOf<String, Any?>(
                "nombre" to "Lista 1",
                "cancion" to cancion
        )

        val db = Firebase.firestore
        val referencia = db.collection("ListaReproduccion")
                .document()
                .set(nuevalista)

        referencia
                .addOnSuccessListener {
                    Log.i("nueva-lista","Funcinoooo")
                }
                .addOnFailureListener {
                    Log.i("nueva-lista", "Nooooo F")
                }

    }

    fun obtenerlistas(){
        listaReproduccion.clear()
        var db = Firebase.firestore
        var referencia = db
                .collection("usuario")
                .document(idUsuario)
                .collection("ListasReproduccion")
                .orderBy("nombre")
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
            var nombre :String = "paquito"
            var parametros = arrayListOf<Pair<String, *>>(
                    Pair("lista", listaReproduccion[id.toInt()].id),
                    Pair("vengo", ""),
                    Pair("idUsuario", "${idUsuario}"),
                    Pair("nombre", "${listaReproduccion[id.toInt()].nombre}")
            )

            irActividad(todas_canciones::class.java, parametros, 100)

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

    override fun onCreateContextMenu(
            menu: ContextMenu?,
            v: View?,
            menuInfo: ContextMenu.ContextMenuInfo?
    ) {
        super.onCreateContextMenu(menu, v, menuInfo)
        val inflater = menuInflater
                .inflate(R.menu.menu, menu)
        val info = menuInfo as AdapterView.AdapterContextMenuInfo
        val id = info.position
        itemseleccionado = listaReproduccion[id]

        Log.i("PRUEBA", "AQUII ")
    }

    fun eliminarDocumento(){
        var db = Firebase.firestore
        var referencia = db

        val updates = hashMapOf<String, Any>(
                "nombre" to FieldValue.delete(),
                "cancion" to FieldValue.delete()
        )

        referencia
                .collection("usuario")
                .document(idUsuario)
                .collection("ListasReproduccion")
                .document(itemseleccionado?.id.toString())
                .delete()
    }

    override fun onContextItemSelected(item: MenuItem): Boolean {
        return when (item?.itemId) {
            R.id.mi_editar -> {
                Log.i("editar", "${itemseleccionado?.id}")
                val parametros3 = arrayListOf<Pair<String, *>>(
                        Pair("idUsuario", idUsuario),
                        Pair("editar", "${itemseleccionado?.id}"),
                        Pair("nomEditar", "${itemseleccionado?.nombre}")
                )
                irActividad(Nueva_Lista::class.java, parametros3, 103)
                return true
            }
            R.id.mi_eliminar -> {
                eliminarDocumento()
                obtenerlistas()
                return true
            }
            else -> super.onContextItemSelected(item)
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
            103 ->{
                if (resultCode == RESULT_OK){
                    listaReproduccion = ArrayList()
                    obtenerlistas()
                }
            }
        }

    }

}




