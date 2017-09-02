package com.eliamyro.arccalendar.activities

import android.os.Bundle
import android.widget.Toast
import com.eliamyro.arccalendar.R
import com.eliamyro.arccalendar.listeners.ClickCallback

class ActivityExcavationsList : ActivityBase(), ClickCallback {

    companion object {
        private val TAG: String = ActivityExcavationsList::class.java.simpleName
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_excavations_list)

        configureToolbar()
    }

    override fun onItemSelected() {
        Toast.makeText(this, "Click callbacl", Toast.LENGTH_SHORT).show()
    }
}
