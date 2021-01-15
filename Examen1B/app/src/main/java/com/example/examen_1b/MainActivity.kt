package com.example.examen_1b

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
import android.widget.Button
import android.widget.ListView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.examen_1b.Peliculas.Lista_Peliculas

import com.example.examen_1b.data.DirectorNuevo.Director

import com.example.examen_1b.data.DirectorNuevo.DirectorViewModel



class MainActivity : AppCompatActivity() {


    private lateinit var Ndirectorviewmodel: DirectorViewModel
    public var itemseleccionado: Director? = null

    var listaDirectores: List<Director> = emptyList()

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        Ndirectorviewmodel = ViewModelProvider(this).get(DirectorViewModel::class.java)

        /*val director01 :Director = Director(null,"Steven","Spielberg",
            "Estados Unidos",33,"steven.spielberg@gmail.com")
        val director02 :Director = Director(null,"Quentin","Tarantino",
            "Estados Unidos",57,"quentin.tarantino@gmail.com")
        Ndirectorviewmodel.ingresarDirector(director01)
        Ndirectorviewmodel.ingresarDirector(director02)*/


        Ndirectorviewmodel.readAllData.observe(this, Observer { director ->
            val adaptador = ArrayAdapter(
                this,
                android.R.layout.simple_list_item_1,
                director
            )
            val listView = findViewById<ListView>(R.id.lv_directores)

            listView.adapter = adaptador
            adaptador.notifyDataSetChanged()
            registerForContextMenu(listView)

            listaDirectores = director
        })

        val buttonCrearDirector = findViewById<Button>(R.id.btn_crear_director)
            .setOnClickListener { irActividad(Crear_Director::class.java, null, 103) }

    }


    fun irActividad(
        clase: Class<*>,
        parametros: ArrayList<Pair<String, Director>>? = null,
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
                tipoDato = it.second is Director // instanceOf()
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

    override fun onCreateContextMenu(
        menu: ContextMenu?,
        v: View?,
        menuInfo: ContextMenu.ContextMenuInfo?
    ) {
        super.onCreateContextMenu(menu, v, menuInfo)
        val inflater = menuInflater
            .inflate(R.menu.menu_directores, menu)
        val info = menuInfo as AdapterView.AdapterContextMenuInfo
        val id = info.position
        itemseleccionado = listaDirectores[id]

        Log.i("PRUEBA", "AQUII ${itemseleccionado}")
    }

    override fun onContextItemSelected(item: MenuItem): Boolean {
        return when (item?.itemId) {

            R.id.itemInfo -> {
                //Log.i("PRUEBA", "INFORMacion ${itemseleccionado}")
                if(itemseleccionado != null){
                    val parametros = arrayListOf<Pair<String, Director>>(
                        Pair("director", itemseleccionado as Director)
                    )
                    irActividad(Informacion_Director::class.java, parametros, null)
                }
                return true
            }

            // Editar
            R.id.itemEditar -> {
                val parametros = arrayListOf<Pair<String, Director>>(
                    Pair("director", itemseleccionado) as Pair<String, Director>
                )
                irActividad(Editar_Director::class.java, parametros, 102)
                return true
            }

            // Ver Peliculas
            R.id.itemVerPeliculas -> {
                val intentExplicito = Intent(
                    this,
                    Lista_Peliculas::class.java
                )
                val parametros = arrayListOf<Pair<String, Director>>(
                    Pair("director", itemseleccionado) as Pair<String, Director>
                )
                irActividad(Lista_Peliculas::class.java, parametros, null)
                return true
            }

            R.id.itemEliminar -> {
                itemseleccionado?.let { Ndirectorviewmodel.eliminarDirector(it) }
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
            102 -> { // EDITAR
                if (resultCode == RESULT_OK) {  //  RESULT_OK
                    if (data != null) {
                        val directorEditado = data.getParcelableExtra<Director>("result")
                        //Log.i("PRUEBA", " DirectorEditado ${directorEditado.toString()}")
                        if (directorEditado != null) {
                            Ndirectorviewmodel.actualizarDirector(directorEditado)
                        }
                    } else {}

                } else { // RESULT_CANCELED
                    //Log.i("PRUEBA", "Usuario no lleno los datos")
                }
            }

            103 -> { // CREAR
                if (resultCode == RESULT_OK) {  //  RESULT_OK
                    //Log.i("PRUEBA", " Informacion ${data?.getStringExtra("result").toString()}")
                    if (data != null) {
                        val Nuevodirector = data.getParcelableExtra<Director>("result")
                        //val Nuevodirector = data.getE
                        //Log.i("PRUEBA", " Nuevo director ${Nuevodirector.toString()}")
                        if (Nuevodirector != null){
                            Ndirectorviewmodel.ingresarDirector(Nuevodirector)
                        }
                    } else {}

                } else { // RESULT_CANCELED
                    Log.i("PRUEBA", "Usuario no lleno los datos")
                }
            }

        }
    }

}