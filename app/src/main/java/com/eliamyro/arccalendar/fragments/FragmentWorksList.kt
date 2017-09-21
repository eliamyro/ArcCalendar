package com.eliamyro.arccalendar.fragments

import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.eliamyro.arccalendar.R
import com.eliamyro.arccalendar.common.FIREBASE_LOCATION_EXCAVATION_WORKS
import com.eliamyro.arccalendar.common.KEY_EXCAVATION_ITEM_ID
import com.eliamyro.arccalendar.common.inTransaction
import com.eliamyro.arccalendar.dialogs.DialogAddWork
import com.eliamyro.arccalendar.listeners.ClickCallback
import com.eliamyro.arccalendar.models.Work
import com.eliamyro.arccalendar.viewHolders.WorkHolder
import com.firebase.ui.database.FirebaseRecyclerAdapter
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.fragment_works_list.*

class FragmentWorksList : Fragment() {

    private val mExcavationId: String by lazy { arguments.getString(KEY_EXCAVATION_ITEM_ID) }
    private var mAdapter: FirebaseRecyclerAdapter<Work, WorkHolder>? = null

    private var mCallbackListener: ClickCallback? = null

    companion object {
        private val TAG: String = FragmentWorksList::class.java.simpleName
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater?.inflate(R.layout.fragment_works_list, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        fab_add_work.setOnClickListener{ showAddWorkDialog() }

        rv_works.layoutManager = LinearLayoutManager(activity)

        val reference: DatabaseReference = FirebaseDatabase.getInstance().reference.child(FIREBASE_LOCATION_EXCAVATION_WORKS +"/"+mExcavationId)
        mAdapter = object : FirebaseRecyclerAdapter<Work, WorkHolder>(
                Work::class.java,
                R.layout.item_row_work,
                WorkHolder::class.java,
                reference) {

            override fun populateViewHolder(holder: WorkHolder, work: Work, position: Int) {
                val workId: String = getRef(position).key

                holder.bindWorkView(work)
                holder.itemView.setOnClickListener { mCallbackListener?.onItemSelected(workId = workId) }
            }
        }

        rv_works.adapter = mAdapter
    }

    override fun onAttach(context: Context?) {
        super.onAttach(context)

        if(context is ClickCallback){
            mCallbackListener = context
        } else {
            throw RuntimeException(context!!.toString() + " must implement ClickCallback")
        }
    }

    override fun onDetach() {
        super.onDetach()
        mCallbackListener = null
    }

    private fun showAddWorkDialog(){

        val dialog = DialogAddWork()
        val bundle = Bundle()
        bundle.putString(KEY_EXCAVATION_ITEM_ID, mExcavationId)
        dialog.arguments = bundle

        fragmentManager.inTransaction { replace(android.R.id.content, dialog) }
    }

    override fun onDestroy() {
        super.onDestroy()

        mAdapter?.cleanup()
    }

}
