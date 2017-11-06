package com.eliamyro.arccalendar.fragments

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.*
import com.eliamyro.arccalendar.R
import com.eliamyro.arccalendar.common.*
import com.eliamyro.arccalendar.contracts.ContractFragmentWorkInfo
import com.eliamyro.arccalendar.dialogs.DialogEditWork
import com.eliamyro.arccalendar.models.Work
import com.eliamyro.arccalendar.presenters.PresenterFragmentWorkInfo
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.activity_work_tabs.*
import kotlinx.android.synthetic.main.fragment_work_info.*

/**
* Created by Elias Myronidis on 19/9/17.
* LinkedIn: https://www.linkedin.com/in/eliasmyronidis/
*/

class FragmentWorkInfo : Fragment(), ContractFragmentWorkInfo.Views {

    private val excavationItemId: String by lazy { arguments.getString(KEY_EXCAVATION_ITEM_ID) }
    private val workItemId: String by lazy { arguments.getString(KEY_WORK_ITEM_ID) }
    private var mWork: Work? = null
    private val reference: DatabaseReference by lazy {
        FirebaseDatabase.getInstance().reference
                .child("$FIREBASE_LOCATION_EXCAVATION_WORKS/$excavationItemId/$workItemId")
    }

    private val mPresenter: ContractFragmentWorkInfo.Actions by lazy { PresenterFragmentWorkInfo(this) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater?.inflate(R.layout.fragment_work_info, container, false)
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        mPresenter.loadWorkInfo(reference)
    }


    override fun onCreateOptionsMenu(menu: Menu?, inflater: MenuInflater?) {
        inflater?.inflate(R.menu.menu_work_info, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {

        when (item?.itemId) {
            R.id.action_edit_work -> {
                showEditWorkDialog()
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun displayInfo(work: Work?) {
        mWork = work
        if (work != null) {
            activity.toolbar.setDateToToolbar(work.workDate)
            tv_work_description.text = work.description
            tg_directors.setTags(work.directorsList)
            tg_archaelogists.setTags(work.archaeologistsList)
            tg_students.setTags(work.studentsList)
        } else {
            activity.finish()
        }
    }

    override fun removeFragment() {
        this.fragmentManager.popBackStack()
    }

    override fun onDestroy() {
        super.onDestroy()
        mPresenter.destroyValueEventListener()
    }

    private fun showEditWorkDialog() {
        val dialog = DialogEditWork()
        val bundle = Bundle()
        bundle.putString(KEY_EXCAVATION_ITEM_ID, excavationItemId)
        bundle.putString(KEY_WORK_ITEM_ID, workItemId)
        bundle.putParcelable(KEY_WORK, mWork)
        dialog.arguments = bundle
        fragmentManager.inTransaction(true) { replace(android.R.id.content, dialog) }
    }



}