package com.example.movilescomputacion

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val buttonCicloVida = findViewById<Button>(R.id.button_ir_ciclo_vida)
        buttonCicloVida.setOnClickListener {
            irCicloVida()
        }
        val buttonListView = findViewById<Button>(R.id.bnt_ir_list_view)
        buttonListView.setOnClickListener {
            irListView()
        }

    }

    fun irListView(){
        val intentExplicito = Intent(
            this,
            BListView::class.java
        )
        startActivity(intentExplicito)
    }

    fun irCicloVida(){
        val intentExplicito = Intent(
            this,
            ACicloVida::class.java
        )
        startActivity(intentExplicito)
    }




}