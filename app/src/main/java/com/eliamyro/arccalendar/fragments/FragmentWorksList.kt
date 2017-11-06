package com.eliamyro.arccalendar.fragments

import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.view.*
import android.widget.Toast
import com.eliamyro.arccalendar.R
import com.eliamyro.arccalendar.common.*
import com.eliamyro.arccalendar.dialogs.DialogAddWork
import com.eliamyro.arccalendar.dialogs.DialogDeleteAllWorks
import com.eliamyro.arccalendar.listeners.ClickCallback
import com.eliamyro.arccalendar.models.Work
import com.eliamyro.arccalendar.viewHolders.WorkHolder
import com.firebase.ui.database.FirebaseRecyclerAdapter
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.Query
import kotlinx.android.synthetic.main.fragment_works_list.*


class FragmentWorksList : Fragment() {

    private val mExcavationId: String by lazy { arguments.getString(KEY_EXCAVATION_ITEM_ID) }
    private var mAdapter: FirebaseRecyclerAdapter<Work, WorkHolder>? = null
    private var mCallbackListener: ClickCallback? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater?.inflate(R.layout.fragment_works_list, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        fab_add_work.setOnClickListener { showAddWorkDialog() }

        val lManager = LinearLayoutManager(activity)
        lManager.reverseLayout = true
        lManager.stackFromEnd = true
        rv_works.layoutManager = lManager

        val reference: DatabaseReference = FirebaseDatabase.getInstance().reference.child("$FIREBASE_LOCATION_EXCAVATION_WORKS/$mExcavationId")
        val query: Query = reference.orderByChild(FIREBASE_PROPERTY_WORK_DATE)

        mAdapter = object : FirebaseRecyclerAdapter<Work, WorkHolder>(
                Work::class.java,
                R.layout.item_row_work,
                WorkHolder::class.java,
                query) {

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

        if (context is ClickCallback) {
            mCallbackListener = context
        } else {
            throw RuntimeException(context!!.toString() + " must implement ClickCallback")
        }
    }

    override fun onDetach() {
        super.onDetach()
        mCallbackListener = null
    }

    override fun onCreateOptionsMenu(menu: Menu?, inflater: MenuInflater?) {
        inflater?.inflate(R.menu.menu_works_list, menu)

        return super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item?.itemId) {
            R.id.action_works_list_delete -> {
                if (mAdapter?.itemCount == 0) {
                    Toast.makeText(activity, "There are no works to delete!", Toast.LENGTH_SHORT).show()
                } else {
                    showDeleteAllWorksDialog(mExcavationId)
                }
            }
        }

        return super.onOptionsItemSelected(item)
    }

    private fun showDeleteAllWorksDialog(itemId: String?) {
        val dialog = DialogDeleteAllWorks()
        val bundle = Bundle()
        bundle.putString(KEY_EXCAVATION_ITEM_ID, itemId)
        dialog.arguments = bundle

        dialog.show(fragmentManager, DELETE_WORK_DIALOG)
    }

    private fun showAddWorkDialog() {
        val dialog = DialogAddWork()
        val bundle = Bundle()
        bundle.putString(KEY_EXCAVATION_ITEM_ID, mExcavationId)
        dialog.arguments = bundle

        fragmentManager.inTransaction(true) { replace(android.R.id.content, dialog) }
    }

    override fun onDestroy() {
        super.onDestroy()

        mAdapter?.cleanup()
    }

}
