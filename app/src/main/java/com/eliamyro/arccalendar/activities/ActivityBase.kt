package com.eliamyro.arccalendar.activities

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.Toolbar
import android.util.Log
import android.view.MenuItem
import android.view.View
import com.eliamyro.arccalendar.R

abstract class ActivityBase : AppCompatActivity() {

    private val TAG: String = ActivityBase::class.java.simpleName

    var view: View? = null

    override fun setContentView(layoutResId: Int) {
        view = layoutInflater.inflate(layoutResId, null)
        super.setContentView(view)
    }

    fun configureToolbar(toolbarHasBack: Boolean = false) {

        val toolbar: Toolbar? = view?.findViewById(R.id.toolbar)

        toolbar?.let {
            setSupportActionBar(it)
            if (toolbarHasBack){
                supportActionBar?.setDisplayHomeAsUpEnabled(true)
            }
        }
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when(item?.itemId){
            android.R.id.home -> onBackPressed()
            else -> Log.d(TAG, "Default case.")
        }

        return super.onOptionsItemSelected(item)
    }

}
