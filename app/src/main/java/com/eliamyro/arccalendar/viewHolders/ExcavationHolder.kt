package com.eliamyro.arccalendar.viewHolders

import android.support.v7.widget.RecyclerView
import android.view.View
import com.eliamyro.arccalendar.models.Excavation
import kotlinx.android.synthetic.main.item_row_excavation.view.*

/**
* Created by Elias Myronidis on 25/8/17.
* LinkedIn: https://www.linkedin.com/in/eliasmyronidis/
*/

class ExcavationHolder(itemView: View): RecyclerView.ViewHolder(itemView) {

    fun bindExcavationView(excavation: Excavation) = with(itemView){
        tv_excavation_place.text = excavation.place
        tv_organisation_value.text = excavation.organisation
        tv_description.text = excavation.description
    }
}