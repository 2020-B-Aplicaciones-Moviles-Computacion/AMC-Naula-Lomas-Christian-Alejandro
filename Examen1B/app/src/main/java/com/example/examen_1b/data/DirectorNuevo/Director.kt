package com.example.examen_1b.data.DirectorNuevo

import android.os.Parcel
import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Director(
    @PrimaryKey(autoGenerate = true)
    val idDirector: Int?,
    var nombre: String?,
    var apellido: String?,
    var nacionalidad: String?,
    var edad: Int?,
    var correo: String?
):Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readValue(Int::class.java.classLoader) as? Int,
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readValue(Int::class.java.classLoader) as? Int,
        parcel.readString()
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeValue(idDirector)
        parcel.writeString(nombre)
        parcel.writeString(apellido)
        parcel.writeString(nacionalidad)
        parcel.writeValue(edad)
        parcel.writeString(correo)
    }

    override fun describeContents(): Int {
        return 0
    }

    override fun toString(): String {
        return "${nombre} ${apellido}"
    }

    companion object CREATOR : Parcelable.Creator<Director> {
        override fun createFromParcel(parcel: Parcel): Director {
            return Director(parcel)
        }

        override fun newArray(size: Int): Array<Director?> {
            return arrayOfNulls(size)
        }
    }
}
