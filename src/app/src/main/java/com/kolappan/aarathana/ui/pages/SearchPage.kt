package com.kolappan.aarathana.ui.pages

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.kolappan.aarathana.R
import com.kolappan.aarathana.models.SongMetadata
import com.kolappan.aarathana.ui.components.AarathanaTopBar
import com.kolappan.aarathana.ui.components.SongListContent
import kotlinx.coroutines.delay

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchPage(
    navController: NavController,
    onSearch: (String) -> List<SongMetadata>
) {
    var searchQuery by remember { mutableStateOf("") }
    val filteredSongs = remember(searchQuery) { onSearch(searchQuery) }
    val focusManager = LocalFocusManager.current
    val focusRequester = remember { FocusRequester() }

    LaunchedEffect(Unit) {
        delay(300) // Small delay to ensure page is loaded
        focusRequester.requestFocus()
    }

    Scaffold(
        topBar = {
            AarathanaTopBar(
                navController = navController,
                canNavigateBack = true,
                title = stringResource(R.string.search_page_title)
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            OutlinedTextField(
                value = searchQuery,
                onValueChange = { searchQuery = it },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
                    .focusRequester(focusRequester),
                placeholder = { Text(stringResource(R.string.search_page_search_hint)) },
                leadingIcon = { Icon(Icons.Default.Search, contentDescription = null) },
                trailingIcon = {
                    if (searchQuery.isNotEmpty()) {
                        IconButton(onClick = { searchQuery = "" }) {
                            Icon(Icons.Default.Clear, contentDescription = "Clear")
                        }
                    }
                },
                singleLine = true,
                colors = OutlinedTextFieldDefaults.colors(
                    focusedTextColor = MaterialTheme.colorScheme.onSurface,
                    unfocusedTextColor = MaterialTheme.colorScheme.onSurface,
                    focusedContainerColor = MaterialTheme.colorScheme.surface,
                    unfocusedContainerColor = MaterialTheme.colorScheme.surface,
                    focusedPlaceholderColor = MaterialTheme.colorScheme.onSurfaceVariant,
                    unfocusedPlaceholderColor = MaterialTheme.colorScheme.onSurfaceVariant
                ),
                keyboardOptions = KeyboardOptions(imeAction = ImeAction.Search),
                keyboardActions = KeyboardActions(onSearch = {
                    focusManager.clearFocus()
                })
            )

            if (filteredSongs.isEmpty() && searchQuery.isNotEmpty()) {
                Text(
                    text = stringResource(R.string.search_page_no_results),
                    modifier = Modifier.padding(16.dp),
                    style = MaterialTheme.typography.bodyLarge
                )
            } else {
                SongListContent(
                    songs = filteredSongs,
                    navController = navController,
                    modifier = Modifier.fillMaxSize()
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun SearchPagePreview() {
    val navController = rememberNavController()
    val mockSongs = listOf(
        SongMetadata("Song 1", "Author 1", "God 1", "song1.md"),
        SongMetadata("Song 2", "Author 2", "God 2", "song2.md")
    )
    SearchPage(
        navController = navController,
        onSearch = { query ->
            if (query.isEmpty()) mockSongs
            else mockSongs.filter { it.title.contains(query, ignoreCase = true) || it.author.contains(query, ignoreCase = true) }
        }
    )
}
