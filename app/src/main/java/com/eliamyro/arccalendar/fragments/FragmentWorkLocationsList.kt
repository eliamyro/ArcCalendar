package com.eliamyro.arccalendar.fragments

import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.view.*
import com.eliamyro.arccalendar.R
import com.eliamyro.arccalendar.common.*
import com.eliamyro.arccalendar.contracts.ContractDialogDeleteAllLocations
import com.eliamyro.arccalendar.contracts.ContractDialogDeleteAllLocations.Views
import com.eliamyro.arccalendar.dialogs.DialogAddWorkLocation
import com.eliamyro.arccalendar.dialogs.DialogDeleteAllLocations
import com.eliamyro.arccalendar.dialogs.DialogWorkLocationDetails
import com.eliamyro.arccalendar.listeners.ClickCallback
import com.eliamyro.arccalendar.models.WorkLocation
import com.eliamyro.arccalendar.presenters.PresenterDialogDeleteAllLocations
import com.eliamyro.arccalendar.viewHolders.WorkLocationHolder
import com.firebase.ui.database.FirebaseRecyclerAdapter
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.fragment_work_locations.*
import kotlinx.android.synthetic.main.item_row_work_location.view.*

/**
 * Created by Elias Myronidis on 19/9/17.
 */
class FragmentWorkLocationsList : Fragment() {

    companion object {
        private val TAG: String = FragmentWorkLocationsList::class.java.simpleName
    }

    private val mExcavationItemId: String by lazy { arguments.getString(KEY_EXCAVATION_ITEM_ID) }
    private val mWorkItemId: String by lazy { arguments.getString(KEY_WORK_ITEM_ID) }
    private var mCallbackListener: ClickCallback? = null

    private var mAdapter: FirebaseRecyclerAdapter<WorkLocation, WorkLocationHolder>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
        Log.d(TAG, "onCreate")
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        Log.d(TAG, "onCreateView")
        return inflater?.inflate(R.layout.fragment_work_locations, container, false)
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Log.d(TAG, "onViewCreated")

        fab.setOnClickListener { showAddWorkLocationDialog() }
        rv_work_locations.layoutManager = LinearLayoutManager(activity)

        val reference: DatabaseReference = FirebaseDatabase.getInstance().reference.child("$FIREBASE_LOCATION_WORK_LOCATIONS/$mExcavationItemId/$mWorkItemId")
        mAdapter = object : FirebaseRecyclerAdapter<WorkLocation, WorkLocationHolder>(
                WorkLocation::class.java,
                R.layout.item_row_work_location,
                WorkLocationHolder::class.java,
                reference) {

            override fun populateViewHolder(holder: WorkLocationHolder, workLocation: WorkLocation, position: Int) {
                val workLocationItemId: String = getRef(position).key

                holder.bindWorkLocation(workLocation)
                holder.itemView.setOnClickListener { showWorkLocationDetailsDialog(workLocationItemId, holder.adapterPosition) }
                holder.itemView.tv_findings.setOnClickListener { mCallbackListener?.onItemSelected(mExcavationItemId, mWorkItemId, workLocationItemId) }
            }
        }

        rv_work_locations.adapter = mAdapter
    }

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        Log.d(TAG, "onAttach")

        if (context is ClickCallback) {
            mCallbackListener = context
        } else {
            throw RuntimeException(context!!.toString() + " must implement ClickCallback")
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?, inflater: MenuInflater?) {
        Log.d(TAG, "onCreateOptionsMenu")
        inflater?.inflate(R.menu.menu_work_locations, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        Log.d(TAG, "onOptionsItemSelected")
        when (item?.itemId) {
            R.id.action_delete_locations ->
                showDeleteAllLocationsDialog()
        }
        return super.onOptionsItemSelected(item)
    }

    private fun showAddWorkLocationDialog() {
        val dialog = DialogAddWorkLocation()
        val bundle = Bundle()
        bundle.putString(KEY_EXCAVATION_ITEM_ID, mExcavationItemId)
        bundle.putString(KEY_WORK_ITEM_ID, mWorkItemId)
        dialog.arguments = bundle
        fragmentManager.inTransaction { replace(android.R.id.content, dialog) }
    }

    private fun showDeleteAllLocationsDialog() {
        val dialog = DialogDeleteAllLocations()
        val bundle = Bundle()
        bundle.putString(KEY_EXCAVATION_ITEM_ID, mExcavationItemId)
        bundle.putString(KEY_WORK_ITEM_ID, mWorkItemId)
        dialog.arguments = bundle

        dialog.show(fragmentManager, DELETE_ALL_LOCATIONS_DIALOG)
    }

    private fun showWorkLocationDetailsDialog(workLocationItemId: String, position: Int) {
        val dialog = DialogWorkLocationDetails()
        val bundle = Bundle()
        val workLocation: WorkLocation? = mAdapter?.getItem(position)
        bundle.putParcelable(KEY_WORK_LOCATION, workLocation)
        bundle.putString(KEY_EXCAVATION_ITEM_ID, mExcavationItemId)
        bundle.putString(KEY_WORK_ITEM_ID, mWorkItemId)
        bundle.putString(KEY_WORK_LOCATION_ITEM_ID, workLocationItemId)

        dialog.arguments = bundle

        fragmentManager.inTransaction({ replace(android.R.id.content, dialog) })
    }

    override fun onAttachFragment(childFragment: Fragment?) {
        super.onAttachFragment(childFragment)
        Log.d(TAG, "onAttachFragment")
    }

    override fun onHiddenChanged(hidden: Boolean) {
        super.onHiddenChanged(hidden)
        Log.d(TAG, "onHiddenChanged")
    }
}