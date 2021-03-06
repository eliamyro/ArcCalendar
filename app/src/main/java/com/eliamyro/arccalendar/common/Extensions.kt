package com.eliamyro.arccalendar.common

import android.content.Context
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentTransaction
import android.view.View
import android.widget.Toast
import com.eliamyro.arccalendar.R
import java.text.SimpleDateFormat
import java.util.*

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

fun Long.toStringDate():String{
    try {
        val sdf = SimpleDateFormat("dd/MM/yyyy")
        val netDate = Date(this)
        return sdf.format(netDate)
    } catch (ex: Exception) {
        return ""
    }
}

fun Context.toast(message: String, length: Int = Toast.LENGTH_SHORT){
    Toast.makeText(this, message, length).show()
}

fun Fragment.toast(message: String, length: Int = Toast.LENGTH_SHORT){
    this.context.toast(message, length)
}