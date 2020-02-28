package com.currymonster.fusion.presentation.base

import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.currymonster.fusion.common.LifeCycleAutoDisposer
import com.currymonster.fusion.common.ProgressState
import com.currymonster.fusion.presentation.base.viewmodelfactory.ViewModelFactory
import io.reactivex.disposables.Disposable
import javax.inject.Inject

abstract class BaseFragment<VM : BaseViewModel> : Fragment() {

    @Inject
    lateinit var factory: ViewModelFactory

    protected abstract val viewModel: VM

    private val baseActivity: BaseActivity?
        get() = activity as? BaseActivity

    private val autoDisposer =
        LifeCycleAutoDisposer()

    @Suppress("TooGenericExceptionThrown")
    override fun onAttach(context: Context) {
        super.onAttach(context)

        if (context !is BaseActivity) {
            throw RuntimeException("Trying to attach BaseFragment to non BaseActivity activity")
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        autoDisposer.attach(this)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        try {
            viewModel.navController = findNavController()
        } catch (ex: IllegalStateException) {
        } catch (ex: TypeCastException) {
        }

        super.onViewCreated(view, savedInstanceState)

        savedInstanceState?.let {
            restoreViewModelState(it)
        }

        viewModel.dialogEvent.observe(this, BaseObserver { dialogState ->
            baseActivity?.onDialogStateChanged(dialogState)
        })

        viewModel.snackbarEvent.observe(this, BaseObserver { message ->
            baseActivity?.showSnackbar(message)
        })

        viewModel.closeActivityEvent.observe(this, BaseObserver {
            baseActivity?.onCloseActivity()
        })
    }

    override fun onDestroy() {
        super.onDestroy()
        autoDisposer.detach(this)
    }

    override fun onSaveInstanceState(outState: Bundle) {
        saveViewModelState(outState)
        super.onSaveInstanceState(outState)
    }

    open fun restoreViewModelState(savedInstanceState: Bundle) {
    }

    open fun saveViewModelState(outState: Bundle) {
    }

    fun <T : Disposable> autoDispose(disposable: T): T = autoDisposer.autoDispose(disposable)

    fun onCloseActivity() {
        baseActivity?.onCloseActivity()
    }

    fun onProgressStateChanged(progressState: ProgressState) {
        baseActivity?.onProgressStateChanged(progressState)
    }
}