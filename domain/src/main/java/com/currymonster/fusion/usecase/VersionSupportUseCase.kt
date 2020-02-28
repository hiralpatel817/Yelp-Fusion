package com.currymonster.fusion.usecase

import com.currymonster.fusion.base.SingleUseCase
import com.currymonster.fusion.repository.VersionRepository
import io.reactivex.Single
import javax.inject.Inject

class VersionSupportUseCase @Inject constructor(
    private val versionRepository: VersionRepository
) : SingleUseCase<VersionSupportUseCase.Data, Boolean>() {

    override fun createSingle(data: Data): Single<Boolean> =
        versionRepository.isVersionSupported(data.appVersion, data.codeVersion)

    data class Data(
        val appVersion: String,
        val codeVersion: Int
    )
}