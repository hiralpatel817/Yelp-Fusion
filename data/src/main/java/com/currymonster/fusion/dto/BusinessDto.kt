package com.currymonster.fusion.dto

import com.currymonster.fusion.data.Business

data class BusinessDto(
    val id: String,
    val alias: String,
    val name: String,
    val image_url: String,
    val is_closed: Boolean,
    val url: String,
    val review_count: Int,
    val categories: List<CategoryDto>,
    val rating: Double,
    val coordinates: CoordinatesDto,
    val transactions: List<String>,
    val location: LocationDto,
    val phone: String,
    val display_phone: String,
    val distance: Double
) {

    fun toDomain() = Business(
        id = id,
        alias = alias,
        name = name,
        imageUrl = image_url,
        isClosed = is_closed,
        url = url,
        reviewCount = review_count,
        categories = categories.map { it.toDomain() },
        rating = rating,
        coordinates = coordinates.toDomain(),
        transactions = transactions,
        location = location.toDomain(),
        phone = phone,
        displayPhone = display_phone,
        distance = distance
    )
}