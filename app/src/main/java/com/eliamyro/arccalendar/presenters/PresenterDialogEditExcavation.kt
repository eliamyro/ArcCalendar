package com.eliamyro.arccalendar.presenters

import android.util.Log
import com.eliamyro.arccalendar.contracts.ContractDialogEditExcavation.Actions
import com.eliamyro.arccalendar.contracts.ContractDialogEditExcavation.Views

/**
 * Created by Elias Myronidis on 28/8/17.
 */
class PresenterDialogEditExcavation(private val mView: Views): Actions {

    companion object {
        private val TAG: String = PresenterDialogEditExcavation::class.java.simpleName
    }

    override fun editExcavation() {
        Log.d(TAG, "Inside editExcavation")
    }
}