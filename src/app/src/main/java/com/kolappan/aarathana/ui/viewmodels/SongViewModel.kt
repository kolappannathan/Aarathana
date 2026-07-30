package com.kolappan.aarathana.ui.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.kolappan.aarathana.data.SongRepository
import com.kolappan.aarathana.models.Song
import com.kolappan.aarathana.models.SongMetadata
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class SongViewModel(application: Application) : AndroidViewModel(application) {
    private val repository = SongRepository(application)

    private val _songsState = MutableStateFlow<List<SongMetadata>>(emptyList())
    val songsState: StateFlow<List<SongMetadata>> = _songsState.asStateFlow()

    init {
        loadSongs()
    }

    private fun loadSongs() {
        _songsState.value = repository.getSongsMetadata()
    }

    fun getSongByTitle(title: String): Song? {
        val metadata = _songsState.value.find { it.title == title } ?: return null
        return repository.getSongWithLyrics(metadata)
    }

    fun getSongsByAuthor(author: String): List<SongMetadata> {
        return _songsState.value.filter { it.author == author }
    }

    fun searchSongs(query: String): List<SongMetadata> {
        val songs = _songsState.value
        if (query.isBlank()) return songs
        
        return songs.filter { 
            it.title.contains(query, ignoreCase = true) || 
            it.author.contains(query, ignoreCase = true)
        }
    }
}
