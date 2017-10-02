package com.eliamyro.arccalendar.activities

import android.content.Intent
import android.support.design.widget.TabLayout
import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import android.support.v4.view.ViewPager
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup

import com.eliamyro.arccalendar.R
import com.eliamyro.arccalendar.common.*
import com.eliamyro.arccalendar.fragments.FragmentWorkDetailPlaceholder
import com.eliamyro.arccalendar.listeners.ClickCallback
import com.eliamyro.arccalendar.models.WorkLocation
import com.fasterxml.jackson.databind.ObjectMapper
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.activity_work_tabs.*
import kotlinx.android.synthetic.main.fragment_activity_work_tabs.view.*

class ActivityWorkDetails : ActivityBase(), ClickCallback {

    companion object {
        private val TAG: String = ActivityWorkDetails::class.java.simpleName
    }

    /**
     * The [android.support.v4.view.PagerAdapter] that will provide
     * fragments for each of the sections. We use a
     * {@link FragmentPagerAdapter} derivative, which will keep every
     * loaded fragment in memory. If this becomes too memory intensive, it
     * may be best to switch to a
     * [android.support.v4.app.FragmentStatePagerAdapter].
     */
    private var mSectionsPagerAdapter: SectionsPagerAdapter? = null

    private val excavationItemId: String by lazy { intent.getStringExtra(KEY_EXCAVATION_ITEM_ID)}
    private val workItemId: String by lazy { intent.getStringExtra(KEY_WORK_ITEM_ID) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_work_tabs)

        configureToolbar(true)
//        setSupportActionBar(toolbar)
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
            // TODO: Implement delete work
            return true
        }

        return super.onOptionsItemSelected(item)
    }

    override fun onItemSelected(excavationId: String, workId: String, workLocationId: String) {
        Log.d(TAG, "excId: $excavationId, workId: $workId, WorkLocId: $workLocationId" )

        val intent = Intent(this, ActivityFindingsList::class.java)
        intent.putExtra(KEY_EXCAVATION_ITEM_ID, excavationId)
        intent.putExtra(KEY_WORK_ITEM_ID, workItemId)
        intent.putExtra(KEY_WORK_LOCATION_ITEM_ID, workLocationId)
        startActivity(intent)
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
            return FragmentWorkDetailPlaceholder.newInstance(position + 1, excavationItemId, workItemId)
        }

        override fun getCount(): Int {
            // Show 2 total pages.
            return 2
        }

        override fun getPageTitle(position: Int): CharSequence {
            when (position) {
                0 -> return getString(R.string.info)
                1 -> return getString(R.string.locations)
                else -> return ""
            }
        }
    }

}
