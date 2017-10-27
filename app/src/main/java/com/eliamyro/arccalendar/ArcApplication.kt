package com.eliamyro.arccalendar

import android.app.Application
import com.google.firebase.database.FirebaseDatabase

/**
* Created by Elias Myronidis on 9/10/17.
* LinkedIn: https://www.linkedin.com/in/eliasmyronidis/
*/

class ArcApplication: Application() {

    override fun onCreate() {
        super.onCreate()

        FirebaseDatabase.getInstance().setPersistenceEnabled(true)
    }
}