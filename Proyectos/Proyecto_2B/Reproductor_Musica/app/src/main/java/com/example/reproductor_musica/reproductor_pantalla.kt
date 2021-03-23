package com.example.reproductor_musica

import android.content.Intent
import android.media.MediaPlayer
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Parcelable
import android.util.Log
import android.widget.ImageView
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage

class reproductor_pantalla : AppCompatActivity() {
    var mp: MediaPlayer = MediaPlayer()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_reproductor_pantalla)
        firebase()

        mp = MediaPlayer.create(this, R.raw.cancion1)
        Log.i("cancion", "${mp.duration}")
        var btnPlay = findViewById<ImageView>(R.id.btn_play)
            .setOnClickListener {
                if(mp.isPlaying){
                    mp.pause()
                    var btnPlay = findViewById<ImageView>(R.id.btn_play)
                        .setImageResource(R.drawable.ic_boton_de_play)
                }
                else{
                    mp.start()

                    var btnPlay = findViewById<ImageView>(R.id.btn_play)
                        .setImageResource(R.drawable.ic_pausa03)
                }
            }

        var opciones = findViewById<ImageView>(R.id.iv_menu)
            .setOnClickListener {
                irActividad(Opciones::class.java)
            }


    }

    override fun onDestroy() {
        super.onDestroy()
        mp.stop()
    }

    fun firebase(){
        var referencia = Firebase.storage.reference
        var nombreArchivo = referencia.child("gs://proyecto-moviles-b2.appspot.com/ejemplo.jfif")


        //var nombreArchivo = referencia

        nombreArchivo.getBytes(10024 * 10024)
            .addOnSuccessListener {
                var datos = it
                Log.i("cancion", "${datos}")
            }
            .addOnFailureListener {
                Log.i("cancion", "Fallido")
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


/**
var texto = findViewById<TextView>(R.id.et_pathImagen).text

var referencia = Firebase.storage

if (texto != null){
var nombreImg = referencia.reference.child(texto.toString())
nombreImg.getBytes(10024*10024)
.addOnSuccessListener {
val bit = BitmapFactory.decodeByteArray(it, 0, it.size)
Log.i("Firebase-Imagen", "Imagen recuperada->  ${dataDir}" )
var imagen=findViewById<ImageView>(R.id.im_mostrarImagen)
.setImageBitmap(bit)
}
.addOnFailureListener {
Log.i("Firebase-Imagen", "Fallido")
}
}
 */
