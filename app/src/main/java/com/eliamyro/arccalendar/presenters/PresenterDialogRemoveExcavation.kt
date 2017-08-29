package com.eliamyro.arccalendar.presenters

import android.util.Log
import com.eliamyro.arccalendar.common.FIREBASE_LOCATION_EXCAVATION_LISTS
import com.eliamyro.arccalendar.contracts.ContractDialogRemoveExcavation.Actions
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

/**
 * Created by Elias Myronidis on 29/8/17.
 */
class PresenterDialogRemoveExcavation : Actions {

    companion object {
        private val TAG: String = PresenterDialogRemoveExcavation::class.java.simpleName
    }

    override fun removeExcavation(itemId: String?) {
        val reference: DatabaseReference = FirebaseDatabase.getInstance().reference.child(FIREBASE_LOCATION_EXCAVATION_LISTS)

        val removeData: HashMap<String, Any?> = HashMap()
        removeData.put("/" + FIREBASE_LOCATION_EXCAVATION_LISTS + "/"
                + itemId, null)

        val firebaseRef = FirebaseDatabase.getInstance().reference

        firebaseRef.updateChildren(removeData)
        { databaseError, _ -> databaseError?.let { Log.e(TAG, "Error" + it.message) } }
    }
}