package com.currymonster.fusion.dto

import com.currymonster.fusion.data.Category

data class CategoryDto(
    val alias: String,
    val title: String
) {

    fun toDomain() = Category(
        alias = alias,
        title = title
    )
}