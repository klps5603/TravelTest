package com.bonge.traveltest.web

data class TravelResponse(
    val id: Int,
    val name: String,
    val introduction: String,
    val images: List<Images>,
    val officialSite: String
)

data class Images(
    val src: String,
    val ext: String
)
