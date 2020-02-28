package com.currymonster.fusion.data

data class SearchResponse(
    val businesses: List<Business>,
    val total: Int
)