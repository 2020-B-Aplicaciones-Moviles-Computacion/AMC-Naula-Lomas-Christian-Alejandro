package com.example.firebase_ejemplo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import androidx.fragment.app.Fragment
import com.google.android.gms.dynamic.SupportFragmentWrapper

class EFragmento : AppCompatActivity() {

    lateinit var fragmentoActual: Fragment

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_e_fragmento)

        /*fragmentoActual = PrimerFragment()
        crearFrgmentoUno()*/

        val fragmentoUno = findViewById<Button>(R.id.btn_crear_primer_frag)
            .setOnClickListener {
                crearFrgmentoUno()
            }
        val fragmentoDos = findViewById<Button>(R.id.btn_crear_segundo_frag)
            .setOnClickListener {
                crearFragmentDos()
            }
    }

    fun crearFrgmentoUno() {
        val fragmentManager = supportFragmentManager

        val fragmentTransaction = fragmentManager.beginTransaction()

        val primerFragmento = PrimerFragment()
        val argumentos = Bundle()
        argumentos.putString("nombre","Christian Naula")
        argumentos.putInt("edad", 22)
        primerFragmento.arguments = argumentos

        //Anadir Fragmento

        fragmentTransaction.replace(R.id.relative_layout_fragmentos, primerFragmento)
        fragmentoActual = primerFragmento
        fragmentTransaction.commit()
    }

    fun crearFragmentDos(){
        val fragmentManager = supportFragmentManager

        val fragmentTransaction = fragmentManager.beginTransaction()

        val segundoFragmento = SegundoFragment()

        val argumentos = Bundle()
        argumentos.putString("nombre","Christian Naula")
        argumentos.putInt("edad", 22)
        segundoFragmento.arguments = argumentos

        fragmentTransaction.replace(R.id.relative_layout_fragmentos, segundoFragmento)
        fragmentoActual = segundoFragmento
        fragmentTransaction.commit()
    }
}