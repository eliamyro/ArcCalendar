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
import com.eliamyro.arccalendar.common.inTransaction
import com.eliamyro.arccalendar.dialogs.DialogAddExcavation
import com.eliamyro.arccalendar.dialogs.DialogEditExcavation
import com.eliamyro.arccalendar.dialogs.DialogRemoveExcavation
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
        private const val KEY_EXCAVATION: String = "excavation"
        private const val REMOVE_EXCAVATION_DIALOG: String = "remove_excavation_dialog"
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
                holder.itemView.setOnClickListener { Toast.makeText(activity, holder.adapterPosition.toString(), Toast.LENGTH_SHORT).show() }
                holder.itemView.ib_excavation_menu.setOnClickListener {
                    showPopUpMenu(holder.itemView.ib_excavation_menu, key, holder.adapterPosition)
                }
            }
        }

        rvExcavations.adapter = mAdapter
    }

    private fun showPopUpMenu(view: View, itemId: String?, position: Int) {
        val popup = PopupMenu(view.context, view)
        val inflater = popup.menuInflater
        inflater.inflate(R.menu.pop_up_menu_excavation, popup.menu)

        popup.setOnMenuItemClickListener { menuItem ->
            when (menuItem.itemId) {
                R.id.action_delete -> {
                    showRemoveExcavationDialog(itemId)
                }

                R.id.action_edit -> {
                    showEditExcavationDialog(mAdapter?.getItem(position))
                }
            }
            true
        }
        popup.show()
    }

    private fun showAddExcavationDialog() {
        val dialog = DialogAddExcavation()
        fragmentManager.inTransaction { replace(android.R.id.content, dialog) }
    }

    private fun showRemoveExcavationDialog(itemId: String?) {
        val dialog = DialogRemoveExcavation()
        val bundle = Bundle()
        bundle.putString("item_id", itemId)
        dialog.arguments = bundle

        dialog.show(fragmentManager, REMOVE_EXCAVATION_DIALOG)
    }

    private fun showEditExcavationDialog(excavation: Excavation?) {
        val dialog = DialogEditExcavation()
        val bundle = Bundle()
        bundle.putParcelable(KEY_EXCAVATION, excavation)
        dialog.arguments = bundle

        fragmentManager.inTransaction({ replace(android.R.id.content, dialog) })
    }

    override fun onDestroy() {
        super.onDestroy()
        mAdapter?.cleanup()
    }
}