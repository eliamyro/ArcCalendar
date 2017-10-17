package com.eliamyro.arccalendar.presenters

import android.util.Log
import com.eliamyro.arccalendar.common.FIREBASE_LOCATION_FINDINGS
import com.eliamyro.arccalendar.common.FIREBASE_LOCATION_WORK_LOCATIONS
import com.eliamyro.arccalendar.contracts.ContractDialogDeleteWorkLocation.Actions
import com.eliamyro.arccalendar.contracts.ContractDialogDeleteWorkLocation.Views
import com.google.firebase.database.FirebaseDatabase

/**
 * Created by Elias Myronidis on 17/10/17.
 */
class PresenterDialogDeleteWorkLocation(val mView: Views): Actions {

    companion object {
        private val TAG: String = PresenterDialogDeleteWorkLocation::class.java.simpleName
    }

    override fun deleteWorkLocation(excavationItemId: String, workItemId: String, workLocationItemId: String) {
        val firebaseRef = FirebaseDatabase.getInstance().reference

        val removeData: HashMap<String, Any?> = HashMap()
        removeData.put("/$FIREBASE_LOCATION_WORK_LOCATIONS/$excavationItemId/$workItemId/$workLocationItemId", null)

        if (FirebaseDatabase.getInstance().reference.child(FIREBASE_LOCATION_FINDINGS) != null) {
            removeData.put("/$FIREBASE_LOCATION_FINDINGS/$excavationItemId/$workItemId/$workLocationItemId", null)
        }

        firebaseRef.updateChildren(removeData)
        { databaseError, _ -> databaseError?.let { Log.e(TAG, "Error" + it.message) } }
    }
}