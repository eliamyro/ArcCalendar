package com.eliamyro.arccalendar.presenters

import com.eliamyro.arccalendar.common.FIREBASE_LOCATION_WORK_LOCATIONS
import com.eliamyro.arccalendar.common.FIREBASE_PROPERTY_WORK_LOCATION_DESCRIPTION
import com.eliamyro.arccalendar.common.FIREBASE_PROPERTY_WORK_LOCATION_LOCATION
import com.eliamyro.arccalendar.contracts.ContractDialogEditWorkLocation.Actions
import com.eliamyro.arccalendar.contracts.ContractDialogEditWorkLocation.Views
import com.eliamyro.arccalendar.models.WorkLocation
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

/**
* Created by Elias Myronidis on 23/10/17.
* LinkedIn: https://www.linkedin.com/in/eliasmyronidis/
*/

class PresenterDialogEditWorkLocation(val mView: Views): Actions {
    override fun editWorkLocation(excavationItemId: String, workItemId: String,
                                  workLocationItemId: String, workLocation: WorkLocation): Boolean {
        var isUpdateSuccessful = true

        val reference: DatabaseReference = FirebaseDatabase.getInstance().reference

        val updatedWorkLocationMap = HashMap<String, Any>()
        updatedWorkLocationMap.put("/" + FIREBASE_LOCATION_WORK_LOCATIONS + "/" + excavationItemId +
                "/" + workItemId + "/" + workLocationItemId + "/" + FIREBASE_PROPERTY_WORK_LOCATION_LOCATION, workLocation.location)

        updatedWorkLocationMap.put("/" + FIREBASE_LOCATION_WORK_LOCATIONS + "/" + excavationItemId +
                "/" + workItemId + "/" + workLocationItemId + "/" + FIREBASE_PROPERTY_WORK_LOCATION_DESCRIPTION, workLocation.description)


        reference.updateChildren(updatedWorkLocationMap).addOnFailureListener { isUpdateSuccessful = false }

        return isUpdateSuccessful
    }
}