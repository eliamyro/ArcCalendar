package com.eliamyro.arccalendar.fragments

import android.content.Context
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
import com.eliamyro.arccalendar.listeners.ClickCallback
import com.eliamyro.arccalendar.models.WorkLocation
import com.eliamyro.arccalendar.viewHolders.WorkLocationHolder
import com.fasterxml.jackson.databind.ObjectMapper
import com.firebase.ui.database.FirebaseRecyclerAdapter
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.fragment_work_locations.*

/**
 * Created by Elias Myronidis on 19/9/17.
 */
class FragmentWorkLocationsList : Fragment() {

    companion object {
        private val TAG: String = FragmentWorkLocationsList::class.java.simpleName
    }

    private val excavationItemId: String by lazy { arguments.getString(KEY_EXCAVATION_ITEM_ID) }
    private val workItemId: String by lazy { arguments.getString(KEY_WORK_ITEM_ID) }
    private var mCallbackListener: ClickCallback? = null

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

        fab.setOnClickListener { showAddWorkLocationDialog() }
        rv_work_locations.layoutManager = LinearLayoutManager(activity)

        val reference: DatabaseReference = FirebaseDatabase.getInstance().reference.child("$FIREBASE_LOCATION_WORK_LOCATIONS/$excavationItemId/$workItemId")
        mAdapter = object : FirebaseRecyclerAdapter<WorkLocation, WorkLocationHolder>(
                WorkLocation::class.java,
                R.layout.item_row_work_location,
                WorkLocationHolder::class.java,
                reference) {

            override fun populateViewHolder(holder: WorkLocationHolder, workLocation: WorkLocation, position: Int) {
                val workLocationItemId: String = getRef(position).key

                holder.bindWorkLocation(workLocation)
                holder.itemView.setOnClickListener { mCallbackListener?.onItemSelected(excavationItemId, workItemId, workLocationItemId) }
            }
        }

        rv_work_locations.adapter = mAdapter
    }

    override fun onAttach(context: Context?) {
        super.onAttach(context)

        if(context is ClickCallback){
            mCallbackListener = context
        } else {
            throw RuntimeException(context!!.toString() + " must implement ClickCallback")
        }
    }

    private fun showAddWorkLocationDialog(){
            val reference: DatabaseReference = FirebaseDatabase.getInstance().reference
            val title = "Work Title"
            val description = "Work description"

            val workLocation = WorkLocation(title, description)

            val updatedItemToAddMap = HashMap<String, Any>()
            val itemsRef: DatabaseReference = FirebaseDatabase.getInstance().reference
                    .child(FIREBASE_LOCATION_WORK_LOCATIONS).child(excavationItemId).child(workItemId)

            /* Save push() to maintain same random Id */
            val newRef = itemsRef.push()
            val itemId = newRef.key

            val itemToAdd = ObjectMapper().convertValue(workLocation, Map::class.java) as HashMap<*, *>
            updatedItemToAddMap.put("/$FIREBASE_LOCATION_WORK_LOCATIONS/$excavationItemId/$workItemId/$itemId", itemToAdd)

            reference.updateChildren(updatedItemToAddMap)
    }
}