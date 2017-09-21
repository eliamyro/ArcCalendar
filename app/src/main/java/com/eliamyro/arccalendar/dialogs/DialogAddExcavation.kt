package com.eliamyro.arccalendar.dialogs

import android.os.Bundle
import android.support.v4.app.DialogFragment
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
import android.view.*
import com.eliamyro.arccalendar.R
import com.eliamyro.arccalendar.contracts.ContractDialogAddExcavation
import com.eliamyro.arccalendar.presenters.PresenterDialogAddExcavation
import kotlinx.android.synthetic.main.dialog_add_excavation.*

/**
 * Created by Elias Myronidis on 24/8/17.
 */
class DialogAddExcavation : DialogFragment(), ContractDialogAddExcavation.Views {

    companion object {
        private val TAG: String = DialogAddExcavation::class.java.simpleName
    }

    private val mPresenter: ContractDialogAddExcavation.Actions by lazy { PresenterDialogAddExcavation(this) }


    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater?.inflate(R.layout.dialog_add_excavation, container, false)
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        val toolbar = toolbar as Toolbar?

        toolbar?.title = getString(R.string.create_excavation)

        toolbar?.let {
            (activity as AppCompatActivity).setSupportActionBar(it)

            val actionBar = (activity as AppCompatActivity).supportActionBar
            actionBar?.setDisplayHomeAsUpEnabled(true)
            actionBar?.setHomeAsUpIndicator(R.drawable.ic_close_white_24dp)
        }

        view?.setOnClickListener({})

        setHasOptionsMenu(true)
    }

    override fun onCreateOptionsMenu(menu: Menu?, inflater: MenuInflater?) {
        menu?.clear()
        activity.menuInflater.inflate(R.menu.menu_add_excavation_work, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {

        when (item?.itemId) {
            R.id.action_save -> {

                if (mPresenter.addExcavation()) {

                    // Remove the fragment from the backstack
                    fragmentManager.popBackStack()
                    dismiss()
                }
            }
        }

        return super.onOptionsItemSelected(item)
    }
}