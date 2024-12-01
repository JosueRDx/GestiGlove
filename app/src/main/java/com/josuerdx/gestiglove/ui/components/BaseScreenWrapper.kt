package com.josuerdx.gestiglove.ui.components

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import com.josuerdx.gestiglove.navigation.NavigationRoutes

/**
 * Envoltorio para pantallas que usan el AppBaseLayout.
 * Maneja la navegación (TopBar y AnimatedNavigationBar).
 */
@Composable
fun BaseScreenWrapper(
    title: String,
    navController: NavHostController,
    selectedRoute: String,
    content: @Composable (PaddingValues) -> Unit
) {
    AppBaseLayout(
        title = title,
        onSettingsClick = { navController.navigate(NavigationRoutes.Settings.route) },
        onProfileClick = { navController.navigate(NavigationRoutes.Profile.route) },
        onHomeClick = { navController.navigate(NavigationRoutes.Home.route) },
        onMenuClick = { navController.navigate(NavigationRoutes.Menu.route) },
        selectedRoute = selectedRoute,
        content = content
    )
}