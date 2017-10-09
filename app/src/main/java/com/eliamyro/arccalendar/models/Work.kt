package com.eliamyro.arccalendar.models

import android.os.Parcel
import android.os.Parcelable
import kotlin.collections.ArrayList


/**
 * Created by Elias Myronidis on 4/9/17.
 */
data class Work(val workDate: String = "", val description: String = "", val directorsList: List<String> = ArrayList(),
                val archaeologistsList: List<String> = ArrayList(),
                val studentsList: List<String> = ArrayList()) : Parcelable {

    constructor(source: Parcel) : this(
            source.readString(),
            source.readString(),
            source.createStringArrayList(),
            source.createStringArrayList(),
            source.createStringArrayList()
    )

    override fun describeContents() = 0

    override fun writeToParcel(dest: Parcel, flags: Int) = with(dest) {
        writeString(workDate)
        writeString(description)
        writeStringList(directorsList)
        writeStringList(archaeologistsList)
        writeStringList(studentsList)
    }

    companion object {
        @JvmField
        val CREATOR: Parcelable.Creator<Work> = object : Parcelable.Creator<Work> {
            override fun createFromParcel(source: Parcel): Work = Work(source)
            override fun newArray(size: Int): Array<Work?> = arrayOfNulls(size)
        }
    }
}