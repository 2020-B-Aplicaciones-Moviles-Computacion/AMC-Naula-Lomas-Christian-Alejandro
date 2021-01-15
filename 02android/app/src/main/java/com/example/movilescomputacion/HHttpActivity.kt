package com.example.movilescomputacion

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.ResultReceiver
import android.util.Log
import com.beust.klaxon.Klaxon
import com.github.kittinunf.fuel.*
import com.github.kittinunf.result.Result

class HHttpActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_h_http)

       // metodoGet()
        //metodoPost()
        //(1)
        //metodoDelete(1)
        metodoBusquedaID(2)

    }


    /*fun metodoGet() {
        "https://jsonplaceholder.typicode.com/posts"
            .httpGet()
            .responseString{ req, res, result ->
                when(result){
                    is Result.Failure -> {
                        val error = result.getException()
                        Log.i("http-klaxon", "Error ${error}")
                    }
                    is Result.Success -> {
                        val postString = result.get()
                        Log.i("http-klaxon", "${postString}")
                        val arregloPosts = Klaxon()
                            .parseArray<IPostHttp>(postString)
                        //Log.i("http-klaxon", "${post?.title}")
                        if (arregloPosts != null) {
                            arregloPosts.forEach {
                                Log.i("http-klaxon", "${it.title}")
                            }
                        }

                    }
                }
            }
    }*/

    fun metodoGet() {
        "https://jsonplaceholder.typicode.com/posts/1"
            .httpGet()
            .responseString{ req, res, result ->
                when(result){
                    is Result.Failure -> {
                        val error = result.getException()
                        Log.i("http-klaxon", "Error ${error}")
                    }
                    is Result.Success -> {
                        val postString = result.get()
                        Log.i("http-klaxon", "${postString}")
                        val post = Klaxon()
                            .parse<IPostHttp>(postString)
                        Log.i("http-klaxon", "${post?.title}")
                    }
                }
            }
    }


    fun metodoPost() {
        val parametros: List<Pair<String, String>> = listOf(
            "title" to "Titulo moviles",
            "body" to "descripcion moviles",
            "userId" to "1"
        )
        "https://jsonplaceholder.typicode.com/posts"
            .httpPost(parametros)
            .responseString { req, res, result ->
                when (result) {
                    is Result.Failure -> {
                        val error = result.getException()
                        Log.i("http-klaxon", "Error ${error}")
                    }
                    is Result.Success -> {
                        /*val usuarioString = result.get()
                        Log.i("http-klaxon", "${usuarioString}")*/
                        val postString = result.get()
                        Log.i("http-klaxon", "${postString}")
                        val posts = Klaxon()
                            .parse<IPostHttp>(postString)
                        Log.i("http-klaxon", "${posts?.title}")

                    }
                }
            }
    }

    fun metodoUpdate(id:Int) {
        val parametros: List<Pair<String, String>> = listOf(
            "title" to "Titulo moviles version actualizada",
            "body" to "descripcion moviles version actualizada",
            "userId" to "1"
        )
        "https://jsonplaceholder.typicode.com/posts/${id}"
            .httpPut(parametros)
            .responseString { req, res, result ->
                when (result) {
                    is Result.Failure -> {
                        val error = result.getException()
                        Log.i("http-klaxon", "Error ${error}")
                    }
                    is Result.Success -> {
                        val postString = result.get()
                        Log.i("http-klaxon", "Recurso Actualizado:${postString}")
                        val posts = Klaxon()
                            .parse<IPostHttp>(postString)
                        Log.i("http-klaxon", "titulo actualizado: ${posts?.title}")
                    }
                }
            }
    }


    fun metodoDelete(id:Int) {
        "https://jsonplaceholder.typicode.com/posts/${id}"
            .httpDelete()
            .responseString { req, res, result ->
                when (result) {
                    is Result.Failure -> {
                        val error = result.getException()
                        Log.i("http-klaxon", "Error ${error}")
                    }
                    is Result.Success -> {
                        val postString = result.get()
                        Log.i("http-klaxon", "Recurso con id ${id} eliminado: ${postString}")
                        /*val posts = Klaxon()
                            .parse<IPostHttp>(postString)
                        Log.i("http-klaxon", "null")*/

                    }
                }
            }
    }


    fun metodoBusquedaID(userId:Int) {
        "https://jsonplaceholder.typicode.com/posts?userId=${userId}"
            .httpGet()
            .responseString{ req, res, result ->
                when(result){
                    is Result.Failure -> {
                        val error = result.getException()
                        Log.i("http-klaxon", "Error ${error}")
                    }
                    is Result.Success -> {
                        val postString = result.get()
                        Log.i("http-klaxon", "${postString}")
                        val arregloPosts = Klaxon()
                            .parseArray<IPostHttp>(postString)
                        if (arregloPosts != null) {
                            arregloPosts.forEach {
                                Log.i("http-klaxon", "Titulo del recuro con id ${it.id}: ${it.title}")
                            }
                        }
                    }
                }
            }
    }
}
