package com.currymonster.fusion.usecase

import com.currymonster.fusion.base.SingleUseCase
import com.currymonster.fusion.data.SearchResponse
import com.currymonster.fusion.repository.SearchRepository
import io.reactivex.Single
import javax.inject.Inject

class SearchUseCase @Inject constructor(
    private val searchRepository: SearchRepository
) : SingleUseCase<SearchUseCase.Data, SearchResponse>() {

    override fun createSingle(data: Data): Single<SearchResponse> =
        searchRepository.searchBusinesses(data.latitude, data.longitude, data.offset, data.limit)

    data class Data(
        val longitude: String,
        val latitude: String,
        val offset: Int,
        val limit: Int
    )
}
