package com.eliamyro.arccalendar.dialogs

import android.content.DialogInterface
import android.os.Bundle
import android.support.v4.app.DialogFragment
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
import android.view.*
import com.eliamyro.arccalendar.R
import com.eliamyro.arccalendar.models.Excavation
import kotlinx.android.synthetic.main.dialog_excavation_details.*
import com.eliamyro.arccalendar.common.*
import com.google.firebase.database.*


/**
* Created by Elias Myronidis on 30/8/17.
* LinkedIn: https://www.linkedin.com/in/eliasmyronidis/
*/

class DialogExcavationDetails : DialogFragment() {

    var mExcavation: Excavation? = null
    private val itemId: String by lazy { arguments.getString(KEY_EXCAVATION_ITEM_ID) }

    private var mExcavationItemRef: DatabaseReference? = null
    private var mExcavationRefListener: ValueEventListener? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        mExcavation = arguments.getParcelable<Excavation?>(KEY_EXCAVATION)
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater?.inflate(R.layout.dialog_excavation_details, container, false)
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        val toolbar = toolbar as Toolbar?

        toolbar?.title = mExcavation?.place

        toolbar?.let {
            (activity as AppCompatActivity).setSupportActionBar(it)

            val actionBar = (activity as AppCompatActivity).supportActionBar
            actionBar?.setDisplayHomeAsUpEnabled(true)
            actionBar?.setHomeAsUpIndicator(R.drawable.ic_close_white_24dp)
        }

        setHasOptionsMenu(true)
        view?.setOnClickListener({})

        updateFields()

        // Load the details of the excavation item.
       loadExcavationDetails(itemId)
    }

    private fun loadExcavationDetails(itemId: String){
        mExcavationItemRef = FirebaseDatabase.getInstance().reference.child(FIREBASE_LOCATION_EXCAVATION_LISTS + "/" + itemId)
        mExcavationRefListener = mExcavationItemRef?.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                mExcavation = dataSnapshot.getValue<Excavation>(Excavation::class.java)
                updateFields()

                if (mExcavation == null) {
                    fragmentManager?.popBackStack()
                }

            }

            override fun onCancelled(databaseError: DatabaseError) {}
        })
    }

    private fun updateFields(){
        tv_excavation_place?.text = mExcavation?.place
        tv_organisation_value?.text = mExcavation?.organisation
        tv_description?.text = mExcavation?.description
    }

    override fun onCreateOptionsMenu(menu: Menu?, inflater: MenuInflater?) {
        menu?.clear()
        inflater?.inflate(R.menu.menu_details, menu)
        return super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {

        when(item?.itemId){
            R.id.action_delete -> showDeleteExcavationDialog(itemId)
            R.id.action_edit -> showEditExcavationDialog(mExcavation)
        }

        return super.onOptionsItemSelected(item)
    }

    private fun showDeleteExcavationDialog(itemId: String?) {
        val dialog = DialogDeleteExcavation()
        val bundle = Bundle()
        bundle.putString(KEY_EXCAVATION_ITEM_ID, itemId)
        dialog.arguments = bundle

        dialog.show(fragmentManager, DELETE_EXCAVATION_DIALOG)
    }

    private fun showEditExcavationDialog(excavation: Excavation?){
        val dialog = DialogEditExcavation()
        val bundle = Bundle()
        bundle.putParcelable(KEY_EXCAVATION, excavation)
        bundle.putString(KEY_EXCAVATION_ITEM_ID, itemId)
        dialog.arguments = bundle

        fragmentManager.inTransaction { replace(android.R.id.content, dialog) }
    }

    override fun onDismiss(dialog: DialogInterface?) {
        super.onDismiss(dialog)

        // Remove the event listener
        mExcavationItemRef?.removeEventListener(mExcavationRefListener)
    }
}

