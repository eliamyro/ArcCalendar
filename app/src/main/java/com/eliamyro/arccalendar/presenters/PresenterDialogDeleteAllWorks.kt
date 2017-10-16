package com.eliamyro.arccalendar.presenters

import android.util.Log
import com.eliamyro.arccalendar.common.FIREBASE_LOCATION_EXCAVATION_WORKS
import com.eliamyro.arccalendar.common.FIREBASE_LOCATION_FINDINGS
import com.eliamyro.arccalendar.common.FIREBASE_LOCATION_WORK_LOCATIONS
import com.eliamyro.arccalendar.contracts.ContractDialogDeleteAllWorks.Actions
import com.eliamyro.arccalendar.contracts.ContractDialogDeleteAllWorks.Views
import com.google.firebase.database.FirebaseDatabase

/**
 * Created by Elias Myronidis on 11/10/17.
 */
class PresenterDialogDeleteAllWorks(private val mView: Views) : Actions {

    companion object {
        private val TAG: String = PresenterDialogDeleteAllWorks::class.java.simpleName
    }

    override fun deleteAllWorks(excavationItemId: String) {
        val firebaseRef = FirebaseDatabase.getInstance().reference

        val removeData: HashMap<String, Any?> = HashMap()
        removeData.put("/$FIREBASE_LOCATION_EXCAVATION_WORKS/$excavationItemId", null)

        if (FirebaseDatabase.getInstance().reference.child(FIREBASE_LOCATION_WORK_LOCATIONS) != null) {
            removeData.put("/$FIREBASE_LOCATION_WORK_LOCATIONS/$excavationItemId", null)
        }

        if (FirebaseDatabase.getInstance().reference.child(FIREBASE_LOCATION_FINDINGS) != null) {
            removeData.put("/$FIREBASE_LOCATION_FINDINGS/$excavationItemId", null)
        }

        firebaseRef.updateChildren(removeData)
        { databaseError, _ -> databaseError?.let { Log.e(TAG, "Error" + it.message) } }
    }
}
