package com.eliamyro.arccalendar.presenters

import android.util.Log
import com.eliamyro.arccalendar.common.FIREBASE_LOCATION_FINDINGS
import com.eliamyro.arccalendar.common.FIREBASE_LOCATION_WORK_LOCATIONS
import com.eliamyro.arccalendar.contracts.ContractDialogDeleteAllLocations.Actions
import com.eliamyro.arccalendar.contracts.ContractDialogDeleteAllLocations.Views
import com.google.firebase.database.FirebaseDatabase

/**
* Created by Elias Myronidis on 16/10/17.
* LinkedIn: https://www.linkedin.com/in/eliasmyronidis/
*/

class PresenterDialogDeleteAllLocations(val mView: Views): Actions {

    companion object {
        private val TAG: String = PresenterDialogDeleteAllWorks::class.java.simpleName
    }

    override fun deleteAllLocations(excavationItemId: String, workItemId: String) {
        val firebaseRef = FirebaseDatabase.getInstance().reference

        val removeData: HashMap<String, Any?> = HashMap()
        removeData.put("/$FIREBASE_LOCATION_WORK_LOCATIONS/$excavationItemId/$workItemId", null)

        if (FirebaseDatabase.getInstance().reference.child(FIREBASE_LOCATION_FINDINGS) != null) {
            removeData.put("/$FIREBASE_LOCATION_FINDINGS/$excavationItemId/$workItemId", null)
        }

        firebaseRef.updateChildren(removeData)
        { databaseError, _ -> databaseError?.let { Log.e(TAG, "Error" + it.message) } }
    }
}