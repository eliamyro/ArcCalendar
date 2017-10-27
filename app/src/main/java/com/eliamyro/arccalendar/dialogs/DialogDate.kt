package com.eliamyro.arccalendar.dialogs

import android.app.DatePickerDialog
import android.app.Dialog
import android.os.Bundle
import android.support.v4.app.DialogFragment
import android.widget.DatePicker
import java.sql.Timestamp
import java.util.*

/**
* Created by Elias Myronidis on 9/10/17.
* LinkedIn: https://www.linkedin.com/in/eliasmyronidis/
*/

class DialogDate: DialogFragment(), DatePickerDialog.OnDateSetListener{

    private var callback: DateSetListener? = null

    interface DateSetListener {
        fun setDate(timestamp: Timestamp)
    }

    override fun onDateSet(view: DatePicker?, year: Int, month: Int, day: Int) {
        val calendar = Calendar.getInstance()
        calendar.set(year, month, day)
        val timestamp = Timestamp(calendar.timeInMillis)
        callback?.setDate(timestamp)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        try {
            callback = parentFragment  as DateSetListener
        } catch (e: ClassCastException) {
            throw ClassCastException("Calling fragment must implement Callback interface")
        }

    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val calendar = Calendar.getInstance()
        val year: Int = calendar.get(Calendar.YEAR)
        val month: Int = calendar.get(Calendar.MONTH)
        val day: Int = calendar.get(Calendar.DAY_OF_MONTH)

        return DatePickerDialog(activity, this, year, month, day)
    }
}