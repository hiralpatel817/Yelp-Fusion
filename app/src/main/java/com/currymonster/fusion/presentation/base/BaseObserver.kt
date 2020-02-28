package com.currymonster.fusion.presentation.base

import androidx.lifecycle.Observer

class BaseObserver<T : Any>(private val f: (T) -> Unit) : Observer<T> {

    override fun onChanged(t: T?) {
        f(t!!)
    }
}