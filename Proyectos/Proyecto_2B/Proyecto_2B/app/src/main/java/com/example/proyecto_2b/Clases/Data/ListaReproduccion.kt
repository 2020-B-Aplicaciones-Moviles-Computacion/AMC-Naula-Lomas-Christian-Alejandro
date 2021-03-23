package com.example.proyecto_2b.Clases.Data

import android.os.Parcel
import android.os.Parcelable

class ListaReproduccion(
        var id: String?,
        var nombre: String?,
        //var cancion: Cancion_lista,
): Parcelable {
    constructor(parcel: Parcel) : this(
            parcel.readString(),
            parcel.readString()) {
    }

    override fun toString(): String {
        return "${nombre}"
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(nombre)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<ListaReproduccion> {
        override fun createFromParcel(parcel: Parcel): ListaReproduccion {
            return ListaReproduccion(parcel)
        }

        override fun newArray(size: Int): Array<ListaReproduccion?> {
            return arrayOfNulls(size)
        }
    }
}