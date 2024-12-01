package com.josuerdx.gestiglove.ui.components


import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import com.josuerdx.gestiglove.repository.ThemeRepository
import com.josuerdx.gestiglove.ui.theme.GestiGloveTheme

/**
 * Diseño base que incluye TopBar y AnimatedNavigationBar.
 */
@Composable
fun AppBaseLayout(
    title: String,
    selectedRoute: String,
    onSettingsClick: () -> Unit,
    onProfileClick: () -> Unit,
    onHomeClick: () -> Unit,
    onMenuClick: () -> Unit,
    content: @Composable (PaddingValues) -> Unit
) {
    Scaffold(
        topBar = {
            TopBar(
                title = title,
                onSettingsClick = onSettingsClick
            )
        },
        bottomBar = {
            AnimatedNavigationBar(
                selectedRoute = selectedRoute,
                onProfileClick = onProfileClick,
                onHomeClick = onHomeClick,
                onMenuClick = onMenuClick
            )
        },
        content = { paddingValues ->
            content(paddingValues)
        }
    )
}

@Preview(showBackground = true)
@Composable
fun AppBaseLayoutPreview() {
    val themeRepository = ThemeRepository.getInstance(LocalContext.current)
    GestiGloveTheme(themeRepository = themeRepository)  {
        AppBaseLayout(
            title = "GestiGlove",
            selectedRoute = "home",
            onSettingsClick = {},
            onProfileClick = {},
            onHomeClick = {},
            onMenuClick = {}
        ) { padding ->
            Text(
                text = "Contenido Principal",
                modifier = Modifier.padding(padding)
            )
        }
    }
}