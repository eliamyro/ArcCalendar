package com.eliamyro.arccalendar.activities

import android.os.Bundle
import com.eliamyro.arccalendar.R
import com.eliamyro.arccalendar.common.KEY_EXCAVATION_ITEM_ID
import com.eliamyro.arccalendar.common.KEY_WORK_ITEM_ID
import com.eliamyro.arccalendar.common.KEY_WORK_LOCATION_ITEM_ID
import com.eliamyro.arccalendar.fragments.FragmentFindingsList

/**
 * Created by Elias Myronidis on 28/8/17.
 * LinkedIn: https://www.linkedin.com/in/eliasmyronidis/
 */

class ActivityFindingsList : ActivityBase() {

    private val excavationItemId: String by lazy { intent.getStringExtra(KEY_EXCAVATION_ITEM_ID) }
    private val workItemId: String by lazy { intent.getStringExtra(KEY_WORK_ITEM_ID) }
    private val workLocationItemId: String by lazy { intent.getStringExtra(KEY_WORK_LOCATION_ITEM_ID) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_findings_list)

        configureToolbar(true)

        val fragment = supportFragmentManager.findFragmentById(R.id.container_finding_lists) as FragmentFindingsList
        val bundle = Bundle()
        bundle.putString(KEY_EXCAVATION_ITEM_ID, excavationItemId)
        bundle.putString(KEY_WORK_ITEM_ID, workItemId)
        bundle.putString(KEY_WORK_LOCATION_ITEM_ID, workLocationItemId)
        fragment.arguments = bundle
    }

    override fun finish() {
        super.finish()
        overridePendingTransition(R.anim.left_in, R.anim.right_out)
    }
}
