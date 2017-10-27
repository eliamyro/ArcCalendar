package com.eliamyro.arccalendar.contracts

import com.eliamyro.arccalendar.models.Work
import com.google.firebase.database.DatabaseReference

/**
* Created by Elias Myronidis on 21/9/17.
* LinkedIn: https://www.linkedin.com/in/eliasmyronidis/
*/

class ContractFragmentWorkInfo {

    interface Actions {
        fun loadWorkInfo(reference: DatabaseReference)
        fun destroyValueEventListener()
    }

    interface Views {
        fun displayInfo(work: Work?)
        fun removeFragment()
    }
}