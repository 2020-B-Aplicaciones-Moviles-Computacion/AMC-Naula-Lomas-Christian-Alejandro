package com.example.proyecto_2b

import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.TextView
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.example.proyecto_2b.Clases.Data.Artista
import com.example.proyecto_2b.Clases.Data.Cancion_lista
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.MarkerOptions
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.type.LatLng

class Artistas : AppCompatActivity(), OnMapReadyCallback {

    val arregloArtistas = arrayListOf<Artista>()
    var adaptadorArtistas: ArrayAdapter<Any>? = null
    var ArtistaSeleccionado: Artista? =  null
    var latitud : Double? = null
    var longitud : Double? = null


    private lateinit var mapa: GoogleMap
    var tienePermisos = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_artistas)

        if (adaptadorArtistas == null){
            adaptadorArtistas = ArrayAdapter(
                this,
                android.R.layout.simple_spinner_item,
                arregloArtistas as List<Artista>
            )
            adaptadorArtistas?.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            cargarArtistas()
        }

        solicitarPermisos()
        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)

    }

    fun cargarArtistas() {
        val spinnerRestaurantes = findViewById<Spinner>(R.id.sp_artistas)
        spinnerRestaurantes.adapter = adaptadorArtistas
        spinnerRestaurantes.onItemSelectedListener = object : AdapterView.OnItemSelectedListener
        {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ){
                ArtistaSeleccionado = arregloArtistas[position]
                latitud = ArtistaSeleccionado?.coordenadas?.get(0)?.toDouble()
                longitud = ArtistaSeleccionado?.coordenadas?.get(1)?.toDouble()

                var ubicacion = com.google.android.gms.maps.model.LatLng(
                    ArtistaSeleccionado?.coordenadas!![0] as Double,
                    ArtistaSeleccionado?.coordenadas!![1] as Double,
                )
                moverCamaraConZoom(ubicacion, 5f)
                anadirMarcador(ubicacion, ArtistaSeleccionado?.nombre.toString())

                var pais = findViewById<TextView>(R.id.tv_pais)
                pais.text = ArtistaSeleccionado?.pais.toString()

            }
            override fun onNothingSelected(parent: AdapterView<*>){
                Log.i("firebase-firestore", "No seleccionó nada")
            }

            var db = Firebase.firestore
            var resultado = db
                .collection("artista")
                .orderBy("nombre")
                .get()
                .addOnSuccessListener {
                    for (artista in it){
                        var artista = Artista(artista.id,
                            artista.get("nombre").toString(),
                            artista.get("pais").toString(),
                            artista.get("coordenadas") as ArrayList<Double>,
                            );
                        arregloArtistas.add(artista)
                        adaptadorArtistas?.notifyDataSetChanged()
                    }

                }
        }
    }


    override fun onMapReady(googleMap: GoogleMap?) {
        if(googleMap != null){
            mapa = googleMap
            establecerConfiguracionMapa(mapa)
            if (longitud != null && latitud != null){
                var ubicacion = com.google.android.gms.maps.model.LatLng(
                    ArtistaSeleccionado?.coordenadas!![0] as Double,
                    ArtistaSeleccionado?.coordenadas!![1] as Double,
                )
                moverCamaraConZoom(ubicacion, 5f)
            }

        }
    }

    fun establecerConfiguracionMapa(mapa: GoogleMap){
        val contexto = this.applicationContext
        with(mapa){
            val permisosFineLocation = ContextCompat
                .checkSelfPermission(
                    contexto,
                    android.Manifest.permission.ACCESS_FINE_LOCATION
                )
            val tienePermisos = permisosFineLocation == PackageManager.PERMISSION_GRANTED
            if(tienePermisos){
                mapa.isMyLocationEnabled = true
            }
            uiSettings.isZoomControlsEnabled = true
            uiSettings.isMyLocationButtonEnabled = true
        }
    }

    fun moverCamaraConZoom(coordenadas: com.google.android.gms.maps.model.LatLng, zoom: Float = 10f){
        mapa.moveCamera(
            CameraUpdateFactory
                .newLatLngZoom(coordenadas, zoom)
        )
    }

    fun anadirMarcador(coordenadas: com.google.android.gms.maps.model.LatLng, title: String){
        mapa.clear()
        mapa.addMarker(
            MarkerOptions()
                .position(coordenadas)
                .title(title)
        )

    }

    fun solicitarPermisos(){
        val permisosFineLocation = ContextCompat
            .checkSelfPermission(
                this.applicationContext,
                android.Manifest.permission.ACCESS_FINE_LOCATION
            )
        val tienePermisos = permisosFineLocation == PackageManager.PERMISSION_GRANTED
        if(tienePermisos){
            Log.i("mapas", "Tiene permisos FINE LOCATION")
            this.tienePermisos = true
        }
        else{
            ActivityCompat.requestPermissions(
                this,
                arrayOf(
                    android.Manifest.permission.ACCESS_FINE_LOCATION
                ),
                1
            )
        }
    }
}