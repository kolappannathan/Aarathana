package com.kolappan.aarathana

import android.content.pm.PackageManager
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.kolappan.aarathana.ui.pages.HomePage
import com.kolappan.aarathana.ui.pages.SongLyricPage
import com.kolappan.aarathana.ui.pages.AboutPage
import com.kolappan.aarathana.ui.pages.SearchPage
import com.kolappan.aarathana.ui.pages.TagPage
import com.kolappan.aarathana.ui.components.AppNavigationDrawerContent
import com.kolappan.aarathana.ui.components.TvAppNavigation
import kotlinx.coroutines.launch
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.kolappan.aarathana.ui.viewmodels.SongViewModel
import com.kolappan.aarathana.ui.theme.AarathanaTheme
import com.kolappan.aarathana.models.Song
import com.kolappan.aarathana.models.SongMetadata

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        val isTv = packageManager.hasSystemFeature(PackageManager.FEATURE_LEANBACK)
        setContent {
            AarathanaTheme(
                darkTheme = if (isTv) true else isSystemInDarkTheme(),
                dynamicColor = !isTv
            ) {
                AppNavigation()
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    AarathanaTheme {
        val mockSongs = listOf(
            SongMetadata("Song 1", "Author 1", "God 1", "song1.md"),
            SongMetadata("Song 2", "Author 2", "God 2", "song2.md")
        )
        val navController = rememberNavController()
        AppNavigationContent(
            navController = navController,
            songs = mockSongs,
            onGetSongByTitle = { title -> Song(title, "Author", "Lyrics", "God") }
        )
    }
}

@Composable
fun AppNavigation(
    viewModel: SongViewModel = viewModel()
) {
    val songs by viewModel.songsState.collectAsState()
    val navController = rememberNavController()
    
    AppNavigationContent(
        navController = navController,
        songs = songs,
        onGetSongByTitle = { title -> viewModel.getSongByTitle(title) },
        onGetSongsByAuthor = { author -> viewModel.getSongsByAuthor(author) },
        onGetSongsByGod = { god -> viewModel.getSongsByGod(god) },
        onSearch = { query -> viewModel.searchSongs(query) }
    )
}

@Composable
fun AppNavigationContent(
    navController: androidx.navigation.NavHostController,
    songs: List<SongMetadata>,
    onGetSongByTitle: (String) -> Song?,
    onGetSongsByAuthor: (String) -> List<SongMetadata> = { emptyList() },
    onGetSongsByGod: (String) -> List<SongMetadata> = { emptyList() },
    onSearch: (String) -> List<SongMetadata> = { emptyList() }
) {
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val scope = rememberCoroutineScope()
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route
    val context = LocalContext.current
    val isTv = remember {
        context.packageManager.hasSystemFeature(PackageManager.FEATURE_LEANBACK)
    }

    if (isTv) {
        TvAppNavigation(
            currentRoute = currentRoute,
            onNavigate = { route ->
                navController.navigate(route) {
                    if (route == "home") {
                        popUpTo("home") { inclusive = true }
                    }
                }
            }
        ) {
            AppNavHost(
                navController = navController,
                songs = songs,
                onGetSongByTitle = onGetSongByTitle,
                onGetSongsByAuthor = onGetSongsByAuthor,
                onGetSongsByGod = onGetSongsByGod,
                onSearch = onSearch,
                onMenuClick = null // D-pad navigation replaces the menu button on TV
            )
        }
    } else {
        ModalNavigationDrawer(
            drawerState = drawerState,
            drawerContent = {
                AppNavigationDrawerContent(
                    currentRoute = currentRoute,
                    onNavigate = { route ->
                        navController.navigate(route) {
                            if (route == "home") {
                                popUpTo("home") { inclusive = true }
                            }
                        }
                    },
                    onCloseDrawer = { scope.launch { drawerState.close() } }
                )
            }
        ) {
            AppNavHost(
                navController = navController,
                songs = songs,
                onGetSongByTitle = onGetSongByTitle,
                onGetSongsByAuthor = onGetSongsByAuthor,
                onGetSongsByGod = onGetSongsByGod,
                onSearch = onSearch,
                onMenuClick = { scope.launch { drawerState.open() } }
            )
        }
    }
}

@Composable
fun AppNavHost(
    navController: NavHostController,
    songs: List<SongMetadata>,
    onGetSongByTitle: (String) -> Song?,
    onGetSongsByAuthor: (String) -> List<SongMetadata>,
    onGetSongsByGod: (String) -> List<SongMetadata>,
    onSearch: (String) -> List<SongMetadata>,
    onMenuClick: (() -> Unit)?
) {
    NavHost(navController = navController, startDestination = "home") {
        composable("home") {
            HomePage(navController, songs, onMenuClick = onMenuClick)
        }
        composable("search") {
            SearchPage(navController, onSearch = onSearch)
        }
        composable("about") {
            AboutPage(navController, onMenuClick = onMenuClick)
        }
        composable(
            route = "lyrics/{songTitle}",
            arguments = listOf(navArgument("songTitle") { type = NavType.StringType })
        ) { backStackEntry ->
            val songTitle = backStackEntry.arguments?.getString("songTitle")
            if (songTitle != null) {
                val song = onGetSongByTitle(songTitle)
                if (song != null) {
                    SongLyricPage(navController, song = song)
                }
            }
        }
        composable(
            route = "author/{authorName}",
            arguments = listOf(navArgument("authorName") { type = NavType.StringType })
        ) { backStackEntry ->
            val authorName = backStackEntry.arguments?.getString("authorName")
            if (authorName != null) {
                val authorSongs = onGetSongsByAuthor(authorName)
                TagPage(navController, authorName, authorSongs)
            }
        }
        composable(
            route = "god/{godName}",
            arguments = listOf(navArgument("godName") { type = NavType.StringType })
        ) { backStackEntry ->
            val godName = backStackEntry.arguments?.getString("godName")
            if (godName != null) {
                val godSongs = onGetSongsByGod(godName)
                TagPage(navController, godName, godSongs)
            }
        }
    }
}
