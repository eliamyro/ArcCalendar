package com.eliamyro.arccalendar.contracts

import com.eliamyro.arccalendar.models.WorkLocation

/**
 * Created by Elias Myronidis on 23/10/17.
 */
abstract class ContractDialogEditWorkLocation {

    interface Actions {
        fun editWorkLocation(excavationItemId: String, workItemId: String,
                             workLocationItemId: String, workLocation: WorkLocation): Boolean
    }

    interface Views {

    }
}