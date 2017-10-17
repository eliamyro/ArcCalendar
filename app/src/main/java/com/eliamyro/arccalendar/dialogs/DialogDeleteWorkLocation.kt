package com.eliamyro.arccalendar.dialogs

import android.app.Dialog
import android.os.Bundle
import android.support.v4.app.DialogFragment
import android.support.v7.app.AlertDialog
import com.eliamyro.arccalendar.R
import com.eliamyro.arccalendar.common.KEY_EXCAVATION_ITEM_ID
import com.eliamyro.arccalendar.common.KEY_WORK_ITEM_ID
import com.eliamyro.arccalendar.common.KEY_WORK_LOCATION_ITEM_ID
import com.eliamyro.arccalendar.contracts.ContractDialogDeleteWorkLocation
import com.eliamyro.arccalendar.contracts.ContractDialogDeleteWorkLocation.Views
import com.eliamyro.arccalendar.presenters.PresenterDialogDeleteWorkLocation
import kotlinx.android.synthetic.main.dialog_information.view.*

/**
 * Created by Elias Myronidis on 17/10/17.
 */
class DialogDeleteWorkLocation: DialogFragment(), Views {

    init {
        this.isCancelable = false
    }

    companion object {
        private val TAG: String = DialogDeleteWork::class.java.simpleName
    }

    private val mPresenter: ContractDialogDeleteWorkLocation.Actions by lazy { PresenterDialogDeleteWorkLocation(this) }
    private val mExcavationItemId: String by lazy { arguments.getString(KEY_EXCAVATION_ITEM_ID) }
    private val mWorkItemId: String by lazy { arguments.getString(KEY_WORK_ITEM_ID) }
    private val mWorkLocationItemId: String by lazy { arguments.getString(KEY_WORK_LOCATION_ITEM_ID) }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val builder = AlertDialog.Builder(activity)
        val inflater = activity.layoutInflater
        val view = inflater.inflate(R.layout.dialog_information, null)
        builder.setView(view)

        view.tv_message.text = "Delete work location?"

        builder.setNegativeButton(getString(R.string.cancel), { _, _ -> dismiss() })
        builder.setPositiveButton(getString(R.string.delete), { _, _ -> mPresenter.deleteWorkLocation(mExcavationItemId, mWorkItemId, mWorkLocationItemId)})

        return builder.create()
    }
}