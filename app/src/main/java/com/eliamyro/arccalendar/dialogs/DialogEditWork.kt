package com.eliamyro.arccalendar.dialogs

import android.os.Bundle
import android.support.v4.app.DialogFragment
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
import android.util.Log
import android.view.*
import com.eliamyro.arccalendar.R
import com.eliamyro.arccalendar.common.*
import com.eliamyro.arccalendar.contracts.ContractDialogEditWork
import com.eliamyro.arccalendar.contracts.ContractDialogEditWork.Views
import com.eliamyro.arccalendar.models.Work
import com.eliamyro.arccalendar.presenters.PresenterDialogEditWork
import kotlinx.android.synthetic.main.dialog_add_work.*
import java.sql.Timestamp
import java.text.SimpleDateFormat
import java.util.*

/**
 * Created by Elias Myronidis on 11/10/17.
 */
class DialogEditWork: DialogFragment(), DialogDate.DateSetListener, Views {
    override fun setDate(timestamp: Timestamp) {
        val sFormat = SimpleDateFormat("dd MMMM yyy", Locale.getDefault())

        val date: String = sFormat.format(timestamp)
        tv_work_date.text = date
    }

    private val mWork: Work by lazy { arguments.getParcelable<Work>(KEY_WORK) }
    private val mExcavationItemId: String by lazy { arguments.getString(KEY_EXCAVATION_ITEM_ID) }
    private val mWorkItemId: String by lazy { arguments.getString(KEY_WORK_ITEM_ID) }
    private val mPresenter: ContractDialogEditWork.Actions by lazy { PresenterDialogEditWork(this) }
//    private var mState: State? = null

//    private enum class State {VISIBLE, HIDDEN }

    companion object {
        private val TAG: String = DialogEditWork::class.java.simpleName
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)

//        mState = State.HIDDEN
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater?.inflate(R.layout.dialog_add_work, container, false)
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        val toolbar = toolbar as Toolbar?

        toolbar?.title = "Edit work"

        toolbar?.let {
            (activity as AppCompatActivity).setSupportActionBar(it)

            var actionBar = (activity as AppCompatActivity).supportActionBar
            actionBar?.setDisplayHomeAsUpEnabled(true)
            actionBar?.setHomeAsUpIndicator(R.drawable.ic_action_back)
        }


        tv_work_date.text = mWork.workDate.formatDate()
        tg_work_directors_list.setTags(mWork.directorsList)
        tg_work_archaeologists_list.setTags(mWork.archaeologistsList)
        tg_work_students_list.setTags(mWork.studentsList)
        et_work_description.setText(mWork.description)

        tv_work_date.setOnClickListener { showDateDialog() }

    }

    override fun onPrepareOptionsMenu(menu: Menu?) {
        menu?.clear()
        val inflater = activity.menuInflater
        inflater?.inflate(R.menu.menu_edit_work, menu)
        super.onPrepareOptionsMenu(menu)
    }
    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item?.itemId) {
            R.id.action_save_edit_work -> {
//                val workDate: String = (tv_work_date.text.toString()).toTimestamp()
                val workDate: String = "${tv_work_date.text.toString()}".toTimestamp()
                Log.d(TAG, workDate)
                val directorsList: List<String> = tg_work_directors_list.tags.asList()
                val archaeologistsList: List<String> = tg_work_archaeologists_list.tags.asList()
                val studentsList: List<String> = tg_work_students_list.tags.asList()
                val description: String = et_work_description.text.toString()
                val work = Work(workDate, description, directorsList, archaeologistsList, studentsList)

                if (mPresenter.editWork(mExcavationItemId, mWorkItemId, work)) {
                    // Remove the fragment from the backstack
                    fragmentManager.popBackStack()
                    dismiss()
                }
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun showDateDialog(){
        val dateDialog = DialogDate()
        dateDialog.show(childFragmentManager, "dialog_date")
    }
}