package com.example.firebase_ejemplo

import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.util.Log
import android.widget.Button
import android.widget.ImageView
import androidx.core.app.ActivityCompat
import androidx.core.app.ActivityCompat.startActivityForResult
import androidx.core.content.FileProvider
import java.io.File
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

                val imagen=findViewById<ImageView>(R.id.im_mostrarImagen)
                    .setImageBitmap(takenimage)
            }
            104 -> { // IMAGEN ALMACENADA
                var uri: Uri? = data?.data
                var imaguri: Uri? = uri
                /*if(imguri!=nu){

                }*/
                val imagen=findViewById<ImageView>(R.id.im_mostrarImagen)
                //imagen.setImageURI(imguri)
                val btmap: Bitmap =
                        MediaStore.Images.Media.getBitmap(this.contentResolver, imaguri)
                imagen.setImageBitmap(btmap)
            }
        }
    }

}

