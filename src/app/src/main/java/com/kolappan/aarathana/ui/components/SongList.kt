package com.kolappan.aarathana.ui.components

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.kolappan.aarathana.models.SongMetadata
import kotlinx.coroutines.delay

@Composable
fun SongListContent(
    songs: List<SongMetadata>,
    navController: NavController,
    modifier: Modifier = Modifier
) {
    val focusRequester = remember { FocusRequester() }

    LaunchedEffect(Unit) {
        delay(100)
        focusRequester.requestFocus()
    }

    LazyColumn(
        modifier = modifier
            .padding(start = 8.dp, end = 8.dp)
            .focusRequester(focusRequester)
    ) {
        items(songs) { song ->
            SongCard(song, navController)
        }
    }
}

@Composable
@Preview(showBackground = true)
fun SongListPreview() {
    val navController = rememberNavController()
    val mockSongs = listOf(
        SongMetadata("Song 1", "Author 1", "God 1", "song1.md"),
        SongMetadata("Song 2", "Author 2", "God 2", "song2.md"),
        SongMetadata("Song 3", "Author 3", "God 3", "song3.md")
    )
    SongListContent(mockSongs, navController)
}
