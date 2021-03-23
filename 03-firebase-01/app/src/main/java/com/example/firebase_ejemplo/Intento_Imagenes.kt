package com.example.firebase_ejemplo

import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.media.MediaPlayer
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.util.Log
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.core.app.ActivityCompat
import androidx.core.app.ActivityCompat.startActivityForResult
import androidx.core.content.FileProvider
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.FileInputStream
import java.io.FileOutputStream
import java.net.URI

class Intento_Imagenes : AppCompatActivity() {

    lateinit var photofile: File
    var PhotoNombre = "img1.jpg"
    var imgUri: URI? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_intento__imagenes)

        val btnImagen = findViewById<Button>(R.id.btn_obtenerImagen)
                .setOnClickListener { obtenerImagen() }

        val btnFoto = findViewById<Button>(R.id.btn_tomarFoto)
                .setOnClickListener { tomarfoto() }

        var bntDescarga = findViewById<Button>(R.id.btn_descargar)
                .setOnClickListener {
                    //llenaPath()
                    obtenecancion()
                }
    }

    fun obtenerImagen(){
        val intent = Intent(Intent.ACTION_OPEN_DOCUMENT)
        intent.type = "image/*"
        startActivityForResult(intent, 104)
    }

    fun tomarfoto(){
        val intentFoto = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        startActivityForResult(intentFoto, 103)
    }

    /*fun tomarfotoHD(){
        val intentFoto = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        photofile = getPhotoFile(PhotoNombre)
        //intentFoto.putExtra(MediaStore.EXTRA_OUTPUT, photofile)
        val fileprov = FileProvider.getUriForFile(this, "com.example.fileprovider", photofile)
        intentFoto.putExtra(MediaStore.EXTRA_OUTPUT, fileprov)
        startActivityForResult(intentFoto, 103)
    }*/

    /*private fun getPhotoFile(nombre: String): File {
        val almacenamiento = getExternalFilesDir(Environment.DIRECTORY_PICTURES)
        return File.createTempFile(nombre,"jpg", almacenamiento)
    }*/

    override fun onActivityResult(
        requestCode: Int,  // Codigo de petición  - Codigo: 102
        resultCode: Int,  //  Codigo de Resultado - RESULT_OK o RESULT_CANCELED
        data: Intent?  // Datos (opcionales)
    ) {
        super.onActivityResult(requestCode, resultCode, data)

        when (requestCode) {
            103 -> { // FOTOGRAFIA
                val takenimage = data?.extras?.get("data") as Bitmap

                var imagen=findViewById<ImageView>(R.id.im_mostrarImagen)
                    .setImageBitmap(takenimage)

                var btnCargar = findViewById<Button>(R.id.btn_subirImagen2)
                    .setOnClickListener {
                        cargarimagen(takenimage)
                    }
            }

            104 -> { // IMAGEN ALMACENADA
                var uri: Uri? = data?.data
                var imaguri: Uri? = uri
                val imagen=findViewById<ImageView>(R.id.im_mostrarImagen)
                //imagen.setImageURI(imguri)
                val btmap: Bitmap =
                        MediaStore.Images.Media.getBitmap(this.contentResolver, imaguri)
                imagen.setImageBitmap(btmap)

                var btnCargar = findViewById<Button>(R.id.btn_subirImagen1)
                    .setOnClickListener {
                        cargarimagen(btmap)
                    }
            }
        }
    }


    fun cargarimagen(img: Bitmap){

        var referencia = Firebase.storage
        var nombreImg = referencia.reference.child(img.toString())

        val baos = ByteArrayOutputStream()
        img.compress(Bitmap.CompressFormat.JPEG, 100, baos)
        val data = baos.toByteArray()
        var uploadTask = nombreImg.putBytes(data)
            .addOnFailureListener {
                Log.i("Firebase-Imagen", "Fallido")
            }
            .addOnSuccessListener {
                Log.i("Firebase-Imagen", "Imagen subida con exito")

                var imagen=findViewById<ImageView>(R.id.im_mostrarImagen)
                    .setImageBitmap(null)
            }

    }


    fun llenaPath(){

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
        
    }

    fun obtenecancion(){

        var referencia = Firebase.storage
        var nombreImg = referencia.reference.child("flora-cash-youre-somebody-else-lyrics.mp3")

        nombreImg.getBytes(10024*10024)
                .addOnSuccessListener {
                    var file:File = File.createTempFile("cancion", "mp3")
                    var fos: FileOutputStream = FileOutputStream(file)
                    fos.write(it)
                    Log.i("cancion", "FOS ${fos}")
                    Log.i("cancion", "IT ${it}")
                    fos.close()

                    var  mp: MediaPlayer = MediaPlayer()
                    Log.i("cancion", "MP ")
                    var fis: FileInputStream = FileInputStream(file)
                    mp?.setDataSource(fis.fd)
                    Log.i("cancion", "FIS.RD ${fis.fd}")

                    Log.i("cancion", "MP ${mp}")
                    mp?.prepare()
                    mp?.start()
                }
    }
}


