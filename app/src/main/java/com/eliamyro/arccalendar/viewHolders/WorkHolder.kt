package com.eliamyro.arccalendar.viewHolders

import android.support.v7.widget.RecyclerView
import android.view.View
import com.eliamyro.arccalendar.models.Work
import kotlinx.android.synthetic.main.item_row_work.view.*
import java.text.SimpleDateFormat
import java.util.*
import java.sql.Timestamp


/**
 * Created by Elias Myronidis on 5/9/17.
 */
class WorkHolder(itemView: View): RecyclerView.ViewHolder(itemView) {

    fun bindWorkView(work: Work) = with(itemView){
        var timestamp: Timestamp? = null
        try {
            val dateFormat = SimpleDateFormat("yyyy-MM-dd hh:mm:ss.SSS")
            val parsedDate = dateFormat.parse(work.workDate)
            timestamp = Timestamp(parsedDate.time)
        } catch (e: Exception) { //this generic but you can control another types of exception
            // look the origin of excption
        }


        val sFormat = SimpleDateFormat("dd MMMM yyy", Locale.getDefault())
        val date: String = sFormat.format(timestamp)
        tv_work_date.text = date
        tv_work_description.text = work.description
    }
}