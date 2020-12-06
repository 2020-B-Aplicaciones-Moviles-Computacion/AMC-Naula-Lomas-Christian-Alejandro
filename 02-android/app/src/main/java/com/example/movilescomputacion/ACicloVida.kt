package com.example.movilescomputacion

import android.content.res.Configuration
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.PersistableBundle
import android.util.Log
import android.widget.Button
import android.widget.TextView

class ACicloVida : AppCompatActivity() {

    var total = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_a_ciclo_vida)
        val textototal = findViewById<TextView>(R.id.txv_ciclo_vida )
        val buttonSumar = findViewById<Button>(R.id.btn_ciclo_vida)
        buttonSumar.setOnClickListener {
            total = total + 1
            textototal.text = total.toString()
        }

        Log.i("ciclo-vida", "OnCreate")
    }


    override fun onSaveInstanceState(outState: Bundle) {
        Log.i("ciclo-vida", "onSaveInstanceState")
        //if (outState != null){
        outState.run {
            // AQUI GUARDAMOS (Solo Bundle)
            // CUALQUIER PRIMITIVO - SOLO PRIMITIVOS (Int, Sting, double, boolean)
            putInt("totalGuardado", total)
        }
        //}
        super.onSaveInstanceState(outState)
    }

    override fun onRestoreInstanceState(
        savedInstanceState: Bundle
    ) {
        if (savedInstanceState != null) {
            super.onRestoreInstanceState(savedInstanceState)
        }
        Log.i("ciclo-vida", "onRestoreInstanceState")
        val totalRecuperado:Int? = savedInstanceState.getInt("totalGuardado")
        if (totalRecuperado!=null){
            this.total = totalRecuperado
            val textototal = findViewById<TextView>(R.id.txv_ciclo_vida)
            textototal.text = total.toString()
        }
    }

    override fun onStart() {
        super.onStart()
        Log.i("ciclo-vida", "OnStart")
    }

    override fun onResume() {
        super.onResume()
        Log.i("ciclo-vida", "OnResume")
    }

    override fun onRestart() {
        super.onRestart()
        Log.i("ciclo-vida", "onRestart")
    }

    override fun onPause() {
        super.onPause()
        Log.i("ciclo-vida", "onPause")
    }

    override fun onStop() {
        super.onStop()
        Log.i("ciclo-vida", "OnStop")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.i("ciclo-vida", "OnDestroy")
    }

}