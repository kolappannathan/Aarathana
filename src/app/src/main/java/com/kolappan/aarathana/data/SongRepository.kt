package com.kolappan.aarathana.data

import android.content.Context
import com.kolappan.aarathana.models.Song
import com.kolappan.aarathana.models.SongMetadata
import kotlinx.serialization.json.Json

class SongRepository(private val context: Context) {
    private var cachedMetadata: List<SongMetadata>? = null
    
    private val json = Json {
        ignoreUnknownKeys = true
    }

    fun getSongsMetadata(): List<SongMetadata> {
        return cachedMetadata ?: loadIndex().also { cachedMetadata = it }
    }

    private fun loadIndex(): List<SongMetadata> {
        return try {
            val jsonString = context.assets.open("songs_index.json").bufferedReader().use { it.readText() }
            json.decodeFromString<List<SongMetadata>>(jsonString)
        } catch (e: Exception) {
            emptyList()
        }
    }

    fun getSongWithLyrics(metadata: SongMetadata): Song? {
        return try {
            val content = context.assets.open("songs/${metadata.fileName}").bufferedReader().use { it.readText() }
            val parts = content.split("---", limit = 2)
            val lyrics = if (parts.size >= 2) parts[1].trim() else ""
            
            Song(
                title = metadata.title,
                author = metadata.author,
                lyrics = lyrics,
                mainGod = metadata.mainGod
            )
        } catch (e: Exception) {
            null
        }
    }
}
