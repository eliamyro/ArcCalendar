package com.eliamyro.arccalendar.contracts

import android.content.Context
import com.google.firebase.database.DatabaseReference

/**
 * Created by Elias Myronidis on 23/8/17.
 */
abstract class ContractFragmentMain {

    interface Actions {
        fun addExcavation(context: Context)
        fun removeExcavation(reference: DatabaseReference, itemId: String?)
    }

    interface Views {
        fun displayToast(message: String)
    }
}