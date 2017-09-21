package com.eliamyro.arccalendar.activities

import android.content.Intent
import android.os.Bundle
import android.util.Log
import com.eliamyro.arccalendar.R
import com.eliamyro.arccalendar.common.KEY_EXCAVATION_ITEM_ID
import com.eliamyro.arccalendar.common.KEY_WORK_ITEM_ID
import com.eliamyro.arccalendar.fragments.FragmentWorksList
import com.eliamyro.arccalendar.listeners.ClickCallback

class ActivityWorksList : ActivityBase(), ClickCallback {

    private val mExcavationId: String by lazy { intent.getStringExtra(KEY_EXCAVATION_ITEM_ID) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_works_list)

        configureToolbar(true)

        val fragment = supportFragmentManager.findFragmentById(R.id.container_fragment_works_list) as FragmentWorksList
        val bundle = Bundle()
        bundle.putString(KEY_EXCAVATION_ITEM_ID, mExcavationId)
        fragment.arguments = bundle
    }

    override fun finish() {
        super.finish()
        overridePendingTransition(R.anim.left_in, R.anim.right_out)
    }

    override fun onItemSelected(excavationId: String, workId: String) {
        Log.d("TAG", "ExcavationId: $mExcavationId, WorkId: $workId")

        val mIntent = Intent(this, ActivityWorkDetails::class.java)
        mIntent.putExtra(KEY_EXCAVATION_ITEM_ID, mExcavationId)
        mIntent.putExtra(KEY_WORK_ITEM_ID, workId)
        startActivity(mIntent)
        overridePendingTransition(R.anim.right_in, R.anim.left_out)
    }
}
