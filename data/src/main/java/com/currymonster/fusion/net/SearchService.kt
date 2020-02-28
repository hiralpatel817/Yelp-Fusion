package com.currymonster.fusion.net

import com.currymonster.fusion.dto.SearchResponseDto
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query

interface SearchService {

    @GET("businesses/search")
    fun fetchBusinesses(
        @Query("offset") offset: Int,
        @Query("limit") limit: Int,
        @Query("latitude") latitude: String,
        @Query("longitude") longitude: String
    ): Single<SearchResponseDto>
}