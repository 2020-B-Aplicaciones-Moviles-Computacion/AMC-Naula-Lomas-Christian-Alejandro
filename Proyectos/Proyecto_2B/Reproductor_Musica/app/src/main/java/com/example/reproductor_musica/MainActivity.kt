package com.example.reproductor_musica

import android.content.Intent
import android.media.MediaPlayer
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Parcelable
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        var mp: MediaPlayer = MediaPlayer.create(this, R.raw.cancion1)



        var imagen = findViewById<ImageView>(R.id.iv_inicio)
                .setOnClickListener {
                    cargar()
                }

        var caratulaCancion = findViewById<ImageView>(R.id.imageView13)
            .setOnClickListener {
                var parametros = arrayListOf<Pair<String, MediaPlayer>>(
                    Pair("cancion", mp)
                );
                irActividad(reproductor_pantalla::class.java)
            }

        var tituloCancion = findViewById<TextView>(R.id.tv_nombreCancion)
            .setOnClickListener {
                irActividad(reproductor_pantalla::class.java)
            }

        var ArtistaCancion = findViewById<TextView>(R.id.tv_artista)
            .setOnClickListener {
                irActividad(reproductor_pantalla::class.java)
            }

        var btnBuscar = findViewById<ImageView>(R.id.iv_buscar)
            .setOnClickListener {
                irActividad(Buscar::class.java)
            }

        var btnBibliotec = findViewById<ImageView>(R.id.iv_biblioteca)
            .setOnClickListener {
                irActividad(Biblioteca::class.java)
            }
    }


    fun cargar (){
        var imagen = findViewById<ImageView>(R.id.iv_inicio)
        imagen.setImageResource(R.drawable.ic_inicio);
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

                tipoDato = it.second is Parcelable // instanceOf()
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
}