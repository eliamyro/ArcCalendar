package com.eliamyro.arccalendar.dialogs

import android.app.Dialog
import android.os.Bundle
import android.support.v4.app.DialogFragment
import android.support.v7.app.AlertDialog
import com.eliamyro.arccalendar.R
import com.eliamyro.arccalendar.contracts.ContractDialogRemoveExcavation
import com.eliamyro.arccalendar.contracts.ContractDialogRemoveExcavation.Views
import com.eliamyro.arccalendar.presenters.PresenterDialogRemoveExcavation
import kotlinx.android.synthetic.main.dialog_information.view.*

/**
 * Created by Elias Myronidis on 29/8/17.
 */
class DialogRemoveExcavation : DialogFragment(), Views {

    init {
        this.isCancelable = false
    }

    private val mPresenter: ContractDialogRemoveExcavation.Actions by lazy { PresenterDialogRemoveExcavation() }
    private val itemId: String by lazy { arguments.getString("item_id") }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val builder = AlertDialog.Builder(activity)
        val inflater = activity.layoutInflater
        val view = inflater.inflate(R.layout.dialog_information, null)
        builder.setView(view)

        view.tv_message.text = getString(R.string.delete_excavation)

        builder.setNegativeButton(getString(R.string.cancel), { _, _ -> dismiss() })
        builder.setPositiveButton(getString(R.string.delete), { _, _ -> mPresenter.removeExcavation(itemId) })

        return builder.create()
    }

}