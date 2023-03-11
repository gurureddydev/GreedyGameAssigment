package com.gurureddy.greedygameassigment.model



data class Album(
    val artist: Artist,
    val image: List<Image>,
    val mbid: String,
    val name: String,
    val url: String,
    )
