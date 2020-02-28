package com.currymonster.fusion.data

data class ReviewResponse(
    val reviews: List<Review>,
    val total: Int
)