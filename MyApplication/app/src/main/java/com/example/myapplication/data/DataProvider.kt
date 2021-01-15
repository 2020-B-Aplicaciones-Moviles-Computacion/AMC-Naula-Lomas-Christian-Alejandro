package com.example.myapplication.data

import android.content.Context
import androidx.room.Room
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class DataProvider {
    /*fun listaDatos(userdao: UserDAO):List<User>{
        val db = Room.databaseBuilder(this, UserDataBase::class.java, "Base").build()
    }*/

    suspend fun listaDatos(context: Context):List<User>{
        val db = Room.databaseBuilder(context.applicationContext, UserDataBase::class.java, "name").build()
        return withContext(Dispatchers.IO){
            db.userDao().listaUsuarios()
        }
    }
}
