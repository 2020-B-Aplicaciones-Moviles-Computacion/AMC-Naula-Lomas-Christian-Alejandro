package com.example.movilescomputacion

import android.content.DialogInterface
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.ContextMenu
import android.view.MenuItem
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.ListView
import androidx.appcompat.app.AlertDialog

class BListView : AppCompatActivity() {
    var posicionItemSeleccionado = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_b_list_view)

        BaseDatosMemoria.cargaInicialDatos()

        val adaptador = ArrayAdapter(
            this, // contexto
            android.R.layout.simple_list_item_1,  // layput (xml visula) existente en Android
            //BaseDatosMemoria.arregloEnteros //arreglo
            BaseDatosMemoria.arregloEntenadores
        )

        val listView = findViewById<ListView>(R.id.lv_entrenador)

        listView.adapter = adaptador

        /*listView.setOnItemLongClickListener { parent, view, position, id ->
            Log.i("intent-explicito", "Hola ${position} ${id}")

            val builder = AlertDialog.Builder(this)

            builder.setTitle("Titulo")
            val seleccionUsuario = booleanArrayOf(true, false, false)

            val opciones = resources.getStringArray(R.array.string_array_opciones_dialogo)
            builder.setMultiChoiceItems(
                //arrayListOf("M","P","S"),
                opciones,
                seleccionUsuario,
                DialogInterface.OnMultiChoiceClickListener{ dialog, which, isCheked ->
                    Log.i("intent-explicito", "Selecciono ${which} ${isCheked}")
                }
            )

            //builder.setMessage("Hola")
                .setPositiveButton(
                    "Si",
                    DialogInterface.OnClickListener { dialog, which ->
                        Log.i("intent-explicito", "Si")
                    }
                )
                .setNegativeButton(
                    "No",
                    null)
            val dialogo = builder.create()
            dialogo.show()
            return@setOnItemLongClickListener true
        }*/

        adaptador.notifyDataSetChanged()
        registerForContextMenu(listView)

        val botonAñadirLV = findViewById<Button>(R.id.btn_anadir_item_lv)
        botonAñadirLV.setOnClickListener {
            añadirListView(
                adaptador,
                "Fernando",
                "Nadador",
                BaseDatosMemoria.arregloEntenadores
            )
        }

    }

    fun añadirListView(
        adaptador: ArrayAdapter<String>,
        item: String,
        descripcion: String,
        arreglo: ArrayList<String>
    ) {
        arreglo.add(BEntrenador(item, descripcion, null).toString())
        //arreglo.add(descripcion)
        adaptador.notifyDataSetChanged()
    }

    override fun onCreateContextMenu(menu: ContextMenu?, v: View?, menuInfo: ContextMenu.ContextMenuInfo?) {
        super.onCreateContextMenu(menu, v, menuInfo)

        val inflater = menuInflater
        inflater.inflate(R.menu.menu, menu)

        val info = menuInfo as AdapterView.AdapterContextMenuInfo
        val id = info.position
        posicionItemSeleccionado = id
    }

    override fun onContextItemSelected(item: MenuItem): Boolean {
        return when (item?.itemId) {
            // Editar
            R.id.mi_editar -> {
                Log.i("intent-explicito", "Editar " +
                        "${BaseDatosMemoria.arregloEntenadores[posicionItemSeleccionado]}")

                return true
            }
            // Editar
            R.id.mi_eliminar -> {
                Log.i("intent-explicito", "Eliminar " +
                        "${BaseDatosMemoria.arregloEntenadores[posicionItemSeleccionado]}")
                return true
            }

            else -> super.onContextItemSelected(item)
        }
    }
}