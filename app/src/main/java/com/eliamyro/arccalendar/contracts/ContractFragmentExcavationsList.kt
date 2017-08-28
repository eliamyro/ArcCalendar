package com.eliamyro.arccalendar.contracts

import android.content.Context
import com.eliamyro.arccalendar.models.Excavation
import com.google.firebase.database.DatabaseReference

/**
 * Created by Elias Myronidis on 23/8/17.
 */
abstract class ContractFragmentExcavationsList {

    interface Actions {
        fun showAddExcavationDialog(context: Context)
        fun removeExcavation(reference: DatabaseReference, itemId: String?)
        fun showEditExcavationDialog(excavation: Excavation?)
    }

    interface Views {
        fun displayToast(message: String)
    }
}