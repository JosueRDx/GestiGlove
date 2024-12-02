package com.josuerdx.gestiglove.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.josuerdx.gestiglove.repository.ThemeRepository
import com.josuerdx.gestiglove.screens.gestos.GestosScreen
import com.josuerdx.gestiglove.screens.help.HelpScreen
import com.josuerdx.gestiglove.screens.home.HomeScreen
import com.josuerdx.gestiglove.screens.inicio_sesion.LoginScreen
import com.josuerdx.gestiglove.screens.menu.MenuScreen
import com.josuerdx.gestiglove.screens.profile.ProfileScreen
import com.josuerdx.gestiglove.screens.registro.RegisterScreen
import com.josuerdx.gestiglove.screens.settings.SettingsScreen
import com.josuerdx.gestiglove.ui.components.BaseScreenWrapper

/**
 * Configura la navegación centralizada de la aplicación.
 * incluyendo TopBar y AnimatedNavigationBar.
 * @param navController Controlador de navegación.
 */
@Composable
fun AppNavigation(navController: NavHostController) {
    NavHost(
        navController = navController,
        startDestination = NavigationRoutes.Login.route
    ) {
        // Ruta para inicio de sesión
        composable(NavigationRoutes.Login.route) {
            LoginScreen(
                onNavigateToRegister = {
                    navController.navigate(NavigationRoutes.Register.route)
                },
                onNavigateToHome = {
                    navController.navigate(NavigationRoutes.Home.route) {
                        popUpTo(0) { inclusive = true }
                    }
                }
            )
        }

        // Ruta para registro
        composable(NavigationRoutes.Register.route) {
            RegisterScreen(onNavigateToLogin = {
                navController.navigate(NavigationRoutes.Login.route) {
                    popUpTo(NavigationRoutes.Register.route) { inclusive = false }
                }
            })
        }

        // Ruta para pantalla Inicio
        composable(NavigationRoutes.Home.route) {
            BaseScreenWrapper(
                title = "Inicio",
                selectedRoute = NavigationRoutes.Home.route,
                navController = navController
            ) { paddingValues ->
                HomeScreen(paddingValues)
            }
        }

        // Ruta para pantalla Configuración
        composable(NavigationRoutes.Settings.route) {
            BaseScreenWrapper(
                title = "Configuración",
                selectedRoute = NavigationRoutes.Settings.route,
                navController = navController
            ) { paddingValues ->
                val themeRepository = ThemeRepository.getInstance(navController.context)
                SettingsScreen(
                    paddingValues = paddingValues,
                    navController = navController,
                    themeRepository = themeRepository
                )
            }
        }

        // Ruta para pantalla Ayuda
        composable(NavigationRoutes.Help.route) {
            BaseScreenWrapper(
                title = "Ayuda",
                selectedRoute = "",
                navController = navController
            ) {
                HelpScreen()
            }
        }

        // Ruta para pantalla Perfil
        composable(NavigationRoutes.Profile.route) {
            BaseScreenWrapper(
                title = "Perfil",
                selectedRoute = NavigationRoutes.Profile.route,
                navController = navController
            ) { paddingValues ->
                ProfileScreen(paddingValues)
            }
        }

        // Ruta para pantalla Menú
        composable(NavigationRoutes.Menu.route) {
            BaseScreenWrapper(
                title = "Menú",
                selectedRoute = NavigationRoutes.Menu.route,
                navController = navController
            ) { paddingValues ->
                MenuScreen(
                    paddingValues = paddingValues,
                    onNavigateToGestos = { navController.navigate(NavigationRoutes.Gestos.route) },
                    onNavigateToProfile = { navController.navigate(NavigationRoutes.Profile.route) },
                    onNavigateToSettings = { navController.navigate(NavigationRoutes.Settings.route) },
                    onNavigateToInfo = { navController.navigate(NavigationRoutes.Help.route) }
                )
            }
        }

        // Ruta para pantalla "Mis Gestos"
        composable(NavigationRoutes.Gestos.route) {
            BaseScreenWrapper(
                title = "Mis Gestos",
                selectedRoute = NavigationRoutes.Gestos.route,
                navController = navController
            ) { paddingValues ->
                GestosScreen(paddingValues)
            }
        }
    }
}