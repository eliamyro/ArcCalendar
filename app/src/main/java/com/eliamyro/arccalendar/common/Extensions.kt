package com.eliamyro.arccalendar.common

import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentTransaction
import com.eliamyro.arccalendar.R

/**
 * Created by Elias Myronidis on 29/8/17.
 */

inline fun FragmentManager.inTransaction(func: FragmentTransaction.() -> Unit) {
    val transaction = beginTransaction()
    transaction.setTransition(android.support.v4.app.FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
    transaction.setCustomAnimations(R.anim.right_in, R.anim.left_out, R.anim.left_in, R.anim.right_out)
    transaction.func()
    transaction.addToBackStack(null)
    transaction.commit()
}