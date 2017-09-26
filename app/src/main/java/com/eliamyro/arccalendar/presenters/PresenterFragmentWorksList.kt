package com.eliamyro.arccalendar.presenters

import android.util.Log
import com.eliamyro.arccalendar.common.FIREBASE_LOCATION_EXCAVATION_WORKS
import com.eliamyro.arccalendar.common.FIREBASE_LOCATION_WORK_LOCATIONS
import com.eliamyro.arccalendar.contracts.ContractFragmentWorksList
import com.google.firebase.database.FirebaseDatabase

/**
 * Created by Elias Myronidis on 26/9/17.
 */
class PresenterFragmentWorksList: ContractFragmentWorksList.Actions {

    companion object {
        private val TAG: String = PresenterFragmentWorksList::class.java.simpleName
    }
    override fun deleteWorks(excavationId: String) {
        val firebaseRef = FirebaseDatabase.getInstance().reference

        val removeData: HashMap<String, Any?> = HashMap()
        removeData.put("/$FIREBASE_LOCATION_EXCAVATION_WORKS/$excavationId", null)
        removeData.put("/$FIREBASE_LOCATION_WORK_LOCATIONS/$excavationId", null)

        firebaseRef.updateChildren(removeData)
        { databaseError, _ -> databaseError?.let { Log.e(TAG, "Error" + it.message) } }
    }
}