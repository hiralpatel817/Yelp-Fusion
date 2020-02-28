package com.currymonster.fusion.presentation.fragment.splash

import android.content.Context
import android.os.Handler
import androidx.lifecycle.Observer
import androidx.lifecycle.Transformations
import com.currymonster.fusion.BuildConfig
import com.currymonster.fusion.R
import com.currymonster.fusion.env.BuildEnv
import com.currymonster.fusion.env.envs.Environment
import com.currymonster.fusion.extensions.distinct
import com.currymonster.fusion.presentation.base.BaseMutableLiveData
import com.currymonster.fusion.presentation.base.BaseViewModel
import com.currymonster.fusion.presentation.common.Dialogs
import com.currymonster.fusion.transformer.Async
import com.currymonster.fusion.usecase.OpenAppStoreUseCase
import com.currymonster.fusion.usecase.VersionSupportUseCase
import io.reactivex.rxkotlin.subscribeBy
import javax.inject.Inject


class SplashViewModel @Inject constructor(
    val context: Context,
    private val versionSupportUseCase: VersionSupportUseCase,
    private val openAppStoreUseCase: OpenAppStoreUseCase,
    val environment: Environment
) : BaseViewModel() {

    private val _state = BaseMutableLiveData(SplashState())

    val progressState = Transformations.map(_state) { state -> state.progressState }.distinct()
    val productState = Transformations.map(_state) { state -> state.product }.distinct()
    val versionState = Transformations.map(_state) { state -> state.version }.distinct()
    val envState = Transformations.map(_state) { state -> state.env }.distinct()
    val buildTypeState = Transformations.map(_state) { state -> state.buildType }.distinct()
    private val versionSupportedState =
        Transformations.map(_state) { state -> state.versionSupported }.distinct()

    init {
        observeForever(versionSupportedState, Observer {
            it?.let {
                if (!it) {
                    postVersionNotSupportedDialog()
                } else {
                    goToNext()
                }
            }
        })

        setAppDetails()

        checkVersionSupported()
    }

    private fun goToNext() {
        Handler().postDelayed({
            navController.navigate(SplashFragmentDirections.actionToHome())
            closeActivityEvent.postValue(true)
        }, 3000)
    }

    private fun checkVersionSupported() {
        versionSupportUseCase.execute(
            VersionSupportUseCase.Data(
                BuildConfig.VERSION_NAME,
                BuildConfig.VERSION_CODE
            ), Async
        ).subscribeBy(
            onSuccess = {
                _state.update { s -> s.next(Action.OnVersionSupported(it)) }
            },
            onError = {
                postNetworkError()
            }
        ).also {
            autoDispose(it)
        }
    }

    private fun setAppDetails() {
        _state.update { s ->
            s.next(
                Action.OnAppDetails(
                    context.getString(R.string.app_name),
                    environment.displayName,
                    BuildConfig.BUILD_TYPE,
                    BuildConfig.VERSION_NAME
                )
            )
        }

        _state.update { s ->
            s.next(
                Action.SetDevMode(
                    BuildConfig.BUILD_TYPE == BuildEnv.debug.toString() || BuildConfig.BUILD_TYPE == BuildEnv.qa.toString()
                )
            )
        }
    }

    private fun postVersionNotSupportedDialog() {
        dialogEvent.value = Dialogs.getVersionNotSupportedDialog(
            context = context,
            ctaPositive = {
                openAppStoreUseCase.execute(
                    OpenAppStoreUseCase.Data(
                        context,
                        BuildConfig.APPLICATION_ID
                    ), Async
                )
                closeActivityEvent.value = true
            },
            ctaNeutral = {
                closeActivityEvent.value = true
            }
        )
    }

    private fun postNetworkError() {
        dialogEvent.value =
            Dialogs.getGenericNetworkRetryDialog(
                context = context,
                ctaPositive = {
                    checkVersionSupported()
                },
                ctaNeutral = {
                    closeActivityEvent.value = true
                }
            )
    }
}