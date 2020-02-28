package com.currymonster.fusion.data

import java.io.Serializable

data class User(
    val id: String,
    val profileUrl: String,
    val imageUrl: String,
    val name: String
) : Serializable