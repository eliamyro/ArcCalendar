package com.eliamyro.arccalendar.common

import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentTransaction
import android.support.v7.widget.Toolbar
import com.eliamyro.arccalendar.R
import java.sql.Timestamp
import java.text.SimpleDateFormat
import java.util.*

/**
 * Created by Elias Myronidis on 29/8/17.
 * LinkedIn: https://www.linkedin.com/in/eliasmyronidis/
 */

inline fun FragmentManager.inTransaction(addToBackStack: Boolean, func: FragmentTransaction.() -> Unit) {
    val transaction = beginTransaction()
    transaction.setTransition(android.support.v4.app.FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
    transaction.setCustomAnimations(R.anim.right_in, R.anim.left_out, R.anim.left_in, R.anim.right_out)
    transaction.func()
    if (addToBackStack) {
        transaction.addToBackStack(null)
    }
    transaction.commit()
}

//fun Long.toStringDate(): String {
//    try {
//        val sdf = SimpleDateFormat("dd/MM/yyyy")
//        val netDate = Date(this)
//        return sdf.format(netDate)
//    } catch (ex: Exception) {
//        return ""
//    }
//}

//fun Context.toast(message: String, length: Int = Toast.LENGTH_SHORT) {
//    Toast.makeText(this, message, length).show()
//}

//fun Fragment.toast(message: String, length: Int = Toast.LENGTH_SHORT) {
//    this.context.toast(message, length)
//}

fun getDate(date: String): String {
    var timestamp: Timestamp? = null
    try {
        val dateFormat = SimpleDateFormat("yyyy-MM-dd hh:mm:ss.SSS", Locale.getDefault())
        val parsedDate = dateFormat.parse(date)
        timestamp = Timestamp(parsedDate.time)
    } catch (e: Exception) { //this generic but you can control another types of exception
        // look the origin of excption
    }


    val sFormat = SimpleDateFormat("dd MMMM yyy", Locale.getDefault())
    return sFormat.format(timestamp)

}

fun Toolbar.setDateToToolbar(date: String) {
    this.title = getDate(date)
}

fun String.formatDate(): String {
    var timestamp: Timestamp? = null
    try {
        val dateFormat = SimpleDateFormat("yyyy-MM-dd hh:mm:ss.SSS", Locale.getDefault())
        val parsedDate = dateFormat.parse(this)
        timestamp = Timestamp(parsedDate.time)
    } catch (e: Exception) { //this generic but you can control another types of exception
        // look the origin of excption
    }


    val sFormat = SimpleDateFormat("dd MMMM yyy", Locale.getDefault())
    return sFormat.format(timestamp)
}

fun String.toTimestamp(): String {
    var timestamp: Timestamp? = null
    try {
        val dateFormat = SimpleDateFormat("dd MMMM yyy", Locale.getDefault())
        val parsedDate = dateFormat.parse(this)
        timestamp = Timestamp(parsedDate.time)
    } catch (e: Exception) { //this generic but you can control another types of exception
        // look the origin of excption
    }

    return timestamp.toString()
}