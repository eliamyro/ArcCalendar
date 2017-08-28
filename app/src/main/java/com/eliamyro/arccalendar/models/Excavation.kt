package com.eliamyro.arccalendar.models

import android.os.Parcel
import android.os.Parcelable

/**
 * Created by Elias Myronidis on 24/8/17.
 */
data class Excavation(var place: String = "", var organization: String = ""): Parcelable {
    constructor(parcel: Parcel) : this(
            parcel.readString(),
            parcel.readString())

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(place)
        parcel.writeString(organization)
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