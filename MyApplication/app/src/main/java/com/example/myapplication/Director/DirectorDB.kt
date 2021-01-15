package com.example.myapplication.Director

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [Director::class], version = 1)
abstract class DirectorDB: RoomDatabase() {
    abstract fun directorDao(): DirectorDAO
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
                    DirectorDB::class.java, "DirectorDB").build()
                INSTANCE = instance
                return instance
            }
        }
    }
}