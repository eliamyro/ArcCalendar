package com.eliamyro.arccalendar.fragments

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.eliamyro.arccalendar.R
import com.eliamyro.arccalendar.common.FIREBASE_LOCATION_WORK_LOCATIONS
import com.eliamyro.arccalendar.common.KEY_EXCAVATION_ITEM_ID
import com.eliamyro.arccalendar.common.KEY_WORK_ITEM_ID
import com.eliamyro.arccalendar.models.WorkLocation
import com.eliamyro.arccalendar.viewHolders.WorkLocationHolder
import com.firebase.ui.database.FirebaseRecyclerAdapter
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.fragment_work_locations.*

/**
 * Created by Elias Myronidis on 19/9/17.
 */
class FragmentWorkLocations: Fragment() {

    companion object {
        private val TAG: String = FragmentWorkLocations::class.java.simpleName
    }

    private val excavationItemId: String by lazy { arguments.getString(KEY_EXCAVATION_ITEM_ID) }
    private val workItemId: String by lazy { arguments.getString(KEY_WORK_ITEM_ID) }

    private var mAdapter: FirebaseRecyclerAdapter<WorkLocation, WorkLocationHolder>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setHasOptionsMenu(true)
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater?.inflate(R.layout.fragment_work_locations, container, false)
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        rv_work_locations.layoutManager = LinearLayoutManager(activity)

        val reference: DatabaseReference = FirebaseDatabase.getInstance().reference.child("$FIREBASE_LOCATION_WORK_LOCATIONS/$excavationItemId/$workItemId")
        mAdapter = object : FirebaseRecyclerAdapter<WorkLocation, WorkLocationHolder>(
                WorkLocation::class.java,
                R.layout.item_row_work_location,
                WorkLocationHolder::class.java,
                reference) {

            override fun populateViewHolder(holder: WorkLocationHolder, workLocation: WorkLocation, position: Int) {
                val excavationId: String = getRef(position).key

                holder.bindWorkLocation(workLocation)
//                holder.itemView.setOnClickListener { showWorkLocationDetailsDialog(excavationId, holder.adapterPosition) }
//                holder.itemView.tv_works.setOnClickListener { mCallbackListener?.onItemSelected(excavationId) }
            }
        }

        rv_work_locations.adapter = mAdapter
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
    }
}