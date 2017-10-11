package com.eliamyro.arccalendar.viewHolders
import android.support.v7.widget.RecyclerView
import android.view.View
import com.eliamyro.arccalendar.models.WorkLocation
import kotlinx.android.synthetic.main.item_row_work_location.view.*

/**
 * Created by Elias Myronidis on 26/9/17.
 */
class WorkLocationHolder(itemView: View): RecyclerView.ViewHolder(itemView) {

    fun bindWorkLocation(workLocation: WorkLocation){
        with(itemView){
            tv_work_location_title.text = workLocation.location
            tv_work_location_description.text = workLocation.description
        }
    }
}