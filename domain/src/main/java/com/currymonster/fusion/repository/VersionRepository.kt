package com.currymonster.fusion.repository

import io.reactivex.Single

interface VersionRepository {

    fun isVersionSupported(appVersion: String, codeVersion: Int): Single<Boolean>
}
