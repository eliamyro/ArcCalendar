package com.eliamyro.arccalendar.listeners


/**
 * Created by Elias Myronidis on 2/9/17.
 */
interface ClickCallback {

    fun onItemSelected(excavationId: String = "", workId: String = "", workLocationId: String = "")
}