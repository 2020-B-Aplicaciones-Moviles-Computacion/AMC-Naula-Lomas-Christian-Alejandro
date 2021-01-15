package com.example.examen_1b.Peliculas

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Parcelable
import android.util.Log
import android.view.ContextMenu
import android.view.MenuItem
import android.view.View
import android.widget.*
import androidx.constraintlayout.solver.widgets.analyzer.Direct
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.examen_1b.Informacion_Director
import com.example.examen_1b.R
import com.example.examen_1b.data.DirectorNuevo.Director
import com.example.examen_1b.data.Pelicula.Pelicula
import com.example.examen_1b.data.Pelicula.PeliculaViewModel


class Lista_Peliculas : AppCompatActivity() {

    private lateinit var Npeliculaviewmodel: PeliculaViewModel
    public var itemseleccionado: Pelicula? = null
    var DirectorGlobal: Director? =  null
    var listaPeliculas: List<Pelicula> = emptyList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_lista_peliculas)

        Npeliculaviewmodel = ViewModelProvider(this).get(PeliculaViewModel::class.java)


        val director = intent.getParcelableExtra<Director>("director")
        DirectorGlobal = director
        var pelicula = Pelicula(0,"Bastardos sin gloria","Bélico",153,2009, 1)

        //Npeliculaviewmodel.ingresarPelicula(pelicula)

        if (director != null) {
            actualizardatos(director)
        }

        val nombreDirector = findViewById<TextView>(R.id.txt_nombrePeli)
        nombreDirector.text = director.toString()

        val btnRegresar = findViewById<Button>(R.id.btn_Regresar)
            .setOnClickListener{
                this.setResult(RESULT_OK)
                this.finish()
            }

        val botonCrearPeli = findViewById<Button>(R.id.btn_crear_pelicula)
            .setOnClickListener {
                val parametros = arrayListOf<Pair<String, *>>(
                    Pair("director", director as Director)
                )
                irActividad(Crear_Peliculas::class.java, null, 103)
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
                tipoDato = it.second is Pelicula // instanceOf()
                if (tipoDato == true) {
                    intentExplicito.putExtra(nombreVariable, valorVariable as Parcelable)
                }
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
            .inflate(R.menu.menu_peliculas, menu)
        val info = menuInfo as AdapterView.AdapterContextMenuInfo
        val id = info.position
        itemseleccionado = listaPeliculas[id]

        Log.i("PRUEBA", "AQUII ${itemseleccionado}")
    }

    override fun onContextItemSelected(item: MenuItem): Boolean {
        return when (item?.itemId) {
            R.id.item_infoPeli-> {
                //Npeliculaviewmodel.actualizarPelicula(Pelicula(1,"Bastardos sin gloria","Bélico",153,2009, 1 ))
                if(itemseleccionado != null){
                    val parametros = arrayListOf<Pair<String, *>>(
                        Pair("pelicula", itemseleccionado as Pelicula),
                        Pair("director", DirectorGlobal as Director)
                    )
                    Log.i("ejemplo", "${itemseleccionado}")
                    irActividad(informacion_pelicula::class.java, parametros, null)
                }
                return true
            }

            R.id.item_editarPeli->{
                if(itemseleccionado != null){
                    val parametros = arrayListOf<Pair<String, *>>(
                        Pair("pelicula", itemseleccionado as Pelicula),
                        Pair("director", DirectorGlobal as Director)
                    )
                    irActividad(editar_pelicula::class.java, parametros, 102)
                }
                return true
            }

            R.id.item_eliminarPeli->{
                itemseleccionado?.let { Npeliculaviewmodel.eliminarPelicula(it) }
                DirectorGlobal?.let { actualizardatos(it) }
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
                        val peliculaEditado = data.getParcelableExtra<Pelicula>("result")
                        Log.i("PRUEBA", " DirectorEditado ${peliculaEditado.toString()}")
                        if (peliculaEditado != null) {
                            Npeliculaviewmodel.actualizarPelicula(peliculaEditado)
                            DirectorGlobal?.let { actualizardatos(it) }
                        }
                    } else {}

                } else { // RESULT_CANCELED
                    Log.i("PRUEBA", "Usuario no lleno los datos")
                }
            }

            103 -> { // CREAR
                if (resultCode == RESULT_OK) {  //  RESULT_OK
                    if (data != null) {

                        val Nuevapelicula = data.getParcelableExtra<Pelicula>("result")

                        Log.i("PRUEBAPELICULA", " Nuevo director ${Nuevapelicula.toString()}")

                        if (Nuevapelicula != null){
                            Nuevapelicula.idDirector = DirectorGlobal?.idDirector
                            null.also { Nuevapelicula.idPelicula = it }
                            Log.i("PRUEBAFINAL", "PRUEBAPELICULA:  ${Nuevapelicula.toString()}")
                            Log.i("PRUEBAFINAL", "LLEGAMOS HASYA AQUI NO ENTIENDO EL RESTO")
                            Npeliculaviewmodel.ingresarPelicula(Nuevapelicula)
                            DirectorGlobal?.let { actualizardatos(it)}
                        }
                    } else {}

                } else { // RESULT_CANCELED
                    Log.i("PRUEBAPELICULA", "Usuario no lleno los datos")
                }
            }
        }
    }

    fun actualizardatos(director: Director) {
        director?.idDirector?.let {
            Npeliculaviewmodel.PeliculaPorID(it).observe(this, Observer { pelicula ->
                //Log.i("PRUEBAFINAL","${pelicula}")
                val adaptador = ArrayAdapter(
                    this,
                    android.R.layout.simple_list_item_1,
                    pelicula
                )
                val listView = findViewById<ListView>(R.id.lv_peliculas)

                listView.adapter = adaptador
                adaptador.notifyDataSetChanged()
                registerForContextMenu(listView)

                listaPeliculas = pelicula
            })
        }


    }

}