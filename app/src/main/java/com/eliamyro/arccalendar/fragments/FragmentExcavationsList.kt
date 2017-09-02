package com.eliamyro.arccalendar.fragments

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.eliamyro.arccalendar.R
import com.eliamyro.arccalendar.common.FIREBASE_LOCATION_EXCAVATION_LISTS
import com.eliamyro.arccalendar.common.KEY_EXCAVATION
import com.eliamyro.arccalendar.common.KEY_EXCAVATION_ITEM_ID
import com.eliamyro.arccalendar.common.inTransaction
import com.eliamyro.arccalendar.dialogs.DialogAddExcavation
import com.eliamyro.arccalendar.dialogs.DialogExcavationDetails
import com.eliamyro.arccalendar.listeners.ClickCallback
import com.eliamyro.arccalendar.models.Excavation
import com.eliamyro.arccalendar.viewHolders.ExcavationHolder
import com.firebase.ui.database.FirebaseRecyclerAdapter
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.fragment_excavations_list.*
import kotlinx.android.synthetic.main.item_row_excavation.view.*

/**
 * Created by Elias Myronidis on 23/8/17.
 */
class FragmentExcavationsList : Fragment() {

    companion object {
        private val TAG: String = FragmentExcavationsList::class.java.simpleName
    }

    private var mAdapter: FirebaseRecyclerAdapter<Excavation, ExcavationHolder>? = null


    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater?.inflate(R.layout.fragment_excavations_list, container, false)
    }


    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        // Fab onClickListener
        fabAddExcavation.setOnClickListener { showAddExcavationDialog() }

        rvExcavations.layoutManager = LinearLayoutManager(activity)

        val reference: DatabaseReference = FirebaseDatabase.getInstance().reference.child(FIREBASE_LOCATION_EXCAVATION_LISTS)
        mAdapter = object : FirebaseRecyclerAdapter<Excavation, ExcavationHolder>(
                Excavation::class.java,
                R.layout.item_row_excavation,
                ExcavationHolder::class.java,
                reference) {

            override fun populateViewHolder(holder: ExcavationHolder, excavation: Excavation, position: Int) {
                val key: String = getRef(position).key

                holder.bindExcavationView(excavation)
                holder.itemView.setOnClickListener { showExcavationDetailsDialog(key, holder.adapterPosition) }
                holder.itemView.tv_works.setOnClickListener { (activity as ClickCallback).onItemSelected() }
            }
        }

        rvExcavations.adapter = mAdapter
    }

    private fun showAddExcavationDialog() {
        val dialog = DialogAddExcavation()

        fragmentManager.inTransaction { replace(android.R.id.content, dialog) }
    }


    private fun showExcavationDetailsDialog(key: String?, position: Int) {
        val dialog = DialogExcavationDetails()
        val bundle = Bundle()
        val excavation: Excavation? = mAdapter?.getItem(position)
        bundle.putParcelable(KEY_EXCAVATION, excavation)
        bundle.putString(KEY_EXCAVATION_ITEM_ID, key)
        dialog.arguments = bundle

        fragmentManager.inTransaction({ replace(android.R.id.content, dialog) })
    }


    override fun onDestroy() {
        super.onDestroy()
        mAdapter?.cleanup()
    }
}