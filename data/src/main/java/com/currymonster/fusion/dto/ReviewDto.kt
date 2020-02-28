package com.currymonster.fusion.dto

import com.currymonster.fusion.data.Review

data class ReviewDto(
    val id: String,
    val url: String,
    val text: String,
    val rating: Int,
    val time_created: String,
    val user: UserDto
) {
    fun toDomain() = Review(
        id = id,
        url = url,
        text = text,
        rating = rating,
        created = time_created,
        user = user.toDomain()
    )
}