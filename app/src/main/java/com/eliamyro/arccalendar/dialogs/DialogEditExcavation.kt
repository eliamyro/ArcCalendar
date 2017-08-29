package com.eliamyro.arccalendar.dialogs

import android.os.Bundle
import android.support.v4.app.DialogFragment
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.eliamyro.arccalendar.R
import com.eliamyro.arccalendar.contracts.ContractDialogEditExcavation
import com.eliamyro.arccalendar.contracts.ContractDialogEditExcavation.Views
import com.eliamyro.arccalendar.models.Excavation
import com.eliamyro.arccalendar.presenters.PresenterDialogEditExcavation
import kotlinx.android.synthetic.main.dialog_add_excavation.*

/**
 * Created by Elias Myronidis on 28/8/17.
 */
class DialogEditExcavation : DialogFragment(), Views {

    private val excavation: Excavation by lazy { arguments.getParcelable<Excavation>(KEY_EXCAVATION) }
    private val mPresenter: ContractDialogEditExcavation.Actions by lazy { PresenterDialogEditExcavation(this) }

    companion object {
        private val TAG: String = DialogEditExcavation::class.java.simpleName
        private const val KEY_EXCAVATION: String = "excavation"
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater?.inflate(R.layout.dialog_add_excavation, container, false)
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        val toolbar = toolbar as Toolbar?

        toolbar?.title = getString(R.string.edit_excavation)

        toolbar?.let {
            (activity as AppCompatActivity).setSupportActionBar(it)

            val actionBar = (activity as AppCompatActivity).supportActionBar
            actionBar?.setDisplayHomeAsUpEnabled(true)
            actionBar?.setHomeAsUpIndicator(R.drawable.ic_close_white_24dp)
        }

        setHasOptionsMenu(true)

        et_excavation_place.setText(excavation.place)
        et_organisation.setText(excavation.organization)

        mPresenter.editExcavation()
    }
}