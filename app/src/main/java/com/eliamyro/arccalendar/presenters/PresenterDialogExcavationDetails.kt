package com.eliamyro.arccalendar.presenters

import com.eliamyro.arccalendar.common.FIREBASE_LOCATION_EXCAVATION_LISTS
import com.eliamyro.arccalendar.contracts.ContractDialogDetailsExcavation
import com.eliamyro.arccalendar.models.Excavation
import com.google.firebase.database.*

/**
 * Created by Elias Myronidis on 30/8/17.
 */
class PresenterDialogExcavationDetails(private val mView: ContractDialogDetailsExcavation.Views): ContractDialogDetailsExcavation.Actions {

    private var mExcavationItemRef: DatabaseReference? = null
    private var mExcavationRefListener: ValueEventListener? = null
    private var mExcavation: Excavation? = null

    override fun loadExcavationDetails(itemId: String) {
        mExcavationItemRef = FirebaseDatabase.getInstance().reference.child(FIREBASE_LOCATION_EXCAVATION_LISTS + "/" + itemId)
        mExcavationRefListener = mExcavationItemRef?.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                mExcavation = dataSnapshot.getValue<Excavation>(Excavation::class.java)

                if (mExcavation == null) {
                    mView.removeFragment()
                }

                mView.updateExcavation(mExcavation)
            }

            override fun onCancelled(databaseError: DatabaseError) {}
        })
    }
}