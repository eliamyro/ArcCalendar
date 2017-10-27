package com.eliamyro.arccalendar.models

import android.os.Parcel
import android.os.Parcelable

/**
* Created by Elias Myronidis on 25/9/17.
* LinkedIn: https://www.linkedin.com/in/eliasmyronidis/
*/

data class WorkLocation(val location: String = "", val description: String = ""): Parcelable {
    constructor(parcel: Parcel) : this(
            parcel.readString(),
            parcel.readString())

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(location)
        parcel.writeString(description)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<WorkLocation> {
        override fun createFromParcel(parcel: Parcel): WorkLocation {
            return WorkLocation(parcel)
        }

        override fun newArray(size: Int): Array<WorkLocation?> {
            return arrayOfNulls(size)
        }
    }
}