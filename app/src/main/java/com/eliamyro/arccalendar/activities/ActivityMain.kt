package com.eliamyro.arccalendar.activities

import android.os.Bundle
import com.eliamyro.arccalendar.R

class ActivityMain : ActivityBase() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        configureToolbar()
    }
}
