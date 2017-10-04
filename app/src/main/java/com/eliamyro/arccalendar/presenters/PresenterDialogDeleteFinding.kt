package com.eliamyro.arccalendar.presenters

import android.util.Log
import com.eliamyro.arccalendar.contracts.ContractDialogDeleteFinding.Actions
import com.google.firebase.database.FirebaseDatabase

/**
 * Created by Elias Myronidis on 4/10/17.
 */
class PresenterDialogDeleteFinding: Actions {

    companion object {
        private val TAG: String = PresenterDialogDeleteFinding::class.java.simpleName
    }

    override fun deleteFinding(itemToDeletePath: String) {
        val firebaseRef = FirebaseDatabase.getInstance().reference

        val removeData: HashMap<String, Any?> = HashMap()
        removeData.put(itemToDeletePath, null)

        firebaseRef.updateChildren(removeData)
        { databaseError, _ -> databaseError?.let { Log.e(TAG, "Error" + it.message) } }
    }
}