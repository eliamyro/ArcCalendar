package com.eliamyro.arccalendar.activities

import android.os.Bundle
import com.eliamyro.arccalendar.R
import com.eliamyro.arccalendar.common.inTransaction
import com.eliamyro.arccalendar.fragments.FragmentSearch

/**
 * Created by Elias Myronidis on 28/8/17.
 * LinkedIn: https://www.linkedin.com/in/eliasmyronidis/
 */

class ActivitySearch : ActivityBase() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search)

        configureToolbar(true)

        supportFragmentManager.inTransaction(false) { add(R.id.fragment_search_container, FragmentSearch()) }
    }

}
