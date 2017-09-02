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
import com.eliamyro.arccalendar.common.KEY_EXCAVATION
import com.eliamyro.arccalendar.common.KEY_EXCAVATION_ITEM_ID
import com.eliamyro.arccalendar.contracts.ContractDialogEditExcavation
import com.eliamyro.arccalendar.contracts.ContractDialogEditExcavation.Views
import com.eliamyro.arccalendar.models.Excavation
import com.eliamyro.arccalendar.presenters.PresenterDialogEditExcavation
import kotlinx.android.synthetic.main.dialog_add_excavation.*

/**
 * Created by Elias Myronidis on 28/8/17.
 */
class DialogEditExcavation : DialogFragment(), Views {

    private val mExcavation: Excavation by lazy { arguments.getParcelable<Excavation>(KEY_EXCAVATION) }
    private val mExcavationItemId: String by lazy { arguments.getString(KEY_EXCAVATION_ITEM_ID) }
    private val mPresenter: ContractDialogEditExcavation.Actions by lazy { PresenterDialogEditExcavation(this) }
    private var mState: State? = null

    private enum class State {VISIBLE, HIDDEN }

    companion object {
        private val TAG: String = DialogEditExcavation::class.java.simpleName
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)

        mState = State.HIDDEN
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater?.inflate(R.layout.dialog_add_excavation, container, false)
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        val toolbar = toolbar as Toolbar?

        toolbar?.title = getString(R.string.edit_excavation)

        toolbar?.let {
            (activity as AppCompatActivity).setSupportActionBar(it)

            var actionBar = (activity as AppCompatActivity).supportActionBar
            actionBar?.setDisplayHomeAsUpEnabled(true)
            actionBar?.setHomeAsUpIndicator(R.drawable.ic_action_back)
        }

        view?.setOnClickListener({})

        et_excavation_place.setText(mExcavation.place)
        et_organisation.setText(mExcavation.organisation)
        et_description.setText(mExcavation.description)


        val textWatcher = object : TextWatcher {
            override fun afterTextChanged(p0: Editable?) {

            }

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                mState = if ((mExcavation.place == et_excavation_place.text.toString())
                        && (mExcavation.organisation == et_organisation.text.toString())
                        && (mExcavation.description == et_description.text.toString())) {
                    State.HIDDEN
                } else {
                    State.VISIBLE
                }
                activity.invalidateOptionsMenu()
            }
        }

        et_excavation_place.addTextChangedListener(textWatcher)
        et_organisation.addTextChangedListener(textWatcher)
        et_description.addTextChangedListener(textWatcher)
    }


    override fun onCreateOptionsMenu(menu: Menu?, inflater: MenuInflater?) {
        inflater?.inflate(R.menu.menu_edit_excavation, menu)

        menu?.findItem(R.id.action_save_edit_dialog_changes)?.isVisible = mState == State.VISIBLE

        return super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {

        when (item?.itemId) {
            R.id.action_save_edit_dialog_changes -> {
                val excavation = Excavation(et_excavation_place.text.toString(),
                        et_organisation.text.toString(), et_description.text.toString())
                if (mPresenter.editExcavation(mExcavationItemId, excavation)){
                    fragmentManager?.popBackStack()
                } else {
                    Log.d(TAG, "Update was unsuccessful.")
                }

            }

        }
        return super.onOptionsItemSelected(item)
    }
}