package com.eliamyro.arccalendar.activities

import android.content.Intent
import android.os.Bundle
import com.eliamyro.arccalendar.R
import com.eliamyro.arccalendar.common.KEY_EXCAVATION_ITEM_ID
import com.eliamyro.arccalendar.listeners.ClickCallback

class ActivityExcavationsList : ActivityBase(), ClickCallback {

    companion object {
        private val TAG: String = ActivityExcavationsList::class.java.simpleName
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_excavations_list)

        configureToolbar()
    }

    override fun onItemSelected(excavationId: String, workId: String) {
        val mIntent = Intent(this, ActivityWorksList::class.java)
        mIntent.putExtra(KEY_EXCAVATION_ITEM_ID, excavationId)
        startActivity(mIntent)
        overridePendingTransition(R.anim.right_in, R.anim.left_out)
    }
}
