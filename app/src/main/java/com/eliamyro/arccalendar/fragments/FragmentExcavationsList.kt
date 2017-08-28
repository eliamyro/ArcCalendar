package com.eliamyro.arccalendar.fragments

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.PopupMenu
import android.widget.Toast
import com.eliamyro.arccalendar.R
import com.eliamyro.arccalendar.common.FIREBASE_LOCATION_EXCAVATION_LISTS
import com.eliamyro.arccalendar.contracts.ContractFragmentExcavationsList
import com.eliamyro.arccalendar.contracts.ContractFragmentExcavationsList.Views
import com.eliamyro.arccalendar.models.Excavation
import com.eliamyro.arccalendar.presenters.PresenterFragmentExcavationsList
import com.eliamyro.arccalendar.viewHolders.ExcavationHolder
import com.firebase.ui.database.FirebaseRecyclerAdapter
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.fragment_excavations_list.*
import kotlinx.android.synthetic.main.item_row_excavation.view.*

/**
 * Created by Elias Myronidis on 23/8/17.
 */
class FragmentExcavationsList : Fragment(), Views {

    companion object {
        private val TAG: String = FragmentExcavationsList::class.java.simpleName
    }


    private val mPresenter: ContractFragmentExcavationsList.Actions by lazy { PresenterFragmentExcavationsList(this) }
    private var mAdapter: FirebaseRecyclerAdapter<Excavation, ExcavationHolder>? = null


    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater?.inflate(R.layout.fragment_excavations_list, container, false)
    }


    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        // Fab onClickListener
        fabAddExcavation.setOnClickListener { mPresenter.showAddExcavationDialog(activity) }

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
                holder.itemView.setOnClickListener { Toast.makeText(activity, holder.adapterPosition.toString(), Toast.LENGTH_LONG).show() }
                holder.itemView.ib_excavation_menu.setOnClickListener {
                    showPopUpMenu(holder.itemView.ib_excavation_menu, key, reference, holder.adapterPosition)
                }
            }
        }

        rvExcavations.adapter = mAdapter
    }

    private fun showPopUpMenu(view: View, itemId: String?, reference: DatabaseReference, position: Int) {
        val popup = PopupMenu(view.context, view)
        val inflater = popup.menuInflater
        inflater.inflate(R.menu.pop_up_menu_excavation, popup.menu)

        popup.setOnMenuItemClickListener { menuItem ->
            when (menuItem.itemId) {
                R.id.action_delete -> {
                    mPresenter.removeExcavation(reference, itemId)
                }

                R.id.action_edit -> {
                    val excavation = mPresenter.showEditExcavationDialog(mAdapter?.getItem(position))
                }
            }
            true
        }
        popup.show()
    }


    override fun displayToast(message: String) {
        Toast.makeText(activity, message, Toast.LENGTH_LONG).show()
    }


    override fun onDestroy() {
        super.onDestroy()

        mAdapter?.cleanup()
    }
}