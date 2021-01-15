package com.example.myapplication.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import java.util.concurrent.Flow
import com.example.myapplication.data.User

@Dao
interface UserDAO {
    @Query("SELECT * FROM User")
    suspend fun listaUsuarios(): List<User>

    @Insert
    suspend fun ingredarPersona(persona: User)

}
