package com.example.myapplication.Director

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.myapplication.Director.ui.main.DirectoresFragment
import com.example.myapplication.R

class Directores : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.directores_activity)
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.container, DirectoresFragment.newInstance())
                .commitNow()
        }
    }
}