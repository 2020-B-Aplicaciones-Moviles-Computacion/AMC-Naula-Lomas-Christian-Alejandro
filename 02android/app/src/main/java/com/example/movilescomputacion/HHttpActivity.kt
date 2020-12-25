package com.example.movilescomputacion

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.ResultReceiver
import android.util.Log
import com.beust.klaxon.Klaxon
import com.github.kittinunf.fuel.httpGet
import com.github.kittinunf.fuel.httpPost
import com.github.kittinunf.result.Result

class HHttpActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_h_http)

        //metodoGet()
        metodoPost()

    }


    fun metodoGet() {
        /*"https://jsonplaceholder.typicode.com/posts/1"
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
            }*/
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
}
