package com.eliamyro.arccalendar.activities

import android.app.ProgressDialog
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.eliamyro.arccalendar.R
import com.google.android.gms.auth.api.Auth
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.auth.api.signin.GoogleSignInStatusCodes
import com.google.android.gms.common.api.GoogleApiClient
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.GoogleAuthProvider
import kotlinx.android.synthetic.main.activity_authentication.*

class ActivityAuthentication : AppCompatActivity() {

    companion object {
        private val RC_SIGN_IN: Int = 1
        private val TAG: String = ActivityAuthentication::class.java.simpleName
    }

    private var mGoogleApiClient: GoogleApiClient? = null
    private var mGoogleAccount: GoogleSignInAccount? = null
    private var mAuthProgressDialog: ProgressDialog? = null
    private var mAuth: FirebaseAuth? = null
    private var mAuthListener: FirebaseAuth.AuthStateListener? = null
    private var mUser: FirebaseUser? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_authentication)

        initializeScreen()

        mAuth = FirebaseAuth.getInstance()

        mAuthListener = FirebaseAuth.AuthStateListener {
            mUser = mAuth?.getCurrentUser()

            if (mUser != null) {
                Log.d(TAG, "onAuthStateChanged: Signed in" + mUser?.getUid())

                val mIntent = Intent(this@ActivityAuthentication, ActivityExcavationsList::class.java)
                mIntent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                startActivity(mIntent)
                finish()

            } else {
                Log.d(TAG, "onAuthStateChanged: signed out")
            }
        }

        // Configure Google Sign In
        val gso: GoogleSignInOptions = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build()

        mGoogleApiClient = GoogleApiClient.Builder(applicationContext)
                .enableAutoManage(this) {
                    Toast.makeText(this@ActivityAuthentication, "There is a connection error.", Toast.LENGTH_SHORT).show()
                }.addApi(Auth.GOOGLE_SIGN_IN_API, gso).build()

        btnSignIn.setOnClickListener { signIn() }
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

    private fun signIn() {
        val signInIntent: Intent = Auth.GoogleSignInApi.getSignInIntent(mGoogleApiClient)
        startActivityForResult(signInIntent, RC_SIGN_IN)
        mAuthProgressDialog?.show()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == RC_SIGN_IN) {
            val result = Auth.GoogleSignInApi.getSignInResultFromIntent(data)
            if (result.isSuccess) {
                mGoogleAccount = result.signInAccount
                firebaseAuthWithGoogle(mGoogleAccount)
                mAuthProgressDialog?.dismiss()
            } else {
                if (result.status.statusCode == GoogleSignInStatusCodes.SIGN_IN_CANCELLED) {
                    showErrorToast("The sign in was cancelled. Make sure you're connected to the internet and try again.")
                } else {
                    showErrorToast("Error handling the sign in: " + result.status.statusMessage!!)
                }
                mAuthProgressDialog?.dismiss()
            }
        }

    }

    private fun initializeScreen(){
        /* Setup the progress dialog that is displayed later when authenticating with Firebase */
        mAuthProgressDialog = ProgressDialog(this)
        mAuthProgressDialog?.let {
            it.setTitle("Loading...")
            it.setMessage("Authenticating with Firebase…")
            it.setCancelable(false)
        }
    }

    private fun firebaseAuthWithGoogle(acct: GoogleSignInAccount?) {
        Log.d(TAG, "firebaseAuthWithGoogle:" + acct?.id!!)

        val credential = GoogleAuthProvider.getCredential(acct?.idToken, null)
        mAuth?.signInWithCredential(credential)?.addOnCompleteListener(this, OnCompleteListener<AuthResult> { task ->
                    if (task.isSuccessful) {
                        mAuthProgressDialog?.dismiss()
                        // Sign in success, update UI with the signed-in user's information
                        Log.d(TAG, "signInWithCredential:success")
                        val currentUser = mAuth?.getCurrentUser()
                        val user = currentUser

                        val mIntent = Intent(this@ActivityAuthentication, ActivityExcavationsList::class.java)
                        mIntent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                        startActivity(mIntent)
                        finish()
                    } else {
                        mAuthProgressDialog?.dismiss()
                        // If sign in fails, display a message to the user.
                        Log.w(TAG, "signInWithCredential:failure", task.exception)
                        Toast.makeText(this@ActivityAuthentication, "Authentication failed.",
                                Toast.LENGTH_SHORT).show()
                    }
                })
    }

    private fun showErrorToast(message: String) {
        Toast.makeText(this@ActivityAuthentication, message, Toast.LENGTH_LONG).show()
    }
}
