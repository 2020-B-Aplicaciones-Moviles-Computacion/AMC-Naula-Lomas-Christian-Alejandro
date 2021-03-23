package com.example.proyecto_2b

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.TextView
import com.example.proyecto_2b.Clases.AuthUsuario
import com.example.proyecto_2b.Clases.Usuario
import com.firebase.ui.auth.AuthUI
import com.firebase.ui.auth.IdpResponse
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

val CODIGO_INICIO_SESION = 102
val textoNoLogeado = "Dar click en Iniciar Sesión"

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        var btnIniciarSesion = findViewById<Button>(R.id.btn_iniciar_sesion)
            .setOnClickListener {
                solicitarIngresarAplicativo()
            }

        var btnSalir = findViewById<Button>(R.id.btn_salir)
            .setOnClickListener{
                solicitarSalirAplicativo()
            }


        val texto = findViewById<TextView>(R.id.tv_mensaje)

        val instanciaAuth = FirebaseAuth.getInstance()
        if (instanciaAuth.currentUser != null){
            texto.text = "${instanciaAuth.currentUser?.email}"
            setearUsuarioFirebase()
            mostrarbotonesOcultos()
        }
        else{
            texto.text = textoNoLogeado
        }

        val textos = findViewById<TextView>(R.id.tv_mensaje)

        var parametros = arrayListOf<Pair<String, String>>(
                Pair("idUsuario", textos.text.toString())
        )

        var btnIngresar = findViewById<Button>(R.id.btn_ingresar)
                .setOnClickListener {
                    irActividad(pantalla_Principal::class.java, parametros)
                }

    }


    fun solicitarIngresarAplicativo(){

        val providers = arrayListOf(
            AuthUI.IdpConfig.EmailBuilder().build())

        startActivityForResult(
            AuthUI.getInstance()
                .createSignInIntentBuilder()
                .setAvailableProviders(providers)
                .setTosAndPrivacyPolicyUrls("https://example.com/terms.html",
                    "https://example.com/privacy.html")
                .build(),
            CODIGO_INICIO_SESION)
    }

    fun solicitarSalirAplicativo(){
        AuthUI.getInstance()
            .signOut(this)
            .addOnCompleteListener{
                val texto = findViewById<TextView>(R.id.tv_mensaje)
                texto.text = textoNoLogeado

                AuthUsuario.usuario = null
                mostrarbotonesOcultos()
                Log.i("FIREBASE-login", "Salió del app")
            }
    }

    fun mostrarbotonesOcultos(){
        val botonEscondidoFirestonre = findViewById<Button>(R.id.btn_ingresar)
        if(AuthUsuario.usuario!= null){
            botonEscondidoFirestonre.visibility = View.VISIBLE
        }
        else{
            botonEscondidoFirestonre.visibility = View.INVISIBLE
        }
    }

    fun setearUsuarioFirebase(){
        val instanciaAuth = FirebaseAuth.getInstance()
        val usuarioLocal = instanciaAuth.currentUser

        if(usuarioLocal != null ){
            if(usuarioLocal!=null){
                val usuarioFirebase = Usuario(
                    usuarioLocal.uid, usuarioLocal.email!!, null
                )
                AuthUsuario.usuario = usuarioFirebase
                //cargarRolesUsuario(usuarioFirebase.email)
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        when(requestCode){
            CODIGO_INICIO_SESION -> {
                if(resultCode == Activity.RESULT_OK){
                    val usuario = IdpResponse.fromResultIntent(data)

                    if (usuario?.isNewUser == true){
                        //logica para crear un nuevo usuario en nuestra colección

                        if (usuario.email != null){
                            val db = Firebase.firestore

                            val rolesUsuario = arrayListOf("usuario")
                            val nuevoUsuario = hashMapOf<String, Any>(
                                "roles" to rolesUsuario
                            )
                            val identificadorUsuario = usuario.email.toString()
                            db.collection("usuario")
                                .document(identificadorUsuario)
                                .set(nuevoUsuario)
                                .addOnSuccessListener {

                                    setearUsuarioFirebase()
                                    mostrarbotonesOcultos()

                                    Log.i("firebase-firestore","Se creo")
                                }
                                .addOnFailureListener {
                                    Log.i("firebase-firestore","Fallo")
                                }

                        }
                    }

                    val texto = findViewById<TextView>(R.id.tv_mensaje)
                    texto.text = "${usuario?.email}"

                    setearUsuarioFirebase()
                    mostrarbotonesOcultos()

                }
                else{
                    Log.i("FIREBASE-login", "El usuario canceló")
                }
            }

        }
    }


    fun irActividad(
            clase: Class<*>,
            parametros: ArrayList<Pair<String, String>>? = null,
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

                /*tipoDato = it.second is Int // instanceOf()
                if (tipoDato == true) {
                    intentExplicito.putExtra(nombreVariable, valorVariable as Int)
                }

                tipoDato = it.second is Parcelable // instanceOf()
                if (tipoDato == true) {
                    intentExplicito.putExtra(nombreVariable, valorVariable as Parcelable)
                }*/

            }
        }
        if (codigo != null) {
            startActivityForResult(intentExplicito, codigo)
        } else {
            startActivity(intentExplicito)
        }
    }

}