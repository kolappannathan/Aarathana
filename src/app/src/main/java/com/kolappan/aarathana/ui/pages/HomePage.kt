package com.kolappan.aarathana.ui.pages

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
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
fun HomePage(
    navController: NavController,
    songs: List<SongMetadata>,
    onMenuClick: (() -> Unit)?
) {
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            AarathanaTopBar(
                navController = navController,
                canNavigateBack = false,
                onMenuClick = onMenuClick,
                actions = {
                    IconButton(onClick = { navController.navigate("search") }) {
                        Icon(Icons.Default.Search, contentDescription = "Search")
                    }
                }
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
fun HomePagePreview(){
    val navController = rememberNavController()
    val mockSongs = listOf(
        SongMetadata("Song 1", "Author 1", "God 1", "song1.md"),
        SongMetadata("Song 2", "Author 2", "God 2", "song2.md"),
        SongMetadata("Song 3", "Author 3", "God 3", "song3.md")
    )
    HomePage(navController, mockSongs, onMenuClick = {})
}
