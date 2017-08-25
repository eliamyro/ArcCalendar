package com.eliamyro.arccalendar.fragments

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.Toast
import com.eliamyro.arccalendar.R
import com.eliamyro.arccalendar.common.FIREBASE_LOCATION_EXCAVATION_LISTS
import com.eliamyro.arccalendar.contracts.ContractFragmentMain.Views
import com.eliamyro.arccalendar.models.Excavation
import com.eliamyro.arccalendar.presenters.PresenterFragmentMain
import com.eliamyro.arccalendar.viewHolders.ExcavationHolder
import com.firebase.ui.database.FirebaseRecyclerAdapter
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.fragment_excavations_list.*
import kotlinx.android.synthetic.main.item_row_excavation.view.*

/**
 * Created by Elias Myronidis on 23/8/17.
 */
class FragmentExcavationsList : Fragment(), Views {
    var mAdapter:  FirebaseRecyclerAdapter<Excavation, ExcavationHolder>? = null

    companion object {
        private val TAG: String = FragmentExcavationsList::class.java.simpleName
    }

    private var mPresenter: PresenterFragmentMain? = null

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        return inflater?.inflate(R.layout.fragment_excavations_list, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        mPresenter = PresenterFragmentMain(this)

        // Fab onClickListener
        fab_add_excavation.setOnClickListener { mPresenter?.addExcavation(activity) }

        rv_excavations.layoutManager = LinearLayoutManager(activity)

        val reference: DatabaseReference = FirebaseDatabase.getInstance().reference.child(FIREBASE_LOCATION_EXCAVATION_LISTS)
        mAdapter = object: FirebaseRecyclerAdapter<Excavation, ExcavationHolder>(
                Excavation::class.java,
                R.layout.item_row_excavation,
                ExcavationHolder::class.java,
                reference) {

            override fun populateViewHolder(holder:ExcavationHolder, excavation: Excavation, position:Int) {
                holder.bindExcavationView(excavation)
            }
        }

        rv_excavations.adapter = mAdapter
    }

    override fun displayToast(message: String) {
        Toast.makeText(activity, message, Toast.LENGTH_LONG).show()
    }

    override fun onDestroy() {
        super.onDestroy()

        mAdapter?.cleanup()
    }

}