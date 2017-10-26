package com.eliamyro.arccalendar.viewHolders

import android.support.v7.widget.RecyclerView
import android.view.View
import com.eliamyro.arccalendar.models.Finding
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.item_row_finding.view.*

/**
 * Created by Elias Myronidis on 2/10/17.
 */
class FindingHolder(itemView: View): RecyclerView.ViewHolder(itemView) {

    fun bindFindingView(finding: Finding){
        with(itemView){
            tv_finding_name.text = finding.name
            tv_finding_description.text = finding.description
            if(finding.imageUrl != ""){
                Picasso.with(itemView.context)
                        .load(finding.imageUrl)
                        .into(iv_finding_image)
            }
        }
    }
}