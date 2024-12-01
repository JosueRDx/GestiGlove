package com.josuerdx.gestiglove.screens.settings

import android.app.Activity
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ExitToApp
import androidx.compose.material.icons.automirrored.filled.HelpOutline
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.josuerdx.gestiglove.navigation.NavigationRoutes
import com.josuerdx.gestiglove.repository.ThemeRepository
import com.josuerdx.gestiglove.viewmodel.settings.SettingsViewModel
import com.josuerdx.gestiglove.viewmodel.settings.SettingsViewModelFactory
import com.josuerdx.gestiglove.ui.components.DeleteAccountDialog
import com.josuerdx.gestiglove.ui.components.ThemeDialog

/**
 * Pantalla de Configuración.
 * Muestra opciones como cambiar el tema, cerrar sesión, eliminar cuenta, y acceder a ayuda.
 */

@Composable
fun SettingsScreen(
    paddingValues: PaddingValues,
    navController: NavHostController,
    themeRepository: ThemeRepository,
    viewModel: SettingsViewModel = viewModel(factory = SettingsViewModelFactory(LocalContext.current))
) {
    val uiState by viewModel.uiState.collectAsState()
    val selectedTheme by themeRepository.selectedTheme.collectAsState(initial = ThemeRepository.THEME_LIGHT)
    val context = LocalContext.current

    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(paddingValues)
            .padding(top = 24.dp, start = 16.dp, end = 16.dp),
        contentAlignment = Alignment.TopStart
    ) {
        Column(
            verticalArrangement = Arrangement.spacedBy(24.dp),
            horizontalAlignment = Alignment.Start
        ) {
            SettingsOption(
                title = "Cambiar Tema",
                description = "Selecciona entre claro y oscuro",
                icon = Icons.Default.Brightness4,
                onClick = { viewModel.showThemeDialog() }
            )

            SettingsOption(
                title = "Cerrar Sesión",
                description = "Salir de tu cuenta actual",
                icon = Icons.AutoMirrored.Filled.ExitToApp,
                onClick = {
                    viewModel.logout {
                        navController.navigate(NavigationRoutes.Login.route) {
                            popUpTo(0) { inclusive = true }
                        }
                    }
                }
            )

            SettingsOption(
                title = "Eliminar Cuenta",
                description = "Eliminar permanentemente tu cuenta",
                icon = Icons.Default.Delete,
                onClick = { viewModel.showDeleteAccountDialog() },
                color = MaterialTheme.colorScheme.error
            )

            SettingsOption(
                title = "Ayuda",
                description = "Obtén más información",
                icon = Icons.AutoMirrored.Filled.HelpOutline,
                onClick = { navController.navigate(NavigationRoutes.Help.route) }
            )
        }
    }

    if (uiState.showThemeDialog) {
        ThemeDialog(
            onDismiss = { viewModel.hideThemeDialog() },
            onThemeSelected = { selectedTheme ->
                viewModel.changeTheme(selectedTheme)
            },
            currentTheme = selectedTheme
        )
    }

    if (uiState.showDeleteAccountDialog) {
        DeleteAccountDialog(
            onDismiss = { viewModel.hideDeleteAccountDialog() },
            onDeleteConfirmed = {
                viewModel.deleteAccount {
                    navController.navigate(NavigationRoutes.Login.route) {
                        popUpTo(0) { inclusive = true }
                    }
                    (context as Activity).finishAffinity()
                }
            }
        )
    }
}

@Composable
fun SettingsOption(
    title: String,
    description: String,
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    onClick: () -> Unit,
    color: Color = MaterialTheme.colorScheme.primary
) {
    Card(
        onClick = onClick,
        modifier = Modifier
            .fillMaxWidth(),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = icon,
                contentDescription = null,
                tint = color,
                modifier = Modifier.size(24.dp)
            )
            Spacer(modifier = Modifier.width(16.dp))
            Column {
                Text(
                    text = title,
                    style = MaterialTheme.typography.titleMedium,
                    color = MaterialTheme.colorScheme.onSurface
                )
                Text(
                    text = description,
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun SettingsScreenPreview() {
    val navController = rememberNavController()
    val themeRepository = ThemeRepository.getInstance(LocalContext.current)

    SettingsScreen(
        paddingValues = PaddingValues(0.dp),
        navController = navController,
        themeRepository = themeRepository
    )
}
