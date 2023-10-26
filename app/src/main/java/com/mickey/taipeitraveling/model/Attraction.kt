package com.mickey.taipeitraveling.model

data class Attraction(
    val id: String,
    val name: String,
    val introduction: String,
    val address: String,
    val images: List<ImageInfo>,
    val modified: String,
    val url: String,
)

data class ImageInfo(
    val src: String,
    val subject: String,
    val ext: String
)