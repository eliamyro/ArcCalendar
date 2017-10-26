package com.eliamyro.arccalendar.dialogs

import android.content.DialogInterface
import android.os.Bundle
import android.support.v4.app.DialogFragment
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
import android.view.*
import com.eliamyro.arccalendar.R
import com.eliamyro.arccalendar.common.*
import com.eliamyro.arccalendar.models.Finding
import com.google.firebase.database.*
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.dialog_finding_details.*

/**
 * Created by Elias Myronidis on 3/10/17.
 */
class DialogFindingDetails: DialogFragment() {

    private val mExcavationItemId: String by lazy { arguments.getString(KEY_EXCAVATION_ITEM_ID) }
    private val mWorkItemId: String by lazy { arguments.getString(KEY_WORK_ITEM_ID) }
    private val mWorkLocationItemId: String by lazy { arguments.getString(KEY_WORK_LOCATION_ITEM_ID) }
    private val mFindingItemId: String by lazy { arguments.getString(KEY_FINDING_ITEM_ID) }
    private var mFinding: Finding? = null
    private var mFindingItemRef: DatabaseReference? = null
    private var mFindingRefListener: ValueEventListener? = null
    private var mToolbar: Toolbar? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        mFinding = arguments.getParcelable<Finding?>(KEY_FINDING)
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater?.inflate(R.layout.dialog_finding_details, container, false)
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        mToolbar = toolbar as Toolbar

        mToolbar?.title = "Finding title"

        mToolbar.let {
            (activity as AppCompatActivity).setSupportActionBar(it)

            val actionBar = (activity as AppCompatActivity).supportActionBar
            actionBar?.setDisplayHomeAsUpEnabled(true)
            actionBar?.setHomeAsUpIndicator(R.drawable.ic_close_white_24dp)
        }

        setHasOptionsMenu(true)

        view?.setOnClickListener { }

        updateFields()

        loadFindingDetails()
    }

    private fun loadFindingDetails() {
        val findingPath = "$FIREBASE_LOCATION_FINDINGS/$mExcavationItemId/$mWorkItemId/$mWorkLocationItemId/$mFindingItemId"
        mFindingItemRef = FirebaseDatabase.getInstance().reference.child(findingPath)
        mFindingRefListener = mFindingItemRef?.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                mFinding = dataSnapshot.getValue<Finding>(Finding::class.java)

                if (mFinding == null) {
                    fragmentManager?.popBackStack()
                }
            }

            override fun onCancelled(databaseError: DatabaseError) {}
        })
    }

    private fun updateFields(){
        mToolbar?.title = mFinding?.name
        iv_finding.setImageResource(R.mipmap.ic_launcher)
        tv_finding_name?.text = mFinding?.name
        tv_finding_description?.text = mFinding?.description
        if(mFinding?.imageUrl != ""){
            Picasso.with(activity).load(mFinding?.imageUrl).into(iv_finding)
        }
    }


    override fun onCreateOptionsMenu(menu: Menu?, inflater: MenuInflater?) {
        inflater?.inflate(R.menu.menu_details, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {

        when(item?.itemId){
            R.id.action_delete -> showDeleteFindingDialog()
            R.id.action_edit -> showEditFindingDialog()
        }

        return super.onOptionsItemSelected(item)
    }

    private fun showDeleteFindingDialog(){
        val dialog = DialogDeleteFinding()
        val bundle = Bundle()
        bundle.putString(KEY_EXCAVATION_ITEM_ID, mExcavationItemId)
        bundle.putString(KEY_WORK_ITEM_ID, mWorkItemId)
        bundle.putString(KEY_WORK_LOCATION_ITEM_ID, mWorkLocationItemId)
        bundle.putString(KEY_FINDING_ITEM_ID, mFindingItemId)
        dialog.arguments = bundle

        dialog.show(fragmentManager, DELETE_FINDING_DIALOG)
    }

    private fun showEditFindingDialog(){
        val dialog = DialogEditFinding()
        val bundle = Bundle()
        bundle.putParcelable(KEY_FINDING, mFinding)
        bundle.putString(KEY_EXCAVATION_ITEM_ID, mExcavationItemId)
        bundle.putString(KEY_WORK_ITEM_ID, mWorkItemId)
        bundle.putString(KEY_WORK_LOCATION_ITEM_ID, mWorkLocationItemId)
        bundle.putString(KEY_FINDING_ITEM_ID, mFindingItemId)
        dialog.arguments = bundle

        fragmentManager.inTransaction { replace(android.R.id.content, dialog) }
    }

    override fun onDismiss(dialog: DialogInterface?) {
        super.onDismiss(dialog)

        // Remove the event listener
        mFindingItemRef?.removeEventListener(mFindingRefListener)
    }
}