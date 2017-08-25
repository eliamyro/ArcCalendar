package com.eliamyro.arccalendar.dialogs

import android.app.Dialog
import android.os.Bundle
import android.support.v4.app.DialogFragment
import android.support.v7.app.AlertDialog
import android.view.WindowManager
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
        private var mDialog: DialogAddExcavation? = null

        fun instance(): DialogAddExcavation? {

            if(mDialog == null) {
                mDialog = DialogAddExcavation()
            }

            mDialog?.isCancelable = false
            return mDialog
        }
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val builder = AlertDialog.Builder(activity)
        val inflater = activity.layoutInflater

        val view = inflater.inflate(R.layout.dialog_add_excavation, null)
        builder.setView(view)

        builder.setPositiveButton(getString(R.string.create), { _, _ -> })
        builder.setNegativeButton(getString(R.string.cancel), { dialog, _ -> dialog.dismiss() })

        return builder.create()
    }

    override fun onStart() {
        super.onStart()

        val dialog = dialog as AlertDialog

        val btnPositive = dialog.getButton(Dialog.BUTTON_POSITIVE)
        btnPositive.setOnClickListener({
            if (addExcavation()) {
                dialog.dismiss()
            }
        })
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        // Show the Soft keyboard
        dialog.window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE)
    }

    private fun addExcavation(): Boolean {
        val reference: DatabaseReference = FirebaseDatabase.getInstance().reference
        val excavationName: String = (dialog as AlertDialog).et_excavation_place.text.toString()
        val organisation: String = (dialog as AlertDialog).et_organisation.text.toString()

        return if (excavationName != "" && organisation != "") {
            val excavation = Excavation(excavationName, organisation)
            reference.child(FIREBASE_LOCATION_EXCAVATION_LISTS).push().setValue(excavation)
            Toast.makeText(activity, "Saved succesfully.", Toast.LENGTH_LONG).show()
            true
        } else {
            Toast.makeText(activity, "Please fill in all the fields.", Toast.LENGTH_LONG).show()
            false
        }
    }
}