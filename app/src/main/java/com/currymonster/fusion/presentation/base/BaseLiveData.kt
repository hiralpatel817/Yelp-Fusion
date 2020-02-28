package com.currymonster.fusion.presentation.base

import androidx.lifecycle.LiveData

open class BaseLiveData<T : Any>(initialValue: T) : LiveData<T>() {

    init {
        super.setValue(initialValue)
    }

    override fun getValue(): T {
        return super.getValue()!!
    }

    override fun setValue(value: T) {
        if (this.value != value) {
            super.setValue(value)
        }
    }

    protected open fun update(f: (T) -> T) {
        value = f(value)
    }

    protected open fun post(f: (T) -> T) {
        postValue(f(value))
    }
}
