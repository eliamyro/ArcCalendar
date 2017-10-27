package com.eliamyro.arccalendar.dialogs

import android.app.Dialog
import android.os.Bundle
import android.support.v4.app.DialogFragment
import android.support.v7.app.AlertDialog
import com.eliamyro.arccalendar.R
import com.eliamyro.arccalendar.common.*
import com.eliamyro.arccalendar.contracts.ContractDialogDeleteFinding
import com.eliamyro.arccalendar.contracts.ContractDialogDeleteFinding.Views
import com.eliamyro.arccalendar.presenters.PresenterDialogDeleteFinding
import kotlinx.android.synthetic.main.dialog_information.view.*

/**
 * Created by Elias Myronidis on 4/10/17.
 */
class DialogDeleteFinding: DialogFragment(), Views {

    init {
        this.isCancelable = false
    }

    private val mPresenter: ContractDialogDeleteFinding.Actions by lazy { PresenterDialogDeleteFinding() }
    private val mExcavationItemId: String by lazy { arguments.getString(KEY_EXCAVATION_ITEM_ID) }
    private val mWorkItemId: String by lazy { arguments.getString(KEY_WORK_ITEM_ID) }
    private val mWorkLocationItemId: String by lazy { arguments.getString(KEY_WORK_LOCATION_ITEM_ID) }
    private val mFindingItemId: String by lazy { arguments.getString(KEY_FINDING_ITEM_ID) }

    companion object {
        private val TAG: String = DialogDeleteFinding::class.java.simpleName
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val builder = AlertDialog.Builder(activity)
        val inflater = activity.layoutInflater
        val view = inflater.inflate(R.layout.dialog_information, null)
        builder.setView(view)

        view.tv_message.text = getString(R.string.delete_finding)

        val itemToDeletePath = "$FIREBASE_LOCATION_FINDINGS/$mExcavationItemId/$mWorkItemId/$mWorkLocationItemId/$mFindingItemId"

        builder.setNegativeButton(getString(R.string.cancel), { _, _ -> dismiss() })
        builder.setPositiveButton(getString(R.string.delete), { _, _ -> mPresenter.deleteFinding(itemToDeletePath, mFindingItemId)})

        return builder.create()
    }
}