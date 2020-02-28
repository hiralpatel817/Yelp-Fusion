package com.currymonster.fusion.dto

import com.currymonster.fusion.data.ReviewResponse

data class ReviewResponseDto(
    val reviews: List<ReviewDto>,
    val total: Int,
    val possible_languages: List<String>
) {
    fun toDomain() = ReviewResponse(
        reviews = reviews.map { it.toDomain() },
        total = total
    )
}