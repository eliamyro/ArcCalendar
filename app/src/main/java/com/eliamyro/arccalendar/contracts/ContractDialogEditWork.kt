package com.eliamyro.arccalendar.contracts

import com.eliamyro.arccalendar.models.Work

/**
 * Created by Elias Myronidis on 16/10/17.
 */
abstract class ContractDialogEditWork {

    interface Actions {
        fun editWork(excavationItemId: String, workItemId: String, work: Work): Boolean
    }

    interface Views {

    }
}