package com.eliamyro.arccalendar.presenters

import com.eliamyro.arccalendar.common.FIREBASE_LOCATION_FINDINGS
import com.eliamyro.arccalendar.contracts.ContractDialogDetailsFinding
import com.eliamyro.arccalendar.models.Finding
import com.google.firebase.database.*

/**
 * Created by Elias Myronidis on 4/10/17.
 */
class PresenterDialogFindingDetails(private val mView: ContractDialogDetailsFinding.Views): ContractDialogDetailsFinding.Actions {

    private var mFindingItemRef: DatabaseReference? = null
    private var mFindingRefListener: ValueEventListener? = null
    private var mFinding: Finding? = null

    override fun loadFindingDetails(itemId: String) {
        mFindingItemRef = FirebaseDatabase.getInstance().reference.child(itemId)
        mFindingRefListener = mFindingItemRef?.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                mFinding = dataSnapshot.getValue<Finding>(Finding::class.java)

                if (mFinding == null) {
                    mView.removeFragment()
                }

                mView.updateFinding(mFinding)
            }

            override fun onCancelled(databaseError: DatabaseError) {}
        })
    }
}