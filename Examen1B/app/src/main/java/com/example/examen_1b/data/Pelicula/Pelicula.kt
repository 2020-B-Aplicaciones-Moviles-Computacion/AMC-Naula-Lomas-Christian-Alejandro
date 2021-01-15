package com.example.examen_1b.data.Pelicula

import android.os.Parcel
import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Pelicula(
    @PrimaryKey(autoGenerate = true)
    var idPelicula: Int?,
    var nombre: String?,
    var genero: String?,
    var duracion: Int?,
    var año: Int?,
    var idDirector: Int?
): Parcelable {

    constructor(parcel: Parcel) : this(
        parcel.readInt(),
        parcel.readString(),
        parcel.readString(),
        parcel.readInt(),
        parcel.readInt(),
        parcel.readInt()
    ) {
    }

    override fun toString(): String {
        return "${nombre}"
    }


    override fun writeToParcel(parcel: Parcel, flags: Int) {
        idPelicula?.let { parcel.writeInt(it) }
        parcel.writeString(nombre)
        parcel.writeString(genero)
        duracion?.let { parcel.writeInt(it) }
        año?.let { parcel.writeInt(it) }
        if (idDirector != null) {
            parcel.writeInt(idDirector!!)
        }
    }

    override fun describeContents(): Int {
        return 0
    }


    companion object CREATOR : Parcelable.Creator<Pelicula> {
        override fun createFromParcel(parcel: Parcel): Pelicula {
            return Pelicula(parcel)
        }

        override fun newArray(size: Int): Array<Pelicula?> {
            return arrayOfNulls(size)
        }
    }
}