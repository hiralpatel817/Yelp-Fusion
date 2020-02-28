package com.currymonster.fusion.repository

import io.reactivex.Single

class VersionRepositoryImpl() : VersionRepository {

    override fun isVersionSupported(appVersion: String, codeVersion: Int): Single<Boolean> {
        return Single.just(true)
    }
}
