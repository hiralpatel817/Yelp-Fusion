package com.currymonster.fusion.dto

import com.currymonster.fusion.data.Error

data class ErrorDto(
    val code: String,
    val description: String
) {

    fun toDomain() = Error(
        description = description
    )
}