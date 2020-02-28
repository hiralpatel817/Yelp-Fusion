package com.currymonster.fusion.dto

import com.currymonster.fusion.data.Coordinates

data class CoordinatesDto(
    val latitude: Double,
    val longitude: Double
) {

    fun toDomain() = Coordinates(
        latitude = latitude,
        longitude = longitude
    )
}