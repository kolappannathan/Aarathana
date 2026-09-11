package com.kolappan.aarathana.ui.components

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.kolappan.aarathana.R
import com.kolappan.aarathana.models.navigationItems

@Composable
fun AppNavigationDrawerContent(
    currentRoute: String?,
    onNavigate: (String) -> Unit,
    onCloseDrawer: () -> Unit
) {
    ModalDrawerSheet {
        Text(stringResource(id = R.string.app_display_title), modifier = Modifier.padding(16.dp), style = MaterialTheme.typography.titleLarge)
        HorizontalDivider()
        Spacer(modifier = Modifier.height(8.dp))
        
        navigationItems.forEach { item ->
            NavigationDrawerItem(
                label = { Text(stringResource(item.labelRes)) },
                selected = currentRoute == item.route,
                onClick = {
                    onNavigate(item.route)
                    onCloseDrawer()
                },
                icon = { Icon(item.icon, contentDescription = null) },
                modifier = Modifier.padding(NavigationDrawerItemDefaults.ItemPadding)
            )
        }
    }
}

@Preview
@Composable
fun AppNavigationDrawerContentPreview() {
    AppNavigationDrawerContent(
        currentRoute = "home",
        onNavigate = {},
        onCloseDrawer = {}
    )
}
