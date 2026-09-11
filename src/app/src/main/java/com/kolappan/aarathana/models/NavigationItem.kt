package com.kolappan.aarathana.models

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Search
import androidx.compose.ui.graphics.vector.ImageVector
import com.kolappan.aarathana.R

data class NavigationItem(
    val route: String,
    val icon: ImageVector,
    val labelRes: Int
)

val navigationItems = listOf(
    NavigationItem("home", Icons.Default.Home, R.string.app_nav_drawer_home),
    NavigationItem("search", Icons.Default.Search, R.string.app_nav_drawer_search),
    NavigationItem("about", Icons.Default.Info, R.string.app_nav_drawer_about)
)
