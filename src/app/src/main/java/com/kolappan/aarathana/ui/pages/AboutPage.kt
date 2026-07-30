package com.kolappan.aarathana.ui.pages

import android.content.Intent
import androidx.compose.foundation.background
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.tooling.preview.Preview
import androidx.core.net.toUri
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.kolappan.aarathana.R
import com.kolappan.aarathana.ui.components.AarathanaTopBar

@Composable
fun AboutPage(navController: NavController, onMenuClick: () -> Unit) {
    val context = LocalContext.current
    val packageManager = context.packageManager
    val packageName = context.packageName
    
    val versionName = try {
        packageManager.getPackageInfo(packageName, 0).versionName
    } catch (_: Exception) {
        "Unknown"
    }
    
    val appName = stringResource(id = R.string.app_display_title)
    val githubUrl = "https://github.com/kolappannathan/aarathana"

    Scaffold(
        topBar = {
            AarathanaTopBar(
                navController = navController,
                canNavigateBack = false,
                title = stringResource(R.string.about_page_title),
                onMenuClick = onMenuClick
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Box(
                modifier = Modifier
                    .size(100.dp)
                    .clip(MaterialTheme.shapes.medium)
                    .background(colorResource(id = R.color.ic_launcher_background)),
                contentAlignment = Alignment.Center
            ) {
                Image(
                    painter = painterResource(id = R.drawable.ic_launcher_foreground),
                    contentDescription = "App Icon",
                    modifier = Modifier.fillMaxSize()
                )
            }
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = appName,
                style = MaterialTheme.typography.headlineMedium
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = "Version: $versionName",
                style = MaterialTheme.typography.bodyLarge
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = "License: GPL 3.0",
                style = MaterialTheme.typography.bodyLarge
            )
            Spacer(modifier = Modifier.height(24.dp))
            Text(
                text = "Developed with ❤️ by Kolappan Nathan",
                style = MaterialTheme.typography.bodyMedium
            )
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = "View Source code",
                style = MaterialTheme.typography.bodyMedium.copy(
                    color = Color.Blue,
                    textDecoration = TextDecoration.Underline
                ),
                modifier = Modifier.clickable {
                    val intent = Intent(Intent.ACTION_VIEW, githubUrl.toUri())
                    context.startActivity(intent)
                }
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun AboutPagePreview() {
    val navController = rememberNavController()
    AboutPage(navController = navController, onMenuClick = {})
}
