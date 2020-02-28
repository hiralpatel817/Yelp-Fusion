package com.currymonster.fusion.dto

import com.currymonster.fusion.data.User

data class UserDto(
    val id: String,
    val profile_url: String,
    val image_url: String,
    val name: String
) {
    fun toDomain() = User(
        id = id,
        profileUrl = profile_url,
        imageUrl = image_url,
        name = name
    )
}