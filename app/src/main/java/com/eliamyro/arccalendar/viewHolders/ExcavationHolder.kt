package com.eliamyro.arccalendar.viewHolders

import android.support.v7.widget.RecyclerView
import android.view.View
import com.eliamyro.arccalendar.models.Excavation
import kotlinx.android.synthetic.main.item_row_excavation.view.*

/**
 * Created by Elias Myronidis on 25/8/17.
 */
class ExcavationHolder(itemView: View): RecyclerView.ViewHolder(itemView) {

    fun bindExcavationView(excavation: Excavation){
        itemView.tv_excavation_place.text = excavation.place
        itemView.tv_organisation_value.text = excavation.organisation
        itemView.tv_description.text = excavation.description
    }

}