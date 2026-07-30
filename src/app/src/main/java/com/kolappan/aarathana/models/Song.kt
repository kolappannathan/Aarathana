package com.kolappan.aarathana.models

import kotlinx.serialization.Serializable

@Serializable
data class SongMetadata(
    val title: String,
    val author: String,
    val mainGod: String,
    val fileName: String
)

data class Song(
    val title: String,
    val author: String,
    val lyrics: String,
    val mainGod: String
)
