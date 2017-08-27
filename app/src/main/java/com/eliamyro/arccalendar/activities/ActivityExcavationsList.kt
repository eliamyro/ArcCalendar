package com.eliamyro.arccalendar.activities

import android.os.Bundle
import com.eliamyro.arccalendar.R

class ActivityExcavationsList : ActivityBase() {

    companion object {
        private val TAG: String = ActivityExcavationsList::class.java.simpleName
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_excavations_list)

        configureToolbar()
    }
}
