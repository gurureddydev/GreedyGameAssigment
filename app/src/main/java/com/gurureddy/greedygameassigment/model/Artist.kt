package com.gurureddy.greedygameassigment.model

data class Artist(
    val mbid: String,
    val name: String,
    val url: String,
    val image: List<Image>
)