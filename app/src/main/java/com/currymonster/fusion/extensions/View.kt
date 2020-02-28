package com.currymonster.fusion.extensions

import android.content.Context
import android.view.View
import android.view.inputmethod.InputMethodManager

fun View.hideKeyboard() {
    val imm = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    imm.hideSoftInputFromWindow(windowToken, 0)
}

fun View.setVisible(visible: Boolean, notVisibleValue: Int = View.GONE) {
    visibility = if (visible) {
        View.VISIBLE
    } else {
        notVisibleValue
    }
}

fun View.setThrottledOnClickListener(listener: View.OnClickListener) {
    var lastClickTime = 0L
    setOnClickListener { view ->
        if (System.currentTimeMillis() > lastClickTime + 1000) {
            listener.onClick(view)
            lastClickTime = System.currentTimeMillis()
        }
    }
}

fun View.setThrottledOnClickListener(listener: () -> Unit) =
    setThrottledOnClickListener(View.OnClickListener { listener.invoke() })