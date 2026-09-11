package com.kolappan.aarathana.ui.components

import androidx.compose.ui.graphics.Color
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.tv.material3.*
import com.kolappan.aarathana.R
import com.kolappan.aarathana.models.navigationItems
import com.kolappan.aarathana.ui.theme.Purple80
import com.kolappan.aarathana.ui.theme.PurpleGrey80
import com.kolappan.aarathana.ui.theme.Pink80

@OptIn(ExperimentalTvMaterial3Api::class)
@Composable
fun TvAppNavigation(
    currentRoute: String?,
    onNavigate: (String) -> Unit,
    content: @Composable () -> Unit
) {
    val tvColorScheme = darkColorScheme(
        primary = Purple80,
        onPrimary = Color(0xFF381E72),
        primaryContainer = Color(0xFF4F378B),
        onPrimaryContainer = Color(0xFFEADDFF),
        secondary = PurpleGrey80,
        onSecondary = Color(0xFF332D41),
        secondaryContainer = Color(0xFF4A4458),
        onSecondaryContainer = Color(0xFFE8DEF8),
        tertiary = Pink80,
        onTertiary = Color(0xFF492532),
        tertiaryContainer = Color(0xFF633B48),
        onTertiaryContainer = Color(0xFFFFD8E4),
        background = Color(0xFF1C1B1F),
        onBackground = Color(0xFFE6E1E5),
        surface = Color(0xFF1C1B1F),
        onSurface = Color(0xFFE6E1E5),
        surfaceVariant = Color(0xFF49454F),
        onSurfaceVariant = Color(0xFFCAC4D0),
    )

    MaterialTheme(colorScheme = tvColorScheme) {
        NavigationDrawer(
            drawerContent = {
                Column(
                    modifier = Modifier
                        .fillMaxHeight()
                        .padding(12.dp)
                ) {
                    navigationItems.forEach { item ->
                        NavigationDrawerItem(
                            selected = currentRoute == item.route,
                            onClick = { onNavigate(item.route) },
                            leadingContent = {
                                Icon(item.icon, contentDescription = null)
                            },
                            colors = NavigationDrawerItemDefaults.colors(
                                contentColor = MaterialTheme.colorScheme.onSurface,
                                selectedContentColor = MaterialTheme.colorScheme.onSecondaryContainer,
                                focusedContentColor = MaterialTheme.colorScheme.onPrimaryContainer,
                                selectedContainerColor = MaterialTheme.colorScheme.secondaryContainer,
                                focusedContainerColor = MaterialTheme.colorScheme.primaryContainer
                            )
                        ) {
                            Text(
                                text = stringResource(item.labelRes),
                                modifier = Modifier.padding(horizontal = 12.dp)
                            )
                        }
                        Spacer(modifier = Modifier.height(8.dp))
                    }
                }
            }
        ) {
            content()
        }
    }
}
