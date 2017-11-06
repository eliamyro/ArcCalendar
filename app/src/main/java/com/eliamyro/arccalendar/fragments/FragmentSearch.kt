package com.eliamyro.arccalendar.fragments

import android.os.Bundle
import android.support.v4.app.Fragment
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.eliamyro.arccalendar.R
import com.eliamyro.arccalendar.common.FIREBASE_LOCATION_WORK_LOCATIONS
import com.eliamyro.arccalendar.models.WorkLocation
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.fragment_search.*
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.ChildEventListener
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.FirebaseDatabase






/**
 * Created by Elias Myronidis on 30/10/17.
 */
class FragmentSearch : Fragment() {

    companion object {
        val TAG: String = FragmentSearch::class.java.simpleName
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater?.inflate(R.layout.fragment_search, container, false)
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        btn_search.setOnClickListener { search() }
    }

    private fun search() {
        val locationSearchStr = et_location_search.text.toString()
        val findingSearchStr = et_finding_search.text.toString()

        val reference = FirebaseDatabase.getInstance().reference
        val workLocationsRef = reference.child("workLocations")
        val eventListener = object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                for (dSnapshot in dataSnapshot.children) {
                    for (ds in dSnapshot.children) {
                        for(d in ds.children){
                            val query = workLocationsRef.child(dSnapshot.key).child(ds.key).child(d.key).orderByChild("location").equalTo("London")
                            Log.d(TAG, workLocationsRef.child(dSnapshot.key).child(ds.key).child(d.key).toString())
                            val eventListener = object : ValueEventListener {
                                override fun onDataChange(snapshot: DataSnapshot) {
//                                val description = snapshot.child("description").getValue(String::class.java)
//                                Log.d("description", description)

                                    Log.d(TAG, snapshot.child("description").toString())
                                }

                                override fun onCancelled(databaseError: DatabaseError) {}
                            }
                            query.addListenerForSingleValueEvent(eventListener)
                        }

                    }
                }
            }

            override fun onCancelled(databaseError: DatabaseError) {}
        }
        workLocationsRef.addListenerForSingleValueEvent(eventListener)
    }
}