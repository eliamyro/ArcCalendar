package com.eliamyro.arccalendar.listeners

/**
* Created by Elias Myronidis on 2/9/17.
* LinkedIn: https://www.linkedin.com/in/eliasmyronidis/
*/

interface ClickCallback {

    fun onItemSelected(excavationId: String = "", workId: String = "",
                       workLocationId: String = "")
}