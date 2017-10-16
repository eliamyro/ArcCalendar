package com.eliamyro.arccalendar.presenters

import android.util.Log
import com.eliamyro.arccalendar.common.*
import com.eliamyro.arccalendar.contracts.ContractDialogEditWork
import com.eliamyro.arccalendar.contracts.ContractDialogEditWork.Actions
import com.eliamyro.arccalendar.contracts.ContractDialogEditWork.Views
import com.eliamyro.arccalendar.models.Work
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

/**
 * Created by Elias Myronidis on 16/10/17.
 */
class PresenterDialogEditWork(val mView: Views) : Actions {

    companion object {
        private val TAG: String = PresenterDialogEditWork::class.java.simpleName
    }

    override fun editWork(excavationItemId: String, workItemId: String, work: Work): Boolean {
        Log.d(TAG, "Inside editWork")

        var isUpdateSuccessful: Boolean = true

        val reference: DatabaseReference = FirebaseDatabase.getInstance().reference

        val updatedWorkMap = HashMap<String, Any>()
        updatedWorkMap.put("/" + FIREBASE_LOCATION_EXCAVATION_WORKS + "/" + excavationItemId +
                "/" + workItemId + "/" + FIREBASE_PROPERTY_WORK_DATE, work.workDate)

        updatedWorkMap.put("/" + FIREBASE_LOCATION_EXCAVATION_WORKS + "/" + excavationItemId +
                "/" + workItemId + "/" + FIREBASE_PROPERTY_WORK_DESCRIPTION, work.description)

        updatedWorkMap.put("/" + FIREBASE_LOCATION_EXCAVATION_WORKS + "/" + excavationItemId +
                "/" + workItemId + "/" + FIREBASE_PROPERTY_WORK_DIRECTORS, work.directorsList)

        updatedWorkMap.put("/" + FIREBASE_LOCATION_EXCAVATION_WORKS + "/" + excavationItemId +
                "/" + workItemId + "/" + FIREBASE_PROPERTY_WORK_ARCHAEOLOGISTS, work.archaeologistsList)

        updatedWorkMap.put("/" + FIREBASE_LOCATION_EXCAVATION_WORKS + "/" + excavationItemId +
                "/" + workItemId + "/" + FIREBASE_PROPERTY_WORK_STUDENTS, work.studentsList)

        reference.updateChildren(updatedWorkMap).addOnFailureListener { isUpdateSuccessful = false }

        return isUpdateSuccessful
    }
}
