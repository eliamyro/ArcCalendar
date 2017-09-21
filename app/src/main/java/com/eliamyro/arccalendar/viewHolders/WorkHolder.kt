package com.eliamyro.arccalendar.viewHolders

import android.support.v7.widget.RecyclerView
import android.view.View
import com.eliamyro.arccalendar.models.Work
import kotlinx.android.synthetic.main.item_row_work.view.*

/**
 * Created by Elias Myronidis on 5/9/17.
 */
class WorkHolder(itemView: View): RecyclerView.ViewHolder(itemView) {

    fun bindWorkView(work: Work){
        itemView.tv_work_description.text = work.description
    }
}