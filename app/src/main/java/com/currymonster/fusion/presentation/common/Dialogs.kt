package com.currymonster.fusion.presentation.common

import android.content.Context
import com.currymonster.fusion.R
import com.currymonster.fusion.common.DialogEvent

class Dialogs {

    companion object {

        fun getGenericNetworkDialog(
            context: Context,
            ctaPositive: () -> Unit = {}
        ): DialogEvent {
            return DialogEvent()
                .withTitle(context.getString(R.string.error_generic_title))
                .withMessage(context.getString(R.string.error_network_generic))
                .withButtonPostiveText(context.getString(R.string.error_generic_acknowledge))
                .withCallbacks(object : DialogEvent.Default() {
                    override fun onButtonPositive() {
                        super.onButtonPositive()
                        ctaPositive.invoke()
                    }
                })
        }

        fun getGenericNetworkRetryDialog(
            context: Context,
            title: String = "",
            description: String = "",
            ctaPositive: () -> Unit = {},
            ctaNeutral: () -> Unit = {}
        ): DialogEvent {
            return DialogEvent()
                .withTitle(if (title.isNotBlank()) title else context.getString(R.string.error_generic_title))
                .withMessage(if (description.isNotBlank()) description else context.getString(R.string.error_network_generic))
                .withButtonPostiveText(context.getString(R.string.error_generic_retry))
                .withButtonNeutralText(context.getString(R.string.error_generic_acknowledge))
                .withCallbacks(object : DialogEvent.Default() {
                    override fun onButtonPositive() {
                        super.onButtonPositive()
                        ctaPositive.invoke()
                    }

                    override fun onButtonNeutral() {
                        super.onButtonNeutral()
                        ctaNeutral.invoke()
                    }
                })
        }

        fun getVersionNotSupportedDialog(
            context: Context,
            ctaPositive: () -> Unit = {},
            ctaNeutral: () -> Unit = {}
        ): DialogEvent {
            return DialogEvent()
                .withTitle(context.getString(R.string.error_generic_title))
                .withMessage(context.getString(R.string.error_version_not_supported))
                .withButtonNeutralText(context.getString(R.string.error_version_not_supported_close))
                .withButtonPostiveText(context.getString(R.string.error_version_not_supported_update))
                .withCallbacks(object : DialogEvent.Default() {
                    override fun onButtonPositive() {
                        super.onButtonPositive()
                        ctaPositive.invoke()
                    }

                    override fun onButtonNeutral() {
                        super.onButtonNegative()
                        ctaNeutral.invoke()
                    }

                    override fun onDismiss() {
                        super.onDismiss()
                        ctaNeutral.invoke()
                    }
                })
        }
    }
}