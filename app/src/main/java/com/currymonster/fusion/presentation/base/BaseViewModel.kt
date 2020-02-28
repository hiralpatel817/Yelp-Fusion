package com.currymonster.fusion.presentation.base

import android.os.Bundle
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.navigation.NavController
import com.currymonster.fusion.common.DialogEvent
import com.currymonster.fusion.common.SingleLiveEvent
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable

open class BaseViewModel : ViewModel() {

    private val disposables = CompositeDisposable()

    lateinit var navController: NavController

    var snackbarEvent = SingleLiveEvent<String>()
    var dialogEvent = SingleLiveEvent<DialogEvent>()
    var closeActivityEvent = SingleLiveEvent<Boolean>()

    override fun onCleared() {
        super.onCleared()
        disposables.clear()
    }

    /**
     * Observables can be registered with the view model as a disposable to be disposed of
     * when this view model is cleared.  By so doing one is effectively tying the lifespan
     * of the Observable/Disposable to the lifecycle of this view model.
     *
     * @param disposable the disposable to dispose of when onCleared is called
     */
    fun <T : Disposable> autoDispose(disposable: T): T {
        disposables.add(disposable)
        return disposable
    }

    /**
     * Clear the disposables. This allows subclasses some control if they need to be able
     * to clear disposables on a different lifecycle than the ViewModel.
     */
    fun clearDisposables() {
        disposables.clear()
    }

    /**
     * Register the specified Observer to observe the specified LiveData instance until this
     * ViewModel is cleared.
     *
     * @param liveData the LiveData instance to observe
     * @param observer the LiveData observer
     */
    fun <T, O : Observer<T>> observeForever(liveData: LiveData<T>, observer: O): O {
        liveData.observeForever(observer)
        autoDispose(ObserverDisposableWrapper(liveData, observer))
        return observer
    }

    /**
     * Override to save state, see [StatefulBaseViewModel]
     */
    open fun saveState(bundle: Bundle) {
    }

    /**
     * Override to restore state, see [StatefulBaseViewModel]
     */
    open fun restoreState(bundle: Bundle) {
    }
}

/**
 * Wrapper to combine the LiveData with the Observer so that the Observer can be removed when this
 * wrapper is disposed as part of onCleared in the ViewModel.
 */
data class ObserverDisposableWrapper<T>(val liveData: LiveData<T>, val observer: Observer<T>) :
    Disposable {

    private var disposed: Boolean = false

    override fun dispose() {
        disposed = true
        liveData.removeObserver(observer)
    }

    override fun isDisposed(): Boolean {
        return disposed
    }
}
