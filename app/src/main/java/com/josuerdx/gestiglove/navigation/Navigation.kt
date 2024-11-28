package com.josuerdx.gestiglove.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.josuerdx.gestiglove.screens.inicio_sesion.LoginScreen
import com.josuerdx.gestiglove.screens.registro.RegisterScreen

/**
 * Configura la navegación centralizada de la aplicación.
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
            LoginScreen(onNavigateToRegister = {
                navController.navigate(NavigationRoutes.Register.route) {
                    popUpTo(NavigationRoutes.Login.route) { inclusive = false }
                }
            })
        }

        // Ruta para registro
        composable(NavigationRoutes.Register.route) {
            RegisterScreen(onNavigateToLogin = {
                navController.navigate(NavigationRoutes.Login.route) {
                    popUpTo(NavigationRoutes.Register.route) { inclusive = false }
                }
            })
        }
    }
}