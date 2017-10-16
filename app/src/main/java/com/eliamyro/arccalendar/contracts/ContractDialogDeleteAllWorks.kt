package com.eliamyro.arccalendar.contracts

/**
 * Created by Elias Myronidis on 11/10/17.
 */
abstract class ContractDialogDeleteAllWorks {

    interface Actions {
        fun deleteAllWorks(excavationItemId: String)
    }

    interface Views
}