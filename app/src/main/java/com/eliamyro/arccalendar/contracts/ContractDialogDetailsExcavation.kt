package com.eliamyro.arccalendar.contracts

import com.eliamyro.arccalendar.models.Excavation

/**
 * Created by Elias Myronidis on 30/8/17.
 */
class ContractDialogDetailsExcavation {

    interface Actions {
        fun loadExcavationDetails(itemId: String)
    }

    interface Views {
        fun removeFragment()
        fun updateExcavation(excavation: Excavation?)
    }
}