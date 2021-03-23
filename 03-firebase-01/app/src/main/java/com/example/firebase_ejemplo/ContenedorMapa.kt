package com.example.firebase_ejemplo

import android.app.Activity
import android.content.pm.PackageManager
import android.icu.text.CaseMap
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.*
import com.google.type.LatLng

class ContenedorMapa : AppCompatActivity(),
    OnMapReadyCallback,
        GoogleMap.OnCameraMoveListener,
        GoogleMap.OnCameraMoveStartedListener,
        GoogleMap.OnCameraIdleListener,
        GoogleMap.OnPolygonClickListener,
        GoogleMap.OnPolylineClickListener,
        GoogleMap.OnMarkerClickListener
{


    private lateinit var mMap: GoogleMap
    var tienePermisos = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_contenedor_mapa)

        solicitarPermisos()
        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment

        mapFragment.getMapAsync(this)

        var caronlina = findViewById<Button>(R.id.btn_carolina)
            .setOnClickListener {
                val quicentro = com.google.android.gms.maps.model.LatLng(-0.1833203719393055, -78.48433746949604)
                val titulo = "Carolina"
                val zoom = 17f
                moverCamaraConZoom(quicentro, zoom)
            }

    }

    override fun onMapReady(googleMap: GoogleMap?) {
        if(googleMap != null){
            mMap = googleMap
            establecerConfiguracionMapa(googleMap)
            val quicentro = com.google.android.gms.maps.model.LatLng(-0.1763322809489942, -78.48006908851866)
            val titulo = "Quicentro"
            val zoom = 17f

            val poliLinea = googleMap
                .addPolyline(
                    PolylineOptions()
                        .clickable(true)
                        .add(
                            com.google.android.gms.maps.model.LatLng(-0.17296860283236784, -78.48033947199211),
                            com.google.android.gms.maps.model.LatLng(-0.1746425319916606, -78.48023108759365),
                            com.google.android.gms.maps.model.LatLng(-0.1757986268636537, -78.48011066048426),
                            )
                )
            poliLinea.tag = "Línea-1"

            var poligono = googleMap
                .addPolygon(
                    PolygonOptions()
                        .clickable(true)
                        .add(
                            com.google.android.gms.maps.model.LatLng(-0.17690052972192485, -78.48074892416403),
                            com.google.android.gms.maps.model.LatLng(-0.17522660076405028, -78.47956271713647),
                            com.google.android.gms.maps.model.LatLng(-0.17772545149102242, -78.47844876637458),
                        )
                )
            poligono.fillColor = -0xc771c4
            poligono.tag = "Poligono-1"


            /*-0.17690052972192485, -78.48074892416403

            -0.17522660076405028, -78.47956271713647

            -0.17772545149102242, -78.47844876637458*/

            anadirMarcador(quicentro, titulo)
            moverCamaraConZoom(quicentro, zoom)
        }
    }

    fun moverCamaraConZoom(coordenadas: com.google.android.gms.maps.model.LatLng, zoom: Float = 10f){
        mMap.moveCamera(
            CameraUpdateFactory
                .newLatLngZoom(coordenadas, zoom)
        )
    }

    fun anadirMarcador(coordenadas: com.google.android.gms.maps.model.LatLng, title: String){
        mMap.addMarker(
            MarkerOptions()
                .position(coordenadas)
                .title(title)
        )
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

    override fun onCameraMove() {
        Log.i("mapas", "onCameraMove")
    }

    override fun onCameraMoveStarted(p0: Int) {
        Log.i("mapas", "onCameraMoveStarted")
    }

    override fun onCameraIdle() {
        Log.i("mapas", "onCameraIdle")
    }

    override fun onPolygonClick(p0: Polygon?) {
        Log.i("mapas", "onCameraIdle")
    }

    override fun onPolylineClick(p0: Polyline?) {
        Log.i("mapas", "onCameraIdle")
    }

    override fun onMarkerClick(p0: Marker?): Boolean {
        Log.i("mapas", "onCameraIdle")
        return true
    }
}