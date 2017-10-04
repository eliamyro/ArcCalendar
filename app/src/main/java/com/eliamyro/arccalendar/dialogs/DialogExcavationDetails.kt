package com.eliamyro.arccalendar.dialogs

import android.content.DialogInterface
import android.os.Bundle
import android.support.v4.app.DialogFragment
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
import android.view.*
import com.eliamyro.arccalendar.R
import com.eliamyro.arccalendar.contracts.ContractDialogDetailsExcavation
import com.eliamyro.arccalendar.models.Excavation
import com.eliamyro.arccalendar.presenters.PresenterDialogExcavationDetails
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener
import kotlinx.android.synthetic.main.dialog_excavation_details.*
import com.eliamyro.arccalendar.common.*


/**
 * Created by Elias Myronidis on 30/8/17.
 */


class DialogExcavationDetails : DialogFragment(), ContractDialogDetailsExcavation.Views {

    private val mPresenter: ContractDialogDetailsExcavation.Actions by lazy { PresenterDialogExcavationDetails(this)}
    var mExcavation: Excavation? = null
    private val itemId: String by lazy { arguments.getString(KEY_EXCAVATION_ITEM_ID) }

    private var mExcavationItemRef: DatabaseReference? = null
    private var mExcavationRefListener: ValueEventListener? = null

    companion object {
        private val TAG: String = com.eliamyro.arccalendar.dialogs.DialogExcavationDetails::class.java.simpleName
    }

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
        mPresenter.loadExcavationDetails(itemId)
    }

    private fun updateFields(){
        tv_excavation_place?.text = mExcavation?.place
        tv_organisation_value?.text = mExcavation?.organisation
        tv_description?.text = mExcavation?.description
    }

    override fun updateExcavation(excavation: Excavation?) {
        mExcavation = excavation
        updateFields()
    }

    override fun removeFragment() {
        this.fragmentManager.popBackStack()
    }

    override fun onCreateOptionsMenu(menu: Menu?, inflater: MenuInflater?) {
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

