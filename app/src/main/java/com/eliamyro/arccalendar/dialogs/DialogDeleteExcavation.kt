package com.eliamyro.arccalendar.dialogs

import android.app.Dialog
import android.os.Bundle
import android.support.v4.app.DialogFragment
import android.support.v7.app.AlertDialog
import android.util.Log
import com.eliamyro.arccalendar.R
import com.eliamyro.arccalendar.common.KEY_EXCAVATION_ITEM_ID
import com.eliamyro.arccalendar.contracts.ContractDialogDeleteExcavation
import com.eliamyro.arccalendar.contracts.ContractDialogDeleteExcavation.Views
import com.eliamyro.arccalendar.presenters.PresenterDialogDeleteExcavation
import kotlinx.android.synthetic.main.dialog_information.view.*

/**
 * Created by Elias Myronidis on 29/8/17.
 */
class DialogDeleteExcavation : DialogFragment(), Views {

    init {
        this.isCancelable = false
    }

    companion object {
        private val TAG: String = DialogDeleteExcavation::class.java.simpleName
    }

    private val mPresenter: ContractDialogDeleteExcavation.Actions by lazy { PresenterDialogDeleteExcavation() }
    private val itemId: String by lazy { arguments.getString(KEY_EXCAVATION_ITEM_ID) }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val builder = AlertDialog.Builder(activity)
        val inflater = activity.layoutInflater
        val view = inflater.inflate(R.layout.dialog_information, null)
        builder.setView(view)

        view.tv_message.text = getString(R.string.delete_excavation)

        builder.setNegativeButton(getString(R.string.cancel), { _, _ -> dismiss() })
        builder.setPositiveButton(getString(R.string.delete), { _, _ -> mPresenter.deleteExcavation(itemId)})

        return builder.create()
    }

}