package com.currymonster.fusion.data

import java.io.Serializable

data class Location(
    val address: String,
    val city: String,
    val state: String,
    val zip: String,
    val displayAddress: String
) : Serializable