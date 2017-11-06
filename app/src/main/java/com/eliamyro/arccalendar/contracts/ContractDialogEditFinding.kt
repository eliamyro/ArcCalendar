package com.eliamyro.arccalendar.contracts

import android.widget.ImageView
import com.eliamyro.arccalendar.models.Finding

/**
 * Created by Elias Myronidis on 27/10/17.
 * LinkedIn: https://www.linkedin.com/in/eliasmyronidis/
 */

abstract class ContractDialogEditFinding {

    interface Actions {
        fun updateFinding(image: ImageView, hasImage: Boolean, finding: Finding,
                          excavationItemId: String, workItemId: String, workLocationItemId: String,
                          findingItemId: String): Boolean
    }

    interface Views
}