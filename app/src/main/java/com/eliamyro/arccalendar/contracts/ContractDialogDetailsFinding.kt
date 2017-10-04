package com.eliamyro.arccalendar.contracts

import com.eliamyro.arccalendar.models.Finding

/**
 * Created by Elias Myronidis on 4/10/17.
 */

class ContractDialogDetailsFinding {
    interface Actions {
        fun loadFindingDetails(itemId: String)
    }

    interface Views {
        fun removeFragment()
        fun updateFinding(finding: Finding?)
    }
}