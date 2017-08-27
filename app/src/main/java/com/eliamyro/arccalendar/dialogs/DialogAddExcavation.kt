package com.eliamyro.arccalendar.dialogs

import android.app.Dialog
import android.os.Bundle
import android.support.v4.app.DialogFragment
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
import android.util.Log
import android.view.*
import android.widget.Toast
import com.eliamyro.arccalendar.R
import com.eliamyro.arccalendar.common.FIREBASE_LOCATION_EXCAVATION_LISTS
import com.eliamyro.arccalendar.models.Excavation
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.dialog_add_excavation.*

/**
 * Created by Elias Myronidis on 24/8/17.
 */
class DialogAddExcavation : DialogFragment() {

    companion object {
        private val TAG: String = DialogAddExcavation::class.java.simpleName
    }


    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val rootView = inflater?.inflate(R.layout.dialog_add_excavation, container, false)

        val toolbar: Toolbar? = rootView?.findViewById(R.id.toolbar)

        toolbar?.title = getString(R.string.create_excavation)

        toolbar?.let {
            (activity as AppCompatActivity).setSupportActionBar(it)

            val actionBar = (activity as AppCompatActivity).supportActionBar
            actionBar?.setDisplayHomeAsUpEnabled(true)
            actionBar?.setHomeAsUpIndicator(R.drawable.ic_close_white_24dp)
        }

        setHasOptionsMenu(true)

        return rootView
    }

    override fun onCreateOptionsMenu(menu: Menu?, inflater: MenuInflater?) {
        menu?.clear()
        activity.menuInflater.inflate(R.menu.menu_add_excavation, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {

        when(item?.itemId){
            R.id.action_save -> {
                if (addExcavation()){
                    dismiss()
                }
            }
        }

        return super.onOptionsItemSelected(item)
    }


    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val dialog = super.onCreateDialog(savedInstanceState)
        dialog.window.requestFeature(Window.FEATURE_NO_TITLE)
        return dialog
    }

    private fun addExcavation(): Boolean {
        val reference: DatabaseReference = FirebaseDatabase.getInstance().reference
        val excavationName: String = et_excavation_place.text.toString()
        val organisation: String = et_organisation.text.toString()

        return if (excavationName != "" && organisation != "") {
            val excavation = Excavation(excavationName, organisation)
            reference.child(FIREBASE_LOCATION_EXCAVATION_LISTS).push().setValue(excavation)
            true
        } else {
            Toast.makeText(activity, "Please fill in all the fields.", Toast.LENGTH_LONG).show()
            false
        }
    }

}