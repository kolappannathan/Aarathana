package com.kolappan.aarathana.data

import android.content.Context
import com.kolappan.aarathana.models.Song

class SongRepository(private val context: Context) {
    private var cachedSongs: List<Song>? = null

    fun getSongs(): List<Song> {
        return cachedSongs ?: loadSongs().also { cachedSongs = it }
    }

    private fun loadSongs(): List<Song> {
        val songs = mutableListOf<Song>()
        val assetsManager = context.assets
        val songFiles = assetsManager.list("songs") ?: return emptyList()

        for (fileName in songFiles) {
            if (fileName.endsWith(".md")) {
                val content = assetsManager.open("songs/$fileName").bufferedReader().use { it.readText() }
                parseMarkdownSong(content)?.let { songs.add(it) }
            }
        }
        return songs
    }

    private fun parseMarkdownSong(content: String): Song? {
        val parts = content.split("---", limit = 2)
        if (parts.size < 2) return null

        val header = parts[0]
        val lyrics = parts[1].trim()

        var title = ""
        var author = ""
        var mainGod = ""

        header.lines().forEach { line ->
            when {
                line.startsWith("title:") -> title = line.removePrefix("title:").trim()
                line.startsWith("author:") -> author = line.removePrefix("author:").trim()
                line.startsWith("mainGod:") -> mainGod = line.removePrefix("mainGod:").trim()
            }
        }

        return if (title.isNotEmpty()) {
            Song(title, author, lyrics, mainGod)
        } else {
            null
        }
    }
}
