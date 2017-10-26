package com.eliamyro.arccalendar.contracts

import android.widget.ImageView
import com.eliamyro.arccalendar.models.Finding

/**
 * Created by Elias Myronidis on 24/10/17.
 */
abstract class ContractDialogAddFinding {

    interface Actions {
        fun saveFinding(image: ImageView, hasImage: Boolean, finding: Finding,
                        excavationItemId: String, workItemId: String, workLocationItemId: String): Boolean
    }

    interface Views {

    }
}