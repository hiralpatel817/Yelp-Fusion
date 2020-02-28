package com.currymonster.fusion.repository

import com.currymonster.fusion.data.SearchResponse
import com.currymonster.fusion.net.SearchService
import io.reactivex.Single

class SearchRepositoryImpl(
    private val service: SearchService
) : SearchRepository {

    override fun searchBusinesses(
        latitude: String,
        longitude: String,
        offset: Int,
        limit: Int
    ): Single<SearchResponse> =
        service.fetchBusinesses(
            offset = offset,
            limit = limit,
            latitude = latitude,
            longitude = longitude
        ).map {
            it.toDomain()
        }
}
