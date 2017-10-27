package com.eliamyro.arccalendar.activities

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.view.View
import com.eliamyro.arccalendar.R

/**
 * Created by Elias Myronidis on 28/8/17.
 * LinkedIn: https://www.linkedin.com/in/eliasmyronidis/
 */

class ActivitySplash : ActivityBase() {

    companion object {
        // Time to pass before start the next activity.
        private val SPLASH_DISPLAY_LENGTH: Long = 3000
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        makeFullScreen()
        showNextActivity()
    }

    /* Make the activity full screen. */
    private fun makeFullScreen(){

        // Note that some of these constants are new as of API 16 (Jelly Bean)
        // and API 19 (KitKat). It is safe to use them, as they are inlined
        // at compile-time and do nothing on earlier devices.

        Handler().post({
            window.decorView.systemUiVisibility =
                    View.SYSTEM_UI_FLAG_LOW_PROFILE or
                            View.SYSTEM_UI_FLAG_FULLSCREEN or
                            View.SYSTEM_UI_FLAG_LAYOUT_STABLE or
                            View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION or
                            View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
        })
    }

    /* Show the ActivityExcavationsList after some seconds. */
    private fun showNextActivity(){
        Handler().postDelayed(
                {
                    val mIntent = Intent(this, ActivityExcavationsList::class.java)
                    startActivity(mIntent)
                    finish()
                }, SPLASH_DISPLAY_LENGTH
        )
    }
}
