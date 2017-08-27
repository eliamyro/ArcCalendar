package com.eliamyro.arccalendar.presenters

import android.content.Context
import android.support.v4.app.FragmentManager
import android.util.Log
import com.eliamyro.arccalendar.common.FIREBASE_LOCATION_EXCAVATION_LISTS
import com.eliamyro.arccalendar.contracts.ContractFragmentMain.Actions
import com.eliamyro.arccalendar.contracts.ContractFragmentMain.Views
import com.eliamyro.arccalendar.dialogs.DialogAddExcavation
import com.eliamyro.arccalendar.fragments.FragmentExcavationsList
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

/**
 * Created by Elias Myronidis on 23/8/17.
 */
class PresenterFragmentExcavationsList(private val mView: Views) : Actions {

    companion object {
        private val TAG: String = PresenterFragmentExcavationsList::class.java.simpleName
    }

    override fun addExcavation(context: Context) {

        val fragmentManager: FragmentManager = (mView as FragmentExcavationsList).fragmentManager
        val dialog = DialogAddExcavation()
        val transaction = fragmentManager.beginTransaction()
        transaction.setTransition(android.support.v4.app.FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
        transaction.replace(android.R.id.content, dialog)
                .addToBackStack(null).commit()
    }

    override fun removeExcavation(reference: DatabaseReference, itemId: String?) {
        val removeData: HashMap<String, Any?> = HashMap()
        removeData.put("/" + FIREBASE_LOCATION_EXCAVATION_LISTS + "/"
                + itemId, null)

        val firebaseRef = FirebaseDatabase.getInstance().reference

        firebaseRef.updateChildren(removeData) { databaseError, _ ->
            if (databaseError != null) {
                Log.e(TAG, "Error" + databaseError.message)
            }
        }

        //TODO: Send message to FragmentExcavationsList
    }
}