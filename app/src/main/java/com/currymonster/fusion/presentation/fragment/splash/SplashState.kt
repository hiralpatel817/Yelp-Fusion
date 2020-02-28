package com.currymonster.fusion.presentation.fragment.splash

import android.os.Parcelable
import com.currymonster.fusion.common.ProgressState
import com.currymonster.fusion.common.Reducer
import kotlinx.android.parcel.Parcelize

sealed class Event {
}

sealed class Action {
    data class OnShowProgressBar(val title: String, val message: String) : Action()
    object OnClearProgressBar : Action()
    data class OnVersionSupported(val supported: Boolean) : Action()
    data class SetDevMode(val devMode: Boolean) : Action()
    data class SetAvailableEnvs(val envs: List<String>) : Action()
    data class OnAppDetails(
        val product: String,
        val env: String,
        val buildType: String,
        val version: String
    ) : Action()
}

object ProgressStateReducer :
    Reducer<ProgressState> {
    override fun reduce(state: ProgressState, action: Any): ProgressState {
        return when (action) {
            is Action.OnShowProgressBar ->
                state.new().withTitle(action.title).withMessage(action.message)
            is Action.OnClearProgressBar ->
                state.clear()
            else ->
                state
        }
    }
}

@Parcelize
data class SplashState(
    val progressState: ProgressState = ProgressState(),
    val product: String = "",
    val env: String = "",
    val buildType: String = "",
    val version: String = "",
    val versionSupported: Boolean? = null,
    val devMode: Boolean = true
) : Parcelable {
    fun next(action: Action): SplashState {
        return copy(
            progressState = ProgressStateReducer.reduce(progressState, action)
        ).let {
            when (action) {
                is Action.OnAppDetails ->
                    copy(
                        product = action.product,
                        env = action.env,
                        buildType = action.buildType,
                        version = action.version
                    )
                is Action.OnVersionSupported ->
                    copy(versionSupported = action.supported)
                is Action.SetDevMode ->
                    copy(devMode = action.devMode)
                else ->
                    it
            }
        }
    }
}