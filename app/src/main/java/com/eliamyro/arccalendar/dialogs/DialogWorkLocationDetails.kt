package com.eliamyro.arccalendar.dialogs

import android.content.DialogInterface
import android.os.Bundle
import android.support.v4.app.DialogFragment
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
import android.view.*
import com.eliamyro.arccalendar.R
import com.eliamyro.arccalendar.common.*
import com.eliamyro.arccalendar.models.WorkLocation
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.dialog_work_location_details.*

/**
 * Created by Elias Myronidis on 16/10/17.
 */
class DialogWorkLocationDetails: DialogFragment() {

    var mWorkLocation: WorkLocation? = null
    private val mExcavationItemId: String by lazy { arguments.getString(KEY_EXCAVATION_ITEM_ID) }
    private val mWorkItemId: String by lazy { arguments.getString(KEY_WORK_ITEM_ID) }
    private val mWorkLocationItemId: String by lazy { arguments.getString(KEY_WORK_LOCATION_ITEM_ID) }

    private var mWorkLocationItemRef: DatabaseReference? = null
    private var mWorkLocationRefListener: ValueEventListener? = null

    companion object {
        private val TAG: String = com.eliamyro.arccalendar.dialogs.DialogExcavationDetails::class.java.simpleName
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        mWorkLocation = arguments.getParcelable<WorkLocation?>(KEY_WORK_LOCATION)
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // TODO: Change the layout
        return inflater?.inflate(R.layout.dialog_work_location_details, container, false)
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        val toolbar = toolbar as Toolbar?

        toolbar?.title = mWorkLocation?.location

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
        loadWorkLocationDetails(mWorkLocationItemId)
    }

    private fun loadWorkLocationDetails(itemId: String){
        mWorkLocationItemRef = FirebaseDatabase.getInstance().reference.child(FIREBASE_LOCATION_WORK_LOCATIONS + "/" + mExcavationItemId
        + "/" + mWorkItemId + "/" + mWorkLocationItemId)
        mWorkLocationRefListener = mWorkLocationItemRef?.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                mWorkLocation = dataSnapshot.getValue<WorkLocation>(WorkLocation::class.java)
                updateFields()

                if (mWorkLocation == null) {
                    fragmentManager?.popBackStack()
                }

            }

            override fun onCancelled(databaseError: DatabaseError) {}
        })
    }

    private fun updateFields(){
        tv_work_location?.text = mWorkLocation?.location
        tv_work_location_description?.text = mWorkLocation?.description
    }

    override fun onCreateOptionsMenu(menu: Menu?, inflater: MenuInflater?) {
        menu?.clear()
        inflater?.inflate(R.menu.menu_details, menu)
        return super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {

        when(item?.itemId){
            R.id.action_delete -> showDeleteWorkLocationDialog()
//            R.id.action_edit -> showEditExcavationDialog(mExcavation)
        }

        return super.onOptionsItemSelected(item)
    }

    private fun showDeleteWorkLocationDialog() {
        val dialog = DialogDeleteWorkLocation()
        val bundle = Bundle()
        bundle.putString(KEY_EXCAVATION_ITEM_ID, mExcavationItemId)
        bundle.putString(KEY_WORK_ITEM_ID, mWorkItemId)
        bundle.putString(KEY_WORK_LOCATION_ITEM_ID, mWorkLocationItemId)

        dialog.arguments = bundle

        dialog.show(fragmentManager, DELETE_WORK_LOCATION_DIALOG)
    }

//    private fun showEditExcavationDialog(excavation: Excavation?){
//        val dialog = DialogEditExcavation()
//        val bundle = Bundle()
//        bundle.putParcelable(KEY_EXCAVATION, excavation)
//        bundle.putString(KEY_EXCAVATION_ITEM_ID, itemId)
//        dialog.arguments = bundle
//
//        fragmentManager.inTransaction { replace(android.R.id.content, dialog) }
//
//    }
//
    override fun onDismiss(dialog: DialogInterface?) {
        super.onDismiss(dialog)

        // Remove the event listener
        mWorkLocationItemRef?.removeEventListener(mWorkLocationRefListener)
    }
}