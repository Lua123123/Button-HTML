@file:Suppress("unused")

package com.example.demo.extensions

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.view.Gravity
import android.view.View
import android.widget.TextView
import android.widget.Toast
import com.example.demo.R

/**
 * Launch activity
 */
fun <T> Context.launchActivity(it: Class<T>) {
    val intent = Intent(this, it)
    startActivity(intent)
}

/**
 * Toast message in Activity
 * @param durationMs time in millisecond or [Toast.LENGTH_SHORT] [Toast.LENGTH_LONG] in case want to hide toast sooner than default
 */
fun Context.toast(message: String, durationMs: Int = Toast.LENGTH_SHORT) {
    val toast = Toast.makeText(this, message, durationMs)
    val toastView = toast.view
    val toastMessage = toastView!!.findViewById<View>(android.R.id.message) as TextView
    toastMessage.textSize = 13f
    toastMessage.setTextColor(Color.YELLOW)
    toastMessage.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0)
    toastMessage.gravity = Gravity.CENTER
    toastMessage.compoundDrawablePadding = 4
    toastView.setBackgroundColor(Color.BLACK)
    toastView.setBackgroundResource(R.drawable.bg_toast)
    toast.show()
}