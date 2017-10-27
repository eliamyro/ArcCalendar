package com.eliamyro.arccalendar.presenters

import android.util.Log
import com.eliamyro.arccalendar.common.FIREBASE_LOCATION_EXCAVATION_LISTS
import com.eliamyro.arccalendar.common.FIREBASE_PROPERTY_EXCAVATION_DESCRIPTION
import com.eliamyro.arccalendar.common.FIREBASE_PROPERTY_EXCAVATION_ORGANISATION
import com.eliamyro.arccalendar.common.FIREBASE_PROPERTY_EXCAVATION_PLACE
import com.eliamyro.arccalendar.contracts.ContractDialogEditExcavation.Actions
import com.eliamyro.arccalendar.contracts.ContractDialogEditExcavation.Views
import com.eliamyro.arccalendar.models.Excavation
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

/**
* Created by Elias Myronidis on 28/8/17.
* LinkedIn: https://www.linkedin.com/in/eliasmyronidis/
*/

class PresenterDialogEditExcavation(private val mView: Views) : Actions {

    companion object {
        private val TAG: String = PresenterDialogEditExcavation::class.java.simpleName
    }

    override fun editExcavation(excavationItemId: String, excavation: Excavation): Boolean {
        Log.d(TAG, "Inside editExcavation")

        var isUpdateSuccessful = true

        val reference: DatabaseReference = FirebaseDatabase.getInstance().reference

        val updatedExcavationMap = HashMap<String, Any>()
        updatedExcavationMap.put("/" + FIREBASE_LOCATION_EXCAVATION_LISTS + "/" + excavationItemId +
                "/" + FIREBASE_PROPERTY_EXCAVATION_PLACE, excavation.place)

        updatedExcavationMap.put("/" + FIREBASE_LOCATION_EXCAVATION_LISTS + "/" + excavationItemId +
                "/" + FIREBASE_PROPERTY_EXCAVATION_ORGANISATION, excavation.organisation)

        updatedExcavationMap.put("/" + FIREBASE_LOCATION_EXCAVATION_LISTS + "/" + excavationItemId +
                "/" + FIREBASE_PROPERTY_EXCAVATION_DESCRIPTION, excavation.description)

        reference.updateChildren(updatedExcavationMap).addOnFailureListener { isUpdateSuccessful = false }

        return isUpdateSuccessful
    }
}