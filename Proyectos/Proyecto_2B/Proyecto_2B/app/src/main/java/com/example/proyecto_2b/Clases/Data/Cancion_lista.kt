package com.example.proyecto_2b.Clases.Data

import android.os.Parcel
import android.os.Parcelable

data class Cancion_lista(var id: String?,
                         var nombre: String?,
                         var artista:List<String>?,
                         var pista: String?,
                         var caratula: String?,
): Parcelable{

    constructor(parcel: Parcel) : this(
        parcel.readString(),
        parcel.readString(),
        parcel.createStringArrayList(),
        parcel.readString(),
        parcel.readString(),

    ) {
    }

    override fun toString(): String {
        return "${nombre} - ${artista}"
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(id)
        parcel.writeString(nombre)
        parcel.writeStringList(artista)
        parcel.writeString(pista)
        parcel.writeString(caratula)

    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Cancion_lista> {
        override fun createFromParcel(parcel: Parcel): Cancion_lista {
            return Cancion_lista(parcel)
        }

        override fun newArray(size: Int): Array<Cancion_lista?> {
            return arrayOfNulls(size)
        }
    }
}
