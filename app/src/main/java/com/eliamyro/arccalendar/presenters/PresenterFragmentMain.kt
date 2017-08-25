package com.eliamyro.arccalendar.presenters

import android.content.Context
import com.eliamyro.arccalendar.contracts.ContractFragmentMain.Actions
import com.eliamyro.arccalendar.contracts.ContractFragmentMain.Views
import com.eliamyro.arccalendar.dialogs.DialogAddExcavation
import com.eliamyro.arccalendar.fragments.FragmentExcavationsList

/**
 * Created by Elias Myronidis on 23/8/17.
 */
class PresenterFragmentMain(private val mView: Views): Actions {

    companion object {
        private val TAG: String = PresenterFragmentMain::class.java.simpleName
        private val DIALOG_ADD_EXCAVATION: String = "dialog_add_excavation"
    }

    override fun addExcavation(context: Context) {
        val dialog = DialogAddExcavation.instance()
        dialog?.show((mView as FragmentExcavationsList).fragmentManager, DIALOG_ADD_EXCAVATION)
    }
}