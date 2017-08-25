package com.eliamyro.arccalendar.viewHolders

import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.Toast
import com.eliamyro.arccalendar.models.Excavation
import kotlinx.android.synthetic.main.item_row_excavation.view.*

/**
 * Created by Elias Myronidis on 25/8/17.
 */
class ExcavationHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
    init {
        itemView.setOnClickListener({ Toast.makeText(itemView.context, "" + adapterPosition, Toast.LENGTH_LONG).show()})
    }

    fun bindExcavationView(excavation: Excavation){
        itemView.tv_excavation_place.text = excavation.place
        itemView.tv_organisation_value.text = excavation.organization
    }

}