package com.currymonster.fusion.usecase

import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.net.Uri
import com.currymonster.fusion.base.CompletableUseCase
import io.reactivex.Completable
import javax.inject.Inject

class OpenAppStoreUseCase @Inject constructor(
) : CompletableUseCase<OpenAppStoreUseCase.Data>() {

    override fun createCompletable(data: Data): Completable {
        try {
            data.context.startActivity(
                Intent(
                    Intent.ACTION_VIEW,
                    Uri.parse("market://details?id=${data.packageName}")
                ).also {
                    it.flags = Intent.FLAG_ACTIVITY_NEW_TASK
                }
            )
        } catch (anfe: ActivityNotFoundException) {
            data.context.startActivity(
                Intent(
                    Intent.ACTION_VIEW,
                    Uri.parse("https://play.google.com/store/apps/details?id=${data.packageName}")
                ).also {
                    it.flags = Intent.FLAG_ACTIVITY_NEW_TASK
                }
            )
        }

        return Completable.complete()
    }

    data class Data(
        val context: Context,
        val packageName: String
    )
}