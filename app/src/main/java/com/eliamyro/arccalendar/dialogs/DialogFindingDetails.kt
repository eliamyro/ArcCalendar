package com.eliamyro.arccalendar.dialogs

import android.os.Bundle
import android.support.v4.app.DialogFragment
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.eliamyro.arccalendar.R
import kotlinx.android.synthetic.main.dialog_finding_details.*

/**
 * Created by Elias Myronidis on 3/10/17.
 */
class DialogFindingDetails: DialogFragment() {

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater?.inflate(R.layout.dialog_finding_details, container, false)
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        val toolbar = toolbar as Toolbar

        toolbar.title = "Finding title"

        toolbar.let {
            (activity as AppCompatActivity).setSupportActionBar(it)

            val actionBar = (activity as AppCompatActivity).supportActionBar
            actionBar?.setDisplayHomeAsUpEnabled(true)
            actionBar?.setHomeAsUpIndicator(R.drawable.ic_close_white_24dp)
        }

        setHasOptionsMenu(true)
    }
}