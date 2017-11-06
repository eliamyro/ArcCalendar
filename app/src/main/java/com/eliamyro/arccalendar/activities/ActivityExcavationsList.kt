package com.eliamyro.arccalendar.activities

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import com.eliamyro.arccalendar.R
import com.eliamyro.arccalendar.common.KEY_EXCAVATION_ITEM_ID
import com.eliamyro.arccalendar.listeners.ClickCallback
import com.google.android.gms.auth.api.Auth
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.GoogleApiClient
import com.google.android.gms.common.api.ResultCallback
import com.google.android.gms.common.api.Status
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser

/**
 * Created by Elias Myronidis on 28/8/17.
 * LinkedIn: https://www.linkedin.com/in/eliasmyronidis/
 */

class ActivityExcavationsList : ActivityBase(), ClickCallback {

    companion object {
        private val TAG: String = ActivityExcavationsList::class.java.simpleName
    }

    private var mAuth: FirebaseAuth? = null
    private var mAuthListener: FirebaseAuth.AuthStateListener? = null
    private var mUser: FirebaseUser? = null
    private var mGoogleApiClient: GoogleApiClient? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_excavations_list)

        configureToolbar()

        mAuth = FirebaseAuth.getInstance()

        mAuthListener = FirebaseAuth.AuthStateListener {
            mUser = mAuth?.currentUser

            if (mUser == null) {
                Log.d(TAG, "onAuthStateChanged: Signed in" + mUser?.uid)

                startActivity(Intent(this@ActivityExcavationsList, ActivityAuthentication::class.java))
                finish()

            } else {
                Log.d(TAG, "onAuthStateChanged: signed out")
            }
        }
    }

    override fun onStart() {
        super.onStart()
        mAuth?.addAuthStateListener(mAuthListener!!)

    }

    override fun onStop() {
        super.onStop()

        if (mAuthListener != null) {
            mAuth?.removeAuthStateListener(mAuthListener!!)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater?.inflate(R.menu.menu_excavations_list, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {

        when(item?.itemId){
            R.id.action_log_out -> { logOut() }
        }

        return super.onOptionsItemSelected(item)
    }

    override fun onItemSelected(excavationId: String, workId: String, workLocationId: String) {
        val mIntent = Intent(this, ActivityWorksList::class.java)
        mIntent.putExtra(KEY_EXCAVATION_ITEM_ID, excavationId)
        startActivity(mIntent)
        overridePendingTransition(R.anim.right_in, R.anim.left_out)
    }

    private fun logOut() {
        mAuth?.signOut()

        val gso: GoogleSignInOptions = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build()

       val  mGoogleApiClient = GoogleApiClient.Builder(applicationContext)
                .enableAutoManage(this) {}.addApi(Auth.GOOGLE_SIGN_IN_API, gso).build()

        if(mGoogleApiClient.isConnected){
            Auth.GoogleSignInApi.revokeAccess(mGoogleApiClient)
        }


    }
}
