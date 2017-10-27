package com.eliamyro.arccalendar.fragments

import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.Toolbar
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.eliamyro.arccalendar.R
import com.eliamyro.arccalendar.common.*
import com.eliamyro.arccalendar.dialogs.DialogAddFinding
import com.eliamyro.arccalendar.dialogs.DialogFindingDetails
import com.eliamyro.arccalendar.listeners.ClickCallback
import com.eliamyro.arccalendar.models.Finding
import com.eliamyro.arccalendar.viewHolders.FindingHolder
import com.fasterxml.jackson.databind.ObjectMapper
import com.firebase.ui.database.FirebaseRecyclerAdapter
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.fragment_findings_list.*

/**
 * Created by Elias Myronidis on 29/9/17.
 */
class FragmentFindingsList : Fragment() {

    val mExcavationItemId: String by lazy { arguments.getString(KEY_EXCAVATION_ITEM_ID) }
    val mWorkItemId: String by lazy { arguments.getString(KEY_WORK_ITEM_ID) }
    val mWorkLocationItemId: String by lazy { arguments.getString(KEY_WORK_LOCATION_ITEM_ID) }

    private var mAdapter: FirebaseRecyclerAdapter<Finding, FindingHolder>? = null

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater?.inflate(R.layout.fragment_findings_list, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        fab_add_finding.setOnClickListener { showAddFindingDialog() }

        rv_findings.layoutManager = LinearLayoutManager(activity)

        val reference: DatabaseReference = FirebaseDatabase.getInstance().reference
                .child("$FIREBASE_LOCATION_FINDINGS/$mExcavationItemId/$mWorkItemId/$mWorkLocationItemId")
        mAdapter = object : FirebaseRecyclerAdapter<Finding, FindingHolder>(
                Finding::class.java,
                R.layout.item_row_finding,
                FindingHolder::class.java,
                reference) {

            override fun populateViewHolder(holder: FindingHolder, finding: Finding, position: Int) {
                val findingItemId: String = getRef(position).key

                holder.bindFindingView(finding)
                holder.itemView.setOnClickListener { showFindingDetailsDialog(findingItemId, holder.adapterPosition) }
            }
        }

        rv_findings.adapter = mAdapter
    }


    private fun showFindingDetailsDialog(findingItemId: String, position: Int) {
        val finding: Finding? = mAdapter?.getItem(position)
        val dialog = DialogFindingDetails()
        val bundle = Bundle()
        bundle.putString(KEY_EXCAVATION_ITEM_ID, mExcavationItemId)
        bundle.putString(KEY_WORK_ITEM_ID, mWorkItemId)
        bundle.putString(KEY_WORK_LOCATION_ITEM_ID, mWorkLocationItemId)
        bundle.putString(KEY_FINDING_ITEM_ID, findingItemId)
        bundle.putParcelable(KEY_FINDING, finding)
        dialog.arguments = bundle

        fragmentManager.inTransaction { replace(android.R.id.content, dialog) }
    }

    private fun showAddFindingDialog() {
        val dialog = DialogAddFinding()
        val bundle = Bundle()
        bundle.putString(KEY_EXCAVATION_ITEM_ID, mExcavationItemId)
        bundle.putString(KEY_WORK_ITEM_ID, mWorkItemId)
        bundle.putString(KEY_WORK_LOCATION_ITEM_ID, mWorkLocationItemId)
        dialog.arguments = bundle

        fragmentManager.inTransaction { replace(android.R.id.content, dialog) }

    }
}