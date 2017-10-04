package com.eliamyro.arccalendar.dialogs

import android.content.DialogInterface
import android.os.Bundle
import android.support.v4.app.DialogFragment
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
import android.view.*
import com.eliamyro.arccalendar.R
import com.eliamyro.arccalendar.common.*
import com.eliamyro.arccalendar.contracts.ContractDialogDetailsFinding
import com.eliamyro.arccalendar.models.Finding
import com.eliamyro.arccalendar.presenters.PresenterDialogFindingDetails
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener
import kotlinx.android.synthetic.main.dialog_finding_details.*

/**
 * Created by Elias Myronidis on 3/10/17.
 */
class DialogFindingDetails: DialogFragment(), ContractDialogDetailsFinding.Views {
    override fun removeFragment() {
        this.fragmentManager.popBackStack()
    }

    override fun updateFinding(finding: Finding?) {
        mFinding = finding
        updateFields()
    }

    private val mExcavationItemId: String by lazy { arguments.getString(KEY_EXCAVATION_ITEM_ID) }
    private val mWorkItemId: String by lazy { arguments.getString(KEY_WORK_ITEM_ID) }
    private val mWorkLocationItemId: String by lazy { arguments.getString(KEY_WORK_LOCATION_ITEM_ID) }
    private val mFindingItemId: String by lazy { arguments.getString(KEY_FINDING_ITEM_ID) }
    private var mFinding: Finding? = null
    private val mPresenter: ContractDialogDetailsFinding.Actions by lazy { PresenterDialogFindingDetails(this)}
    private var mFindingItemRef: DatabaseReference? = null
    private var mFindingRefListener: ValueEventListener? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        mFinding = arguments.getParcelable<Finding?>(KEY_FINDING)
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater?.inflate(R.layout.dialog_finding_details, container, false)
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        val toolbar = toolbar as Toolbar

        toolbar.title = "Finding title"

        toolbar.let {
            (activity as AppCompatActivity).setSupportActionBar(it)

            val actionBar = (activity as AppCompatActivity).supportActionBar
            actionBar?.setDisplayHomeAsUpEnabled(true)
            actionBar?.setHomeAsUpIndicator(R.drawable.ic_close_white_24dp)
        }

        setHasOptionsMenu(true)

        view?.setOnClickListener { }

        updateFields()

        val findingPath = "$FIREBASE_LOCATION_FINDINGS/$mExcavationItemId/$mWorkItemId/$mWorkLocationItemId/$mFindingItemId"
        // Load the details of the findings item.
        mPresenter.loadFindingDetails(findingPath)
    }

    private fun updateFields(){
        iv_finding.setImageResource(R.mipmap.ic_launcher)
        tv_finding_name?.text = mFinding?.name
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
        val dialog = DialogFindingEdit()
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