package com.eliamyro.arccalendar.contracts

/**
* Created by Elias Myronidis on 4/10/17.
* LinkedIn: https://www.linkedin.com/in/eliasmyronidis/
*/

class ContractDialogDeleteFinding {
    interface Actions {
        fun deleteFinding(itemToDeletePath: String, findingItemId: String)
    }

    interface Views
}