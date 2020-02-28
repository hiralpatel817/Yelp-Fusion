package com.currymonster.fusion.common

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
import kotlinx.android.parcel.RawValue

@Parcelize
data class DialogEvent(
    private val title: String = "",
    private val message: String = "",
    private val verticalButtons: Boolean = true,
    private val neutralButtonText: String = "",
    private val negativeButtonText: String = "",
    private val positiveButtonText: String = "",
    private val callbacks: @RawValue DialogCallbacks = Default()
) : Parcelable {

    fun withTitle(title: String?, vararg args: Any): DialogEvent {
        return copy(title = title?.format(*args) ?: "")
    }

    fun withMessage(message: String?, vararg args: Any): DialogEvent {
        return copy(message = message?.format(*args) ?: "")
    }

    fun withVerticalButtons(verticalButtons: Boolean): DialogEvent {
        return copy(verticalButtons = verticalButtons)
    }

    fun withButtonNeutralText(text: String?, vararg args: Any): DialogEvent {
        return copy(neutralButtonText = text?.format(*args) ?: "")
    }

    fun withButtonNegativeText(text: String?, vararg args: Any): DialogEvent {
        return copy(negativeButtonText = text?.format(*args) ?: "")
    }

    fun withButtonPostiveText(text: String?, vararg args: Any): DialogEvent {
        return copy(positiveButtonText = text?.format(*args) ?: "")
    }

    fun withCallbacks(callbacks: DialogCallbacks): DialogEvent {
        return copy(callbacks = callbacks)
    }

    fun getTitle() = title

    fun getMessage() = message

    fun getVerticalButton() = verticalButtons

    fun getButtonNeutralText() = neutralButtonText

    fun getButtonNegativeText() = negativeButtonText

    fun getButtonPositiveText() = positiveButtonText

    fun getCallbacks() = callbacks


    interface DialogCallbacks {

        fun onDisplay()

        fun onCancel()

        fun onDismiss()

        fun onButtonNeutral()

        fun onButtonNegative()

        fun onButtonPositive()
    }

    open class Default : DialogCallbacks {

        override fun onDisplay() {
        }

        override fun onCancel() {
        }

        override fun onDismiss() {
        }

        override fun onButtonNeutral() {
        }

        override fun onButtonNegative() {
        }

        override fun onButtonPositive() {
        }
    }
}