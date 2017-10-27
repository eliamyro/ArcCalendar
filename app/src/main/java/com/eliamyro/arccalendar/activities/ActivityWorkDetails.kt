package com.eliamyro.arccalendar.activities

import android.content.Intent
import android.support.design.widget.TabLayout
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import com.eliamyro.arccalendar.R
import com.eliamyro.arccalendar.common.*
import com.eliamyro.arccalendar.contracts.ContractDialogDeleteWork
import com.eliamyro.arccalendar.dialogs.DialogDeleteWork
import com.eliamyro.arccalendar.fragments.FragmentWorkDetailPlaceholder
import com.eliamyro.arccalendar.listeners.ClickCallback
import kotlinx.android.synthetic.main.activity_work_tabs.*

/**
 * Created by Elias Myronidis on 28/8/17.
 * LinkedIn: https://www.linkedin.com/in/eliasmyronidis/
 */

class ActivityWorkDetails : ActivityBase(), ClickCallback, ContractDialogDeleteWork.Views {

    companion object {
        private val TAG: String = ActivityWorkDetails::class.java.simpleName
    }

    private var mSectionsPagerAdapter: SectionsPagerAdapter? = null
    private val mExcavationItemId: String by lazy { intent.getStringExtra(KEY_EXCAVATION_ITEM_ID)}
    private val mWorkItemId: String by lazy { intent.getStringExtra(KEY_WORK_ITEM_ID) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_work_tabs)

        configureToolbar(true)

        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        mSectionsPagerAdapter = SectionsPagerAdapter(supportFragmentManager)

        // Set up the ViewPager with the sections adapter.
        view_pager.adapter = mSectionsPagerAdapter

        tabs.setupWithViewPager(view_pager)

        view_pager.addOnPageChangeListener(TabLayout.TabLayoutOnPageChangeListener(tabs))
        tabs.addOnTabSelectedListener(TabLayout.ViewPagerOnTabSelectedListener(view_pager))

    }


    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_work_tabs, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        val id = item?.itemId

        if (id == R.id.action_delete_work) {
            showDeleteWorkDialog()
            return true
        }

        return super.onOptionsItemSelected(item)
    }

    override fun onItemSelected(excavationId: String, workId: String, workLocationId: String) {
        Log.d(TAG, "excId: $excavationId, workId: $workId, WorkLocId: $workLocationId" )

            val intent = Intent(this, ActivityFindingsList::class.java)
            intent.putExtra(KEY_EXCAVATION_ITEM_ID, mExcavationItemId)
            intent.putExtra(KEY_WORK_ITEM_ID, mWorkItemId)
            intent.putExtra(KEY_WORK_LOCATION_ITEM_ID, workLocationId)
            startActivity(intent)
            overridePendingTransition(R.anim.right_in, R.anim.left_out)
    }

    override fun finish() {
        super.finish()

        overridePendingTransition(R.anim.left_in, R.anim.right_out)
    }


    /**
     * A [FragmentPagerAdapter] that returns a fragment corresponding to
     * one of the sections/tabs/pages.
     */
    inner class SectionsPagerAdapter(fm: FragmentManager) : FragmentPagerAdapter(fm) {

        override fun getItem(position: Int): Fragment? {
            // getItem is called to instantiate the fragment for the given page.
            // Return a PlaceholderFragment (defined as a static inner class below).
            return FragmentWorkDetailPlaceholder.newInstance(position + 1, mExcavationItemId, mWorkItemId)
        }

        override fun getCount(): Int {
            // Show 2 total pages.
            return 2
        }

        override fun getPageTitle(position: Int): CharSequence {
            when (position) {
                0 -> return getString(R.string.info)
                1 -> return getString(R.string.locations)
            }

            return ""
        }
    }

    private fun showDeleteWorkDialog(){

        val dialog = DialogDeleteWork()
        val bundle = Bundle()
        bundle.putString(KEY_EXCAVATION_ITEM_ID, mExcavationItemId)
        bundle.putString(KEY_WORK_ITEM_ID, mWorkItemId)
        dialog.arguments = bundle

        dialog.show(supportFragmentManager, DELETE_WORK_DIALOG)
    }
}
