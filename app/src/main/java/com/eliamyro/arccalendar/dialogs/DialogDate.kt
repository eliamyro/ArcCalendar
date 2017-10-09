package com.eliamyro.arccalendar.dialogs

import android.app.DatePickerDialog
import android.app.Dialog
import android.os.Bundle
import android.support.v4.app.DialogFragment
import android.util.Log
import android.widget.DatePicker
import java.sql.Timestamp
import java.text.SimpleDateFormat
import java.util.*

/**
 * Created by Elias Myronidis on 9/10/17.
 */
class DialogDate: DialogFragment(), DatePickerDialog.OnDateSetListener{

    companion object {
        private val TAG: String = DialogDate::class.java.simpleName
    }

    private var callback: DateSetListener? = null

    interface DateSetListener {
        fun setDate(date: Timestamp)
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
        val day: Int = calendar.get(Calendar.DAY_OF_WEEK)

        return DatePickerDialog(activity, this, year, month, day)
    }
}