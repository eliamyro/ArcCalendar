package com.eliamyro.arccalendar.fragments

import android.os.Bundle
import android.support.v4.app.Fragment
import android.util.Log
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import com.eliamyro.arccalendar.R
import com.eliamyro.arccalendar.common.*
import com.eliamyro.arccalendar.contracts.ContractFragmentWorkInfo
import com.eliamyro.arccalendar.models.Work
import com.eliamyro.arccalendar.presenters.PresenterFragmentWorkInfo
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.fragment_work_info.*

/**
 * Created by Elias Myronidis on 19/9/17.
 */
class FragmentWorkInfo: Fragment(), ContractFragmentWorkInfo.Views {

    companion object {
        private val TAG: String = FragmentWorkInfo::class.java.simpleName
    }

    private val excavationItemId: String by lazy { arguments.getString(KEY_EXCAVATION_ITEM_ID) }
    private val workItemId: String by lazy { arguments.getString(KEY_WORK_ITEM_ID) }
    private val reference: DatabaseReference by lazy { FirebaseDatabase.getInstance().reference
            .child("$FIREBASE_LOCATION_EXCAVATION_WORKS/$excavationItemId/$workItemId") }

    private val mPresenter: ContractFragmentWorkInfo.Actions by lazy { PresenterFragmentWorkInfo(this) }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater?.inflate(R.layout.fragment_work_info, container, false)
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        mPresenter.loadWorkInfo(reference)
    }

    override fun displayInfo(work: Work?) {
        tg_directors.setTags(work?.directorsList)
        tg_archaelogists.setTags(work?.archaeologistsList)
        tg_students.setTags(work?.studentsList)
    }

    override fun removeFragment() {
        this.fragmentManager.popBackStack()
    }

    override fun onDestroy() {
        super.onDestroy()
        mPresenter.destroyValueEventListener()
    }

}