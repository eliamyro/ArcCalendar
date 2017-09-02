package com.eliamyro.arccalendar.presenters

import android.widget.Toast
import com.eliamyro.arccalendar.common.FIREBASE_LOCATION_EXCAVATION_LISTS
import com.eliamyro.arccalendar.contracts.ContractDialogAddExcavation
import com.eliamyro.arccalendar.contracts.ContractDialogAddExcavation.Views
import com.eliamyro.arccalendar.dialogs.DialogAddExcavation
import com.eliamyro.arccalendar.models.Excavation
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.dialog_add_excavation.*

/**
 * Created by Elias Myronidis on 28/8/17.
 */
class PresenterDialogAddExcavation(private val mView: Views) : ContractDialogAddExcavation.Actions{

    override fun addExcavation(): Boolean {
        val reference: DatabaseReference = FirebaseDatabase.getInstance().reference
        val excavationName: String = (mView as DialogAddExcavation).et_excavation_place.text.toString()
        val organisation: String = mView.et_organisation.text.toString()
        val description: String = mView.et_description.text.toString()

        return if (excavationName != "" && organisation != "" && description != "") {
            val excavation = Excavation(excavationName, organisation, description)
            reference.child(FIREBASE_LOCATION_EXCAVATION_LISTS).push().setValue(excavation)
            true
        } else {
            Toast.makeText(mView.context, "Please fill in all the fields.", Toast.LENGTH_LONG).show()
            false
        }
    }
}