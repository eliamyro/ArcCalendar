package com.eliamyro.arccalendar.contracts

/**
* Created by Elias Myronidis on 16/10/17.
* LinkedIn: https://www.linkedin.com/in/eliasmyronidis/
*/

abstract class ContractDialogDeleteAllLocations {

    interface Actions {
        fun deleteAllLocations(excavationItemId: String, workItemId: String)
    }

    interface Views
}