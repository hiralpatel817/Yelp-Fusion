package com.currymonster.fusion.dto

import com.currymonster.fusion.data.SearchResponse

data class SearchResponseDto(
    val businesses: List<BusinessDto>,
    val total: Int
) {

    fun toDomain() = SearchResponse(
        businesses = businesses.map { it.toDomain() },
        total = total
    )
}