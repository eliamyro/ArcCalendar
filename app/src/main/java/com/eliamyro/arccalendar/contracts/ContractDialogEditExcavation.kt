package com.eliamyro.arccalendar.contracts

import com.eliamyro.arccalendar.models.Excavation

/**
* Created by Elias Myronidis on 28/8/17.
* LinkedIn: https://www.linkedin.com/in/eliasmyronidis/
*/

class ContractDialogEditExcavation {

    interface Actions {
        fun editExcavation(excavationItemId: String, excavation: Excavation): Boolean
    }

    interface Views
}