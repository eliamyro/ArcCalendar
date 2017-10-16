package com.eliamyro.arccalendar.contracts

/**
 * Created by Elias Myronidis on 16/10/17.
 */
abstract class ContractDialogDeleteAllLocations {

    interface Actions {
        fun deleteAllLocations(excavationItemId: String, workItemId: String)
    }

    interface Views {

    }
}