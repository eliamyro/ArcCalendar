package com.eliamyro.arccalendar.fragments

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.eliamyro.arccalendar.R
import com.eliamyro.arccalendar.common.*
import com.eliamyro.arccalendar.dialogs.DialogAddFinding
import com.eliamyro.arccalendar.dialogs.DialogFindingDetails
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
class FragmentFindingsList: Fragment() {

    val excavationItemId: String by lazy { arguments.getString(KEY_EXCAVATION_ITEM_ID) }
    val workItemId: String by lazy { arguments.getString(KEY_WORK_ITEM_ID) }
    val workLocationItemId: String by lazy { arguments.getString(KEY_WORK_LOCATION_ITEM_ID) }

    private var mAdapter: FirebaseRecyclerAdapter<Finding, FindingHolder>? = null

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater?.inflate(R.layout.fragment_findings_list, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        fab_add_finding.setOnClickListener { showAddFindingDialog() }

        rv_findings.layoutManager = LinearLayoutManager(activity)

        val reference: DatabaseReference = FirebaseDatabase.getInstance().reference
                .child("$FIREBASE_LOCATION_FINDINGS/$excavationItemId/$workItemId/$workLocationItemId")
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

    private fun showFindingDetailsDialog(findingItemId: String, position: Int){
        val finding: Finding? = mAdapter?.getItem(position)
        val dialog = DialogFindingDetails()
        val bundle = Bundle()
        bundle.putString(KEY_EXCAVATION_ITEM_ID, excavationItemId)
        bundle.putString(KEY_WORK_ITEM_ID, workItemId)
        bundle.putString(KEY_WORK_LOCATION_ITEM_ID, workLocationItemId)
        bundle.putString(KEY_FINDING_ITEM_ID, findingItemId)
        bundle.putParcelable(KEY_FINDING, finding)
        dialog.arguments = bundle

        fragmentManager.inTransaction { replace(android.R.id.content, dialog) }
    }

    private fun showAddFindingDialog(){
        val reference: DatabaseReference = FirebaseDatabase.getInstance().reference
        val name = "Finding name"
        val image = "Finding image"

        val finding = Finding(name, image)

        val updatedItemToAddMap = HashMap<String, Any>()
        val itemsRef: DatabaseReference = FirebaseDatabase.getInstance().reference
                .child("$FIREBASE_LOCATION_FINDINGS/$excavationItemId/$workItemId/$workLocationItemId")

        /* Save push() to maintain same random Id */
        val newRef = itemsRef.push()
        val itemId = newRef.key

        val itemToAdd = ObjectMapper().convertValue(finding, Map::class.java) as HashMap<*, *>
        updatedItemToAddMap.put("/$FIREBASE_LOCATION_FINDINGS/$excavationItemId/$workItemId/$workLocationItemId/$itemId", itemToAdd)

        reference.updateChildren(updatedItemToAddMap)
//        val dialog = DialogAddFinding()
//        val bundle = Bundle()
//        bundle.putString(KEY_EXCAVATION_ITEM_ID, excavationItemId)
//        bundle.putString(KEY_WORK_ITEM_ID, workItemId)
//        bundle.putString(KEY_WORK_LOCATION_ITEM_ID, workLocationItemId)
//        dialog.arguments = bundle
//
//        fragmentManager.inTransaction { replace(android.R.id.content, dialog) }

    }
}