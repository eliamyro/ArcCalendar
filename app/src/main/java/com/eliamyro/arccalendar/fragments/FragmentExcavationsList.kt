package com.eliamyro.arccalendar.fragments

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.view.*
import com.eliamyro.arccalendar.R
import com.eliamyro.arccalendar.activities.ActivitySearch
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
* LinkedIn: https://www.linkedin.com/in/eliasmyronidis/
*/

class FragmentExcavationsList : Fragment() {

    private var mAdapter: FirebaseRecyclerAdapter<Excavation, ExcavationHolder>? = null
    private var mCallbackListener: ClickCallback? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater?.inflate(R.layout.fragment_excavations_list, container, false)
    }


    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        // Fab onClickListener
        fab_add_excavation.setOnClickListener { showAddExcavationDialog() }

        rv_excavations.layoutManager = LinearLayoutManager(activity)

        val reference: DatabaseReference = FirebaseDatabase.getInstance().reference.child(FIREBASE_LOCATION_EXCAVATION_LISTS)
        mAdapter = object : FirebaseRecyclerAdapter<Excavation, ExcavationHolder>(
                Excavation::class.java,
                R.layout.item_row_excavation,
                ExcavationHolder::class.java,
                reference) {

            override fun populateViewHolder(holder: ExcavationHolder, excavation: Excavation, position: Int) {
                val excavationId: String = getRef(position).key

                holder.bindExcavationView(excavation)
                holder.itemView.setOnClickListener { showExcavationDetailsDialog(excavationId, holder.adapterPosition) }
                holder.itemView.tv_works.setOnClickListener { mCallbackListener?.onItemSelected(excavationId) }
            }
        }

        rv_excavations.adapter = mAdapter
    }

    override fun onAttach(context: Context?) {
        super.onAttach(context)

        if(context is ClickCallback){
            mCallbackListener = context
        } else {
            throw RuntimeException(context!!.toString() + " must implement ClickCallback")
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?, inflater: MenuInflater?) {
        inflater?.inflate(R.menu.menu_excavations_list, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {

        when(item?.itemId){
            R.id.action_search -> {
                val intent = Intent(activity, ActivitySearch::class.java)
                startActivity(intent)
            }
        }

        return super.onOptionsItemSelected(item)
    }

    override fun onDetach() {
        super.onDetach()
        mCallbackListener = null
    }

    private fun showAddExcavationDialog() {
        val dialog = DialogAddExcavation()

        fragmentManager.inTransaction { replace(android.R.id.content, dialog) }
    }


    private fun showExcavationDetailsDialog(excavationId: String?, position: Int) {
        val dialog = DialogExcavationDetails()
        val bundle = Bundle()
        val excavation: Excavation? = mAdapter?.getItem(position)
        bundle.putParcelable(KEY_EXCAVATION, excavation)
        bundle.putString(KEY_EXCAVATION_ITEM_ID, excavationId)
        dialog.arguments = bundle

        fragmentManager.inTransaction({ replace(android.R.id.content, dialog) })
    }


    override fun onDestroy() {
        super.onDestroy()
        mAdapter?.cleanup()
    }
}