package com.eliamyro.arccalendar.presenters

import android.util.Log
import com.eliamyro.arccalendar.common.FIREBASE_LOCATION_EXCAVATION_LISTS
import com.eliamyro.arccalendar.common.FIREBASE_LOCATION_EXCAVATION_WORKS
import com.eliamyro.arccalendar.common.FIREBASE_LOCATION_WORK_LOCATIONS
import com.eliamyro.arccalendar.contracts.ContractDialogDeleteExcavation.Actions
import com.google.firebase.database.FirebaseDatabase

/**
 * Created by Elias Myronidis on 29/8/17.
 */
class PresenterDialogDeleteExcavation : Actions {

    companion object {
        private val TAG: String = PresenterDialogDeleteExcavation::class.java.simpleName
    }

    override fun deleteExcavation(itemId: String?) {
        val firebaseRef = FirebaseDatabase.getInstance().reference

        val removeData: HashMap<String, Any?> = HashMap()
        removeData.put("/$FIREBASE_LOCATION_EXCAVATION_LISTS/$itemId", null)
        if (FirebaseDatabase.getInstance().reference.child(FIREBASE_LOCATION_EXCAVATION_WORKS) != null) {
            removeData.put("/$FIREBASE_LOCATION_EXCAVATION_WORKS/$itemId", null)
        }
        if (FirebaseDatabase.getInstance().reference.child(FIREBASE_LOCATION_WORK_LOCATIONS) != null) {
            removeData.put("/$FIREBASE_LOCATION_WORK_LOCATIONS/$itemId", null)
        }

        firebaseRef.updateChildren(removeData)
        { databaseError, _ -> databaseError?.let { Log.e(TAG, "Error" + it.message) } }
    }
}