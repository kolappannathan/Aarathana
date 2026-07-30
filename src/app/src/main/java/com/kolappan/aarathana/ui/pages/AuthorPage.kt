package com.kolappan.aarathana.ui.pages

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.kolappan.aarathana.models.SongMetadata
import com.kolappan.aarathana.ui.components.AarathanaTopBar
import com.kolappan.aarathana.ui.components.SongListContent

@Composable
fun AuthorPage(
    navController: NavController,
    authorName: String,
    songs: List<SongMetadata>
) {
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            AarathanaTopBar(
                navController = navController,
                canNavigateBack = true,
                title = authorName
            )
        }) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            Spacer(modifier = Modifier.height(16.dp))
            SongListContent(songs, navController, modifier = Modifier.fillMaxSize())
        }
    }
}

@Composable
@Preview(showBackground = true)
fun AuthorPagePreview() {
    val navController = rememberNavController()
    val mockSongs = listOf(
        SongMetadata("Song 1", "Author 1", "God 1", "song1.md"),
        SongMetadata("Song 2", "Author 1", "God 2", "song2.md")
    )
    AuthorPage(navController, "Author 1", mockSongs)
}
