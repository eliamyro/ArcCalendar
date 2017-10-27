package com.eliamyro.arccalendar.presenters

import android.util.Log
import com.eliamyro.arccalendar.contracts.ContractDialogDeleteFinding.Actions
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage



/**
 * Created by Elias Myronidis on 4/10/17.
 */
class PresenterDialogDeleteFinding: Actions {

    companion object {
        private val TAG: String = PresenterDialogDeleteFinding::class.java.simpleName
    }

    override fun deleteFinding(itemToDeletePath: String, findingItemId: String) {

        // Delete image from storage
        val storageRef = FirebaseStorage.getInstance().reference
        val imageRef = storageRef.child("images/$findingItemId")
        imageRef.delete()

        // Delete Finding item from database
        val firebaseRef = FirebaseDatabase.getInstance().reference

        val removeData: HashMap<String, Any?> = HashMap()
        removeData.put(itemToDeletePath, null)
        Log.d(TAG, itemToDeletePath)

        firebaseRef.updateChildren(removeData)
        { databaseError, _ -> databaseError?.let { Log.e(TAG, "Error" + it.message) } }


    }
}