package com.eliamyro.arccalendar.presenters

import com.eliamyro.arccalendar.common.FIREBASE_LOCATION_EXCAVATION_WORKS
import com.eliamyro.arccalendar.contracts.ContractDialogAddWork
import com.eliamyro.arccalendar.contracts.ContractDialogAddWork.Views
import com.eliamyro.arccalendar.models.Work
import com.fasterxml.jackson.databind.ObjectMapper
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.debug

/**
 * Created by Elias Myronidis on 6/9/17.
 */
class PresenterDialogAddWork(private val mView: Views): ContractDialogAddWork.Actions, AnkoLogger {

    override fun addWork(excavationId: String, work: Work): Boolean {

        val updatedItemToAddMap = HashMap<String, Any>()
        val firebaseRef: DatabaseReference = FirebaseDatabase.getInstance().reference
        val itemsRef: DatabaseReference = FirebaseDatabase.getInstance().reference
                .child(FIREBASE_LOCATION_EXCAVATION_WORKS).child(excavationId)

        /* Save push() to maintain same random Id */
        val newRef = itemsRef.push()
        val itemId = newRef.key

        val itemToAdd = ObjectMapper().convertValue(work, Map::class.java) as HashMap<*, *>
        updatedItemToAddMap.put("/$FIREBASE_LOCATION_EXCAVATION_WORKS/$excavationId/$itemId", itemToAdd)

        firebaseRef.updateChildren(updatedItemToAddMap)
        return true
    }
}