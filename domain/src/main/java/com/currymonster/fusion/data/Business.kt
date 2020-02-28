package com.currymonster.fusion.data

import java.io.Serializable

data class Business(
    val id: String,
    val alias: String,
    val name: String,
    val imageUrl: String,
    val isClosed: Boolean,
    val url: String,
    val reviewCount: Int,
    val categories: List<Category>,
    val rating: Double,
    val coordinates: Coordinates,
    val transactions: List<String>,
    val location: Location,
    val phone: String,
    val displayPhone: String,
    val distance: Double
) : Serializable