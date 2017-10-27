package com.eliamyro.arccalendar.contracts

/**
* Created by Elias Myronidis on 17/10/17.
* LinkedIn: https://www.linkedin.com/in/eliasmyronidis/
*/

abstract class ContractDialogDeleteWorkLocation {

    interface Actions {
        fun deleteWorkLocation(excavationItemId: String, workItemId: String, workLocationItemId: String)
    }

    interface Views
}