package com.josuerdx.gestiglove.screens.menu

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

/**
 * Pantalla de Menú principal.
 */
@Composable
fun MenuScreen(
    paddingValues: PaddingValues,
    onNavigateToGestos: () -> Unit = {},
    onNavigateToProfile: () -> Unit = {},
    onNavigateToSettings: () -> Unit = {},
    onNavigateToInfo: () -> Unit = {}
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(paddingValues)
            .padding(top = 12. dp, start = 16.dp, end = 16.dp),
        contentAlignment = Alignment.TopStart
    ) {
        Column(
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.Start,
            modifier = Modifier.fillMaxWidth()
        ) {
            // Sección Funcionalidades
            SectionTitle("Funcionalidades")
            MenuCard(
                label = "Mis Gestos",
                icon = Icons.Default.PanTool,
                onClick = onNavigateToGestos
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Sección Cuenta y Configuración
            SectionTitle("Cuenta y Configuración")
            MenuCard(
                label = "Perfil",
                icon = Icons.Default.Person,
                onClick = onNavigateToProfile
            )
            MenuCard(
                label = "Configuraciones",
                icon = Icons.Default.Settings,
                onClick = onNavigateToSettings
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Sección Acerca de GestiGlove
            SectionTitle("Acerca de GestiGlove")
            MenuCard(
                label = "Información",
                icon = Icons.Default.Info,
                onClick = onNavigateToInfo
            )
        }
    }
}
