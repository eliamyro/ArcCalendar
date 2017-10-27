package com.eliamyro.arccalendar.models

import android.os.Parcel
import android.os.Parcelable

/**
* Created by Elias Myronidis on 24/8/17.
* LinkedIn: https://www.linkedin.com/in/eliasmyronidis/
*/

data class Excavation(val place: String = "", val organisation: String = "", val description: String = ""): Parcelable {
    constructor(parcel: Parcel) : this(
            parcel.readString(),
            parcel.readString(),
            parcel.readString())

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(place)
        parcel.writeString(organisation)
        parcel.writeString(description)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Excavation> {
        override fun createFromParcel(parcel: Parcel): Excavation {
            return Excavation(parcel)
        }

        override fun newArray(size: Int): Array<Excavation?> {
            return arrayOfNulls(size)
        }
    }
}