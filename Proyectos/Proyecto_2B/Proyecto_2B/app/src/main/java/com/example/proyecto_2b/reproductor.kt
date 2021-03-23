package com.example.proyecto_2b

import android.content.Intent
import android.graphics.BitmapFactory
import android.media.Image
import android.media.MediaPlayer
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.os.Parcelable
import android.util.Log
import android.widget.Button
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import com.example.proyecto_2b.Clases.Data.Cancion_lista
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage
import java.io.File
import java.io.FileInputStream
import java.io.FileOutputStream
import kotlin.random.Random

class reproductor : AppCompatActivity() {

    //var  mp: MediaPlayer = MediaPlayer()
    lateinit var listadoCanciones: ArrayList<Cancion_lista>
    lateinit var idUsuario: String
    lateinit var nombre: String
    var Pos: Int = 0
    var repetir: Int = 0
    var aleatorio: Int = 0
    var favorito: Int = 0
    var esFav: Int = 0
    var nombreLista: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_reproductor)

        val listado: ArrayList<Cancion_lista> = intent.getParcelableArrayListExtra<Cancion_lista>("lista") as ArrayList<Cancion_lista>
        val posicion = intent.getIntExtra("posicion", 0)
        idUsuario = intent.getStringExtra("idUsuario").toString()
        listadoCanciones = listado
        Pos = posicion
        nombre = intent.getStringExtra("nombre").toString()
        nombreLista = nombre

        var nombreList = findViewById<TextView>(R.id.tv_nombre_lista)
        nombreList.setText(nombreLista)

        Log.i("cancion", "tamaño: ${listadoCanciones.size} ")

        Log.i("idUsuario", idUsuario)

        var anadirLista = findViewById<ImageView>(R.id.iv_lista_reproduccion)
            .setOnClickListener {
                var parametros = arrayListOf<Pair<String, *>>(
                    Pair("cancion", listadoCanciones[Pos]),
                    Pair("id", listadoCanciones[Pos].id),
                        Pair("idUsuario", idUsuario)
                )
                irActividad(anadir_a_lista::class.java, parametros)
            }

        cargarInfo()



    }

    override fun onDestroy() {
        super.onDestroy()
        //mp?.stop()
    }


    fun reproducir(pista: String){
        Log.i("posision", "${Pos}")
        var referencia = Firebase.storage
        var nombreImg = referencia.reference.child(pista)
        var  mp: MediaPlayer = MediaPlayer()
        var progre = findViewById<ProgressBar>(R.id.progressBar)
        progre.setProgress(0)
        var ptogreso: Int = 1

        var bandera = true
        var destruccion = false

        mp.stop()

        nombreImg.getBytes(10024*10024)
            .addOnSuccessListener {
                var file:File = File.createTempFile("cancion", "mp3")
                var fos: FileOutputStream = FileOutputStream(file)
                fos.write(it)
                fos.close()
                var fis: FileInputStream = FileInputStream(file)
                mp?.setDataSource(fis.fd)
                Log.i("cancion", "FIS.RD ${fis.fd}")

                Log.i("cancion", "MP ${mp}")
                mp?.prepare()
                mp?.start()


                if(mp.isPlaying){

                    var tiempoInicial = findViewById<TextView>(R.id.tv_inicion)
                    var tiempoFinal = findViewById<TextView>(R.id.tv_final)
                    var tiempo = 0
                    var entero = 0
                    var tiempoReal = mp.duration/60000

                    var result: Float = (mp.duration.toFloat()/60000)
                    var parteDecimal :Int = ((result - tiempoReal)*60).toInt()
                    var parteEntera: Int = result.toInt()
                    result.toInt()

                    tiempoInicial.text = "0:00"
                    tiempoFinal.text = "${parteEntera}:${parteDecimal}"

                    progre.setProgress(0)
                    progre.max = mp.duration/1000
                    var aux = mp.duration * 1000

                    val timer = object: CountDownTimer(aux.toLong(), 1000) {
                        var ptogreso: Int = 1
                        override fun onTick(millisUntilFinished: Long) {
                            if(ptogreso > 0 ){
                                progre.setProgress(ptogreso)
                            }
                            if (bandera){
                                ptogreso = ptogreso + 1;
                                if(tiempo < 10){
                                    tiempoInicial.text = "${entero}:0${tiempo}"
                                }
                                else{
                                    if (tiempo == 60){
                                        entero = entero + 1
                                        tiempo = 0
                                    }else{
                                        tiempoInicial.text = "${entero}:${tiempo}"
                                    }
                                }
                                tiempo = tiempo + 1
                            }
                        }
                        override fun onFinish() {
                            progre.setProgress(Int.MAX_VALUE)
                            Pos = Pos + 1
                            cargarInfo()
                        }
                    }
                    timer.start()
                }
            }

        var btnRepetir = findViewById<ImageView>(R.id.btn_repetir)
        btnRepetir.setOnClickListener {
            if (repetir == 0){
                btnRepetir.setImageResource(R.drawable.ic_repetir_color)
                repetir = 1
            }
            else{
                btnRepetir.setImageResource(R.drawable.ic_repetir)
                repetir = 0
            }
        }


        var btnAleatorio = findViewById<ImageView>(R.id.btn_aleatorio)
        btnAleatorio
                .setOnClickListener {
                    if(aleatorio == 0){
                        var aux = Random.nextInt(0..listadoCanciones.size)
                        btnAleatorio.setImageResource(R.drawable.ic_aleatori_color)
                        aleatorio = 1
                    }
                    else{
                        btnAleatorio.setImageResource(R.drawable.ic_aleatorio)
                        aleatorio = 0
                    }
                }

        var btnPlau = findViewById<ImageView>(R.id.btn_play)
                .setOnClickListener {
                    var imPlay = findViewById<ImageView>(R.id.btn_play)
                    if(mp.isPlaying){
                        mp.pause()
                        bandera=false
                        imPlay.setImageResource(R.drawable.ic_boton_de_play)
                    }
                    else{
                        mp.start()
                        bandera=true
                        imPlay.setImageResource(R.drawable.ic_pausa03)
                    }
                }

        var btnAdelante = findViewById<ImageView>(R.id.btn_siguiente)
            .setOnClickListener {
                mp.stop()
                if(aleatorio == 1){
                    Pos = Random.nextInt(0..listadoCanciones.size)
                }
                else{
                    if(Pos < listadoCanciones.size-1){
                        Pos = Pos + 1
                    }
                    else {
                        Pos = 0
                    }
                }
                Log.i("adelante", "${Pos}")
                ptogreso = 0
                bandera = false
                destruccion = true
                cargarInfo()
                var imPlay = findViewById<ImageView>(R.id.btn_play)
                imPlay.setImageResource(R.drawable.ic_pausa03)

            }

        var btnAtras = findViewById<ImageView>(R.id.btn_anterior)
            .setOnClickListener {
                mp.stop()
                if(aleatorio == 1){
                    Pos = Random.nextInt(0..listadoCanciones.size)
                }
                else{
                    if(Pos > 0) {
                        Pos = Pos - 1
                    }
                    else{
                        Pos = listadoCanciones.size - 1
                    }
                }
                ptogreso = 0
                bandera = false
                destruccion = true
                cargarInfo()
                var imPlay = findViewById<ImageView>(R.id.btn_play)
                imPlay.setImageResource(R.drawable.ic_pausa03)
            }


    }

    fun addFavorito(){
        var fb = Firebase.firestore
        var referencia = fb
                .collection("usuario")
                .document(idUsuario)
                .collection("Favorito")
                .document("${listadoCanciones[Pos].id}")
                .set(listadoCanciones[Pos])
    }

    fun deleteFavorito(){
        var fb = Firebase.firestore
        var referencia = fb
                .collection("usuario")
                .document(idUsuario)
                .collection("Favorito")
                .document("${listadoCanciones[Pos].id}")
                .delete()
    }

    fun esFavorito(){
        var btnFavorito = findViewById<ImageView>(R.id.iv_favorito)
        var contador = 0
        var fb = Firebase.firestore
        var referencia = fb
                .collection("usuario")
                .document(idUsuario)
                .collection("Favorito")
                .document("${listadoCanciones[Pos].id}")
                .get()

                .addOnSuccessListener { document ->
                    //Log.i("favorito", "${document.data}")
                    if (document.data != null){
                        Log.i("favorito", "verdadero")
                        esFav = 1
                        btnFavorito.setImageResource(R.drawable.ic_favorito_color)
                    }
                    else{
                        Log.i("favorito", "falso")
                        esFav = 0
                        btnFavorito.setImageResource(R.drawable.ic_favorito)
                    }
                    btnFavorito
                            .setOnClickListener {
                                if (esFav == 0){
                                    btnFavorito.setImageResource(R.drawable.ic_favorito_color)
                                    addFavorito()
                                    esFav = 1
                                }
                                else{
                                    btnFavorito.setImageResource(R.drawable.ic_favorito)
                                    deleteFavorito()
                                    esFav = 0
                                }
                            }

                }
                .addOnFailureListener {
                }
        //Log.i("favorito", "${listadoCanciones[Pos].id}")

    }

    fun cargarImagen(caratula: String){
        var referencia = Firebase.storage
        var nombreImg = referencia.reference.child(caratula)
        nombreImg.getBytes(10024*10024)
            .addOnSuccessListener {
                val bit = BitmapFactory.decodeByteArray(it, 0, it.size)
                //Log.i("Firebase-Imagen", "Imagen recuperada->  ${dataDir}" )
                var imagen=findViewById<ImageView>(R.id.iv_caratula)
                    .setImageBitmap(bit)
            }
            .addOnFailureListener {

            }
    }

    fun cargarInfo(){
        Log.i("cancion", "id ${Pos}")
        //var idCancion = listadoCanciones[Pos].id
        var nombreCancion = listadoCanciones[Pos].nombre
        var artistaCancion = listadoCanciones[Pos].artista
        var caratulaCancion = listadoCanciones[Pos].caratula
        var pistalaCancion = listadoCanciones[Pos].pista

        var nombreReproductor = findViewById<TextView>(R.id.tv_nombre_cancion)
        nombreReproductor.text = nombreCancion

        var artistaReproductor = findViewById<TextView>(R.id.tv_nombre_artista)
        artistaReproductor.text = artistaCancion.toString()



        esFavorito()


        cargarImagen(caratulaCancion.toString())
        reproducir(pistalaCancion.toString())

    }

    fun Random.nextInt(range: IntRange): Int {
        return range.start + nextInt(range.last - range.start)
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