package com.example.myapplication

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.room.Room
import com.example.myapplication.Director.Director
import com.example.myapplication.Director.DirectorViewModel
import com.example.myapplication.data.User
import com.example.myapplication.data.UserDataBase
import kotlin.math.log

class MainActivity : AppCompatActivity() {
    lateinit var lista: List<User>
    private lateinit var Ndirectorviewmodel: DirectorViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val db = Room.databaseBuilder(applicationContext,
        UserDataBase::class.java, "User").build()
        lista = emptyList()
        Ndirectorviewmodel = ViewModelProvider(this).get(DirectorViewModel::class.java)
        Ndirectorviewmodel.readAllData.observe(this, Observer { director ->
            Log.i("EJEMPLO", "${director.toString()}")
        })
        //val user: User = User(0,"christian", "lomas")

        val director: Director = Director(0,"STEVEN","SPIELBERG", "ESTADOS UNIDOS",
        73, "gmail")
        Ndirectorviewmodel.ingresarDirector(director)
        //Ndirectorviewmodel.observe
        Log.i("EJEMPLO", "${Ndirectorviewmodel}")



    }

    /*fun irActividad(
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
                //tipoDato = it.second is Direc // instanceOf()
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
    }*/

}