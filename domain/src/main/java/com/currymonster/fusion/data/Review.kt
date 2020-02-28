package com.currymonster.fusion.data

import java.io.Serializable

data class Review(
    val id: String,
    val url: String,
    val text: String,
    val rating: Int,
    val created: String,
    val user: User
) : Serializable