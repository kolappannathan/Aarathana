package com.kolappan.aarathana.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.focusable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsFocusedAsState
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.kolappan.aarathana.R
import com.kolappan.aarathana.models.Song
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun SongLyricComponent(song: Song, navController: NavController, modifier: Modifier) {
    val stanzas = remember(song.lyrics) {
        song.lyrics.split("\n\n").filter { it.isNotBlank() }
    }
    
    val focusRequester = remember { FocusRequester() }
    val listState = rememberLazyListState()
    val scope = rememberCoroutineScope()

    LaunchedEffect(Unit) {
        // Delay slightly to ensure layout is ready before requesting focus
        delay(100)
        focusRequester.requestFocus()
    }

    LazyColumn(
        state = listState,
        modifier = modifier
            .fillMaxSize()
            .padding(start = 7.dp, bottom = 12.dp)
    ) {
        item {
            Column(modifier = Modifier.focusRequester(focusRequester)) {
                Spacer(modifier = Modifier.height(16.dp))
                if (song.author.isNotEmpty()) {
                    FocusableLink(
                        label = stringResource(R.string.song_lyric_page_author) + ": ",
                        value = song.author,
                        onClick = { navController.navigate("author/${song.author}") }
                    )
                }
                if (song.mainGod.isNotEmpty()) {
                    FocusableLink(
                        label = stringResource(R.string.song_lyric_page_god) + ": ",
                        value = song.mainGod,
                        onClick = { navController.navigate("god/${song.mainGod}") }
                    )
                }
                Spacer(modifier = Modifier.height(12.dp))
            }
        }
        
        items(stanzas) { stanza ->
            StanzaItem(stanza)
        }
    }
}

@Composable
fun StanzaItem(stanza: String) {
    val interactionSource = remember { MutableInteractionSource() }
    val isFocused by interactionSource.collectIsFocusedAsState()

    Text(
        text = stanza,
        modifier = Modifier
            .fillMaxWidth()
            .background(
                if (isFocused) MaterialTheme.colorScheme.surfaceVariant 
                else Color.Transparent,
                shape = MaterialTheme.shapes.small
            )
            .focusable(interactionSource = interactionSource)
            .padding(horizontal = 8.dp, vertical = 8.dp)
    )
}

@Composable
fun FocusableLink(label: String, value: String, onClick: () -> Unit) {
    val interactionSource = remember { MutableInteractionSource() }
    val isFocused by interactionSource.collectIsFocusedAsState()

    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .background(
                if (isFocused) MaterialTheme.colorScheme.primaryContainer else Color.Transparent,
                shape = MaterialTheme.shapes.small
            )
            .clickable(interactionSource = interactionSource, indication = null) { onClick() }
            .padding(horizontal = 4.dp, vertical = 2.dp)
    ) {
        Text(
            text = label,
            fontStyle = FontStyle.Italic
        )
        Text(
            text = value,
            fontStyle = FontStyle.Italic,
            color = if (isFocused) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.primary,
            textDecoration = if (isFocused) TextDecoration.None else TextDecoration.Underline
        )
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
