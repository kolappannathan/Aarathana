package com.kolappan.aarathana.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.kolappan.aarathana.R
import com.kolappan.aarathana.models.Song

@Composable
fun SongLyricComponent(song: Song, navController: NavController, modifier: Modifier) {
    Box(
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(start = 7.dp, bottom = 12.dp)
    )
    {
        Column {
            Spacer(modifier = Modifier.height(16.dp))
            if (song.author.isNotEmpty()) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(
                        text = stringResource(R.string.song_lyric_page_author) + ": ",
                        fontStyle = FontStyle.Italic
                    )
                    Text(
                        text = song.author,
                        fontStyle = FontStyle.Italic,
                        color = MaterialTheme.colorScheme.primary,
                        textDecoration = TextDecoration.Underline,
                        modifier = Modifier.clickable {
                            navController.navigate("author/${song.author}")
                        }
                    )
                }
            }
            if (song.mainGod.isNotEmpty()) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(
                        text = stringResource(R.string.song_lyric_page_god) + ": ",
                        fontStyle = FontStyle.Italic
                    )
                    Text(
                        text = song.mainGod,
                        fontStyle = FontStyle.Italic,
                        color = MaterialTheme.colorScheme.primary,
                        textDecoration = TextDecoration.Underline,
                        modifier = Modifier.clickable {
                            navController.navigate("god/${song.mainGod}")
                        }
                    )
                }
            }
            Spacer(modifier = Modifier.height(12.dp))
            Text(text = song.lyrics)
        }
    }
}

@Preview(showBackground = true)
@Composable
fun SongLyricComponentPreview() {
    val mockSong = Song(
        title = "Preview Song",
        author = "Author Name",
        lyrics = "Line 1 of the lyrics\nLine 2 of the lyrics\nLine 3 of the lyrics\nMore lyrics here...",
        mainGod = "God Name"
    )
    SongLyricComponent(mockSong, rememberNavController(), Modifier.padding(5.dp))
}
