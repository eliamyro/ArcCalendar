package com.eliamyro.arccalendar.presenters

import com.eliamyro.arccalendar.contracts.ContractFragmentWorkInfo
import com.eliamyro.arccalendar.models.Work
import com.google.firebase.database.*

/**
* Created by Elias Myronidis on 21/9/17.
* LinkedIn: https://www.linkedin.com/in/eliasmyronidis/
*/

class PresenterFragmentWorkInfo(private val mView: ContractFragmentWorkInfo.Views): ContractFragmentWorkInfo.Actions {

    private var refListener: ValueEventListener? = null
    private var mReference: DatabaseReference? = null

    override fun destroyValueEventListener() {
        if (refListener != null){
            mReference?.removeEventListener(refListener)
        }
    }

    override fun loadWorkInfo(reference: DatabaseReference) {
        mReference = reference
        refListener = mReference?.addValueEventListener(object : ValueEventListener {
            override fun onCancelled(p0: DatabaseError?) {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }

            override fun onDataChange(dataSnapshot: DataSnapshot?) {
                val work = dataSnapshot?.getValue(Work::class.java)

                if (work == null){
                    mView.removeFragment()
                }

                mView.displayInfo(work)
            }
        })
    }

}