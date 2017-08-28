package com.eliamyro.arccalendar.presenters

import android.content.Context
import android.os.Bundle
import android.support.v4.app.FragmentManager
import android.util.Log
import com.eliamyro.arccalendar.R
import com.eliamyro.arccalendar.common.FIREBASE_LOCATION_EXCAVATION_LISTS
import com.eliamyro.arccalendar.contracts.ContractFragmentExcavationsList.Actions
import com.eliamyro.arccalendar.contracts.ContractFragmentExcavationsList.Views
import com.eliamyro.arccalendar.dialogs.DialogAddExcavation
import com.eliamyro.arccalendar.dialogs.DialogEditExcavation
import com.eliamyro.arccalendar.fragments.FragmentExcavationsList
import com.eliamyro.arccalendar.models.Excavation
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

/**
 * Created by Elias Myronidis on 23/8/17.
 */
class PresenterFragmentExcavationsList(private val mView: Views) : Actions {

    companion object {
        private val TAG: String = PresenterFragmentExcavationsList::class.java.simpleName
        private const val KEY_EXCAVATION: String = "excavation"
    }

    override fun showAddExcavationDialog(context: Context) {
        val fragmentManager: FragmentManager = (mView as FragmentExcavationsList).fragmentManager
        val dialog = DialogAddExcavation()
        val transaction = fragmentManager.beginTransaction()
        transaction.setTransition(android.support.v4.app.FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
        transaction.setCustomAnimations(R.anim.right_in, R.anim.left_out, R.anim.left_in, R.anim.right_out)
        transaction.replace(android.R.id.content, dialog)
                .addToBackStack(null).commit()
    }

    override fun removeExcavation(reference: DatabaseReference, itemId: String?) {
        val removeData: HashMap<String, Any?> = HashMap()
        removeData.put("/" + FIREBASE_LOCATION_EXCAVATION_LISTS + "/"
                + itemId, null)

        val firebaseRef = FirebaseDatabase.getInstance().reference

        firebaseRef.updateChildren(removeData)
        { databaseError, _ -> databaseError?.let { Log.e(TAG, "Error" + it.message) } }
    }

    override fun showEditExcavationDialog(excavation: Excavation?) {
        val fragmentManager: FragmentManager = (mView as FragmentExcavationsList).fragmentManager
        val dialog = DialogEditExcavation()
        val args = Bundle()
        args.putParcelable(KEY_EXCAVATION, excavation)
        dialog.arguments = args

        val transaction = fragmentManager.beginTransaction()
        transaction.setTransition(android.support.v4.app.FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
        transaction.setCustomAnimations(R.anim.right_in, R.anim.left_out, R.anim.left_in, R.anim.right_out)
        transaction.replace(android.R.id.content, dialog)
                .addToBackStack(null).commit()
    }
}