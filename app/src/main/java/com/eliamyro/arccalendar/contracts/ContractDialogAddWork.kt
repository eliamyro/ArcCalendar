package com.eliamyro.arccalendar.contracts

import com.eliamyro.arccalendar.models.Work

/**
* Created by Elias Myronidis on 6/9/17.
* LinkedIn: https://www.linkedin.com/in/eliasmyronidis/
*/

abstract class ContractDialogAddWork {

    interface Actions {
        fun addWork(excavationId: String, work: Work):Boolean
    }

    interface Views
}