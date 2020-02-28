package com.currymonster.fusion.common

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class ProgressState(
    val title: String = "",
    val message: String = "",
    val overlayAlpha: Float = 0.54f,
    val isVisible: Boolean = false
) : Parcelable {

    fun new(): ProgressState {
        return copy(isVisible = true, title = "", message = "")
    }

    fun clear(): ProgressState {
        return copy(isVisible = false, title = "", message = "")
    }

    fun withTitle(title: String?, vararg args: Any): ProgressState {
        return copy(title = title?.format(*args) ?: "")
    }

    fun withMessage(message: String, vararg args: Any): ProgressState {
        return copy(message = message.format(*args))
    }
}