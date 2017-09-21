package com.eliamyro.arccalendar.fragments

import android.os.Bundle
import android.support.v4.app.Fragment
import com.eliamyro.arccalendar.common.KEY_EXCAVATION_ITEM_ID
import com.eliamyro.arccalendar.common.KEY_WORK_ITEM_ID

/**
 * Created by Elias Myronidis on 16/9/17.
 */
class FragmentWorkDetailPlaceholder : Fragment() {

    companion object {

        /**
         * Returns a new instance of this fragment for the given section
         * number.
         */
        fun newInstance(sectionNumber: Int, excavationItemId: String, workItemId: String): Fragment? {
            var fragment: Fragment? = null

            when (sectionNumber) {
                1 -> {
                    fragment = FragmentWorkInfo()
                    val bundle = Bundle()
                    bundle.putString(KEY_EXCAVATION_ITEM_ID, excavationItemId)
                    bundle.putString(KEY_WORK_ITEM_ID, workItemId)
                    fragment.arguments = bundle
                }
                2 -> fragment = FragmentWorkLocations()
            }
            
            return fragment
        }
    }
}