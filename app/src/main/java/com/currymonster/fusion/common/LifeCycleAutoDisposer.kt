package com.currymonster.fusion.common

import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.OnLifecycleEvent
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import java.util.*

/**
 * A Class that encapsulated CompositeDisposables that are disposed of when the current lifecycle
 * state "drops down" one level
 *
 * To use, attach an instance of this class to a LifeCycleOwner. This results in the LifeCycle
 * in question being observed. When the observer gets notified about an ON_CREATE, ON_START or
 * ON_RESUME event, a new CompositeDisposable is being pushed on a stack. When the corresponding
 * ON_PAUSE, ON_STOP or ON_DESTROY is received, the top CompositeDisposable is popped and cleared.
 *
 */
class LifeCycleAutoDisposer {

    private val stack = Stack<CompositeDisposable>()

    private val observer = object : LifecycleObserver {

        @OnLifecycleEvent(Lifecycle.Event.ON_ANY)
        fun onLifeCycleEvent(lifecycleOwner: LifecycleOwner, event: Lifecycle.Event) {
            when (event) {
                Lifecycle.Event.ON_CREATE, Lifecycle.Event.ON_START, Lifecycle.Event.ON_RESUME ->
                    stack.push(CompositeDisposable())
                Lifecycle.Event.ON_PAUSE, Lifecycle.Event.ON_STOP, Lifecycle.Event.ON_DESTROY ->
                    stack.pop().clear()
                else -> {
                }
            }
        }
    }

    private var isAttached = false

    /**
     * Attach this LifeCycleAutoDisposer to a LifeCycleOwner. The owned lifecycle wil be observed.
     */
    fun attach(lifecycleOwner: LifecycleOwner) {
        if (isAttached) {
            throw IllegalStateException("Already attached to lifecycle owner")
        }
        isAttached = true
        // We need a pool before we actually get the ON_CREATE event
        stack.push(CompositeDisposable())
        lifecycleOwner.lifecycle.addObserver(observer)
    }

    /**
     * Detach from the LifeCycleOwner and stop observing the LifeCycle
     */
    fun detach(lifecycleOwner: LifecycleOwner) {
        if (!isAttached) {
            throw IllegalStateException("Not attached to lifecycle owner")
        }
        isAttached = false
        lifecycleOwner.lifecycle.removeObserver(observer)
        // Empty the stack
        stack.pop().clear()
    }

    /**
     * Add a Disposable to the CompositeDisposable at the top of the stack.
     */
    fun <T : Disposable> autoDispose(disposable: T): T {
        stack.peek().add(disposable)
        return disposable
    }
}
