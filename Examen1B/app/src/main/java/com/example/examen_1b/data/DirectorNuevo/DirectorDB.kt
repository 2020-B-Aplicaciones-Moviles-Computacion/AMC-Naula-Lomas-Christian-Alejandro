package com.example.examen_1b.data.DirectorNuevo

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.examen_1b.data.Pelicula.Pelicula
import com.example.examen_1b.data.Pelicula.PeliculaDAO

@Database(entities = arrayOf(Director::class, Pelicula::class), version = 4, exportSchema = false)
abstract class DirectorDB: RoomDatabase() {
    abstract fun directorDao(): DirectorDAO
    abstract fun peliculaDao(): PeliculaDAO

    companion object{
        @Volatile
        private var INSTANCE: DirectorDB? = null

        fun getDatabase(context: Context): DirectorDB{
            val tempInstance = INSTANCE
            if (tempInstance != null){
                return tempInstance
            }
            synchronized(this){
                val instance = Room.databaseBuilder(context.applicationContext,
                    DirectorDB::class.java, "DirectorDB").fallbackToDestructiveMigration()
                    .build()
                INSTANCE = instance
                return instance
            }
        }
    }
}