package com.example.proyecto_2b.Clases.Servicios

import android.app.Service
import android.content.Intent
import android.media.MediaPlayer
import android.os.IBinder

class Musica : Service() {

    var  mp: MediaPlayer = MediaPlayer()

    override fun onBind(intent: Intent): IBinder {
        TODO("Return the communication channel to the service.")
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        //return super.onStartCommand(intent, flags, startId)
        
        return START_STICKY
    }

    override fun onDestroy() {
        super.onDestroy()
    }

}