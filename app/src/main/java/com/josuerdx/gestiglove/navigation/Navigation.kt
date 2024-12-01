package com.josuerdx.gestiglove.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
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
                SettingsScreen(paddingValues)
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
                MenuScreen(paddingValues)
            }
        }
    }
}