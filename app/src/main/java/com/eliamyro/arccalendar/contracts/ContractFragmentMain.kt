package com.eliamyro.arccalendar.contracts

import android.content.Context

/**
 * Created by Elias Myronidis on 23/8/17.
 */
abstract class ContractFragmentMain {

    interface Actions {
        fun addExcavation(context: Context)
    }

    interface Views {
        fun displayToast(message: String)
    }
}