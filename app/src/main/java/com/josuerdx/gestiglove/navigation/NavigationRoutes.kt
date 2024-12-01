package com.josuerdx.gestiglove.navigation

/**
 * Define las rutas de navegación como constantes para evitar errores y facilitar su mantenimiento.
 */
sealed class NavigationRoutes(val route: String) {
    object Login : NavigationRoutes("login_screen")
    object Register : NavigationRoutes("register_screen")
    object Home : NavigationRoutes("home")
    object Menu : NavigationRoutes("menu")
    object Profile : NavigationRoutes("profile")
    object Settings : NavigationRoutes("settings")
    object Help : NavigationRoutes("help")
}