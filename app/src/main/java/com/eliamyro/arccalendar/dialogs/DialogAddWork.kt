package com.eliamyro.arccalendar.dialogs

import android.app.DatePickerDialog
import android.os.Build
import android.os.Bundle
import android.support.annotation.RequiresApi
import android.support.v4.app.DialogFragment
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
import android.view.*
import android.widget.CalendarView
import android.widget.DatePicker
import com.eliamyro.arccalendar.R
import com.eliamyro.arccalendar.common.KEY_EXCAVATION_ITEM_ID
import com.eliamyro.arccalendar.contracts.ContractDialogAddWork
import com.eliamyro.arccalendar.models.Work
import com.eliamyro.arccalendar.presenters.PresenterDialogAddWork
import kotlinx.android.synthetic.main.dialog_add_work.*
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.debug

/**
 * Created by Elias Myronidis on 6/9/17.
 */
class DialogAddWork : DialogFragment(), ContractDialogAddWork.Views, AnkoLogger {


    private val mPresenter: ContractDialogAddWork.Actions by lazy { PresenterDialogAddWork(this) }
    private val mExcavationId: String by lazy { arguments.getString(KEY_EXCAVATION_ITEM_ID) }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater?.inflate(R.layout.dialog_add_work, container, false)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {

        val toolbar = toolbar as Toolbar?

        toolbar?.title = getString(R.string.create_work)

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
                val directorsList: List<String> = tg_work_directors_list.tags.asList()
                val archaeologistsList: List<String> = tg_work_archaeologists_list.tags.asList()
                val studentsList: List<String> = tg_work_students_list.tags.asList()
                val description: String = et_work_description.text.toString()
                val work = Work(description, directorsList, archaeologistsList, studentsList)

                if (mPresenter.addWork(mExcavationId, work)) {

                    // Remove the fragment from the backstack
                    fragmentManager.popBackStack()
                    dismiss()
                }
            }
        }

        return super.onOptionsItemSelected(item)
    }
}