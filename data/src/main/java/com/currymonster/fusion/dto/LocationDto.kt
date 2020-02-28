package com.currymonster.fusion.dto

import com.currymonster.fusion.data.Location

data class LocationDto(
    val address1: String,
    val address2: String,
    val address3: String,
    val city: String,
    val zip_code: String,
    val country: String,
    val state: String,
    val display_address: List<String>
) {
    fun toDomain() = Location(
        address = address1,
        city = city,
        state = state,
        zip = zip_code,
        displayAddress = display_address.joinToString(separator = " ")
    )
}