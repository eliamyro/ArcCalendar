package com.eliamyro.arccalendar.models

import android.os.Parcel
import android.os.Parcelable

/**
* Created by Elias Myronidis on 29/9/17.
* LinkedIn: https://www.linkedin.com/in/eliasmyronidis/
*/

data class Finding(var name: String = "", var description: String = "", var imageUrl: String = ""): Parcelable {
    constructor(parcel: Parcel) : this(
            parcel.readString(),
            parcel.readString(),
            parcel.readString()
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(name)
        parcel.writeString(description)
        parcel.writeString(imageUrl)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Finding> {
        override fun createFromParcel(parcel: Parcel): Finding {
            return Finding(parcel)
        }

        override fun newArray(size: Int): Array<Finding?> {
            return arrayOfNulls(size)
        }
    }
}