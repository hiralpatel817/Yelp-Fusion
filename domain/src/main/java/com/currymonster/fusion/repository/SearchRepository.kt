package com.currymonster.fusion.repository

import com.currymonster.fusion.data.SearchResponse
import io.reactivex.Single

interface SearchRepository {

    fun searchBusinesses(
        latitude: String,
        longitude: String,
        offset: Int,
        limit: Int
    ): Single<SearchResponse>
}
