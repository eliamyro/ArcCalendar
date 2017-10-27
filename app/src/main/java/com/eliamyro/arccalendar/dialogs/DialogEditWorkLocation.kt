package com.eliamyro.arccalendar.dialogs

import android.os.Bundle
import android.support.v4.app.DialogFragment
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.*
import com.eliamyro.arccalendar.R
import com.eliamyro.arccalendar.common.KEY_EXCAVATION_ITEM_ID
import com.eliamyro.arccalendar.common.KEY_WORK_ITEM_ID
import com.eliamyro.arccalendar.common.KEY_WORK_LOCATION
import com.eliamyro.arccalendar.common.KEY_WORK_LOCATION_ITEM_ID
import com.eliamyro.arccalendar.contracts.ContractDialogEditWorkLocation
import com.eliamyro.arccalendar.contracts.ContractDialogEditWorkLocation.Views
import com.eliamyro.arccalendar.models.WorkLocation
import com.eliamyro.arccalendar.presenters.PresenterDialogEditWorkLocation
import kotlinx.android.synthetic.main.dialog_add_work_location.*

/**
* Created by Elias Myronidis on 23/10/17.
* LinkedIn: https://www.linkedin.com/in/eliasmyronidis/
*/

class DialogEditWorkLocation: DialogFragment(), Views {

    private val mWorkLocation: WorkLocation by lazy { arguments.getParcelable<WorkLocation>(KEY_WORK_LOCATION) }
    private val mExcavationItemId: String by lazy { arguments.getString(KEY_EXCAVATION_ITEM_ID) }
    private val mWorkItemId: String by lazy { arguments.getString(KEY_WORK_ITEM_ID) }
    private val mWorkLocationItemId: String by lazy { arguments.getString(KEY_WORK_LOCATION_ITEM_ID) }
    private val mPresenter: ContractDialogEditWorkLocation.Actions by lazy { PresenterDialogEditWorkLocation(this) }
    private var mState: State? = null

    private enum class State {VISIBLE, HIDDEN }

    companion object {
        private val TAG: String = DialogEditWorkLocation::class.java.simpleName
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)

        mState = State.HIDDEN
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater?.inflate(R.layout.dialog_add_work_location, container, false)
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        val toolbar = toolbar as Toolbar?

        toolbar?.title = getString(R.string.edit_excavation)

        toolbar?.let {
            (activity as AppCompatActivity).setSupportActionBar(it)

            val actionBar = (activity as AppCompatActivity).supportActionBar
            actionBar?.setDisplayHomeAsUpEnabled(true)
            actionBar?.setHomeAsUpIndicator(R.drawable.ic_action_back)
        }

        view?.setOnClickListener({})

        et_work_location.setText(mWorkLocation.location)
        et_work_location_description.setText(mWorkLocation.description)

        val textWatcher = object : TextWatcher {
            override fun afterTextChanged(p0: Editable?) {

            }

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                mState = if ((mWorkLocation.location == et_work_location.text.toString())
                        && (mWorkLocation.description == et_work_location_description.text.toString())) {
                    State.HIDDEN
                } else {
                    State.VISIBLE
                }
                activity.invalidateOptionsMenu()
            }
        }

        et_work_location.addTextChangedListener(textWatcher)
        et_work_location_description.addTextChangedListener(textWatcher)
    }


    override fun onCreateOptionsMenu(menu: Menu?, inflater: MenuInflater?) {
        menu?.clear()
        inflater?.inflate(R.menu.menu_edit_excavation, menu)

        menu?.findItem(R.id.action_save_edit_dialog_changes)?.isVisible = mState == State.VISIBLE

        return super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {

        when (item?.itemId) {
            R.id.action_save_edit_dialog_changes -> {
                val location = et_work_location.text.toString()
                val description = et_work_location_description.text.toString()

                val workLocation = WorkLocation(location, description)
                if (mPresenter.editWorkLocation(mExcavationItemId, mWorkItemId, mWorkLocationItemId, workLocation)){
                    fragmentManager?.popBackStack()
                } else {
                    Log.d(TAG, "Update was unsuccessful.")
                }
            }
        }
        return super.onOptionsItemSelected(item)
    }
}