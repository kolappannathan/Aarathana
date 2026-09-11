package com.kolappan.aarathana.ui.components

import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsFocusedAsState
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.kolappan.aarathana.models.SongMetadata

@Composable
fun SongCard(song: SongMetadata, navController: NavController, modifier: Modifier = Modifier) {
    val interactionSource = remember { MutableInteractionSource() }
    val isFocused by interactionSource.collectIsFocusedAsState()
    
    Card(
        onClick = { navController.navigate("lyrics/${song.title}") },
        interactionSource = interactionSource,
        colors = CardDefaults.cardColors(
            containerColor = if (isFocused) MaterialTheme.colorScheme.secondaryContainer else MaterialTheme.colorScheme.surfaceVariant,
            contentColor = if (isFocused) MaterialTheme.colorScheme.onSecondaryContainer else MaterialTheme.colorScheme.onSurfaceVariant
        ),
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 1.dp, vertical = 3.dp)
    ) {
        Column(modifier = Modifier.padding(all = 8.dp)) {
            Text(
                text = song.title,
                modifier = Modifier.padding(bottom = 3.dp)
            )
            if (song.author.isNotEmpty()) {
                Text(
                    text = song.author,
                    fontSize = 10.sp
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun SongCardPreview() {
    val mockSong = SongMetadata(
        title = "Sample Song Title",
        author = "Sample Author",
        mainGod = "God",
        fileName = "sample.md"
    )
    SongCard(song = mockSong, navController = rememberNavController())
}
