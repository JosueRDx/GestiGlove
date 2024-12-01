package com.josuerdx.gestiglove.ui.components

import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.josuerdx.gestiglove.repository.ThemeRepository
import com.josuerdx.gestiglove.ui.theme.GestiGloveTheme

/**
 * Barra superior con título e ícono de ajustes.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopBar(
    title: String,
    onSettingsClick: () -> Unit
) {
    TopAppBar(
        title = { Text(
            text = title,
            style = MaterialTheme.typography.titleLarge,
        ) },
        actions = {
            IconButton(onClick = onSettingsClick) {
                Icon(
                    imageVector = Icons.Default.Settings,
                    modifier = Modifier.size(30.dp),
                    contentDescription = "Configuración")
            }
        },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = MaterialTheme.colorScheme.primaryContainer,
            titleContentColor = MaterialTheme.colorScheme.onPrimaryContainer,
            actionIconContentColor = MaterialTheme.colorScheme.onPrimaryContainer
        )
    )
}

@Preview(showBackground = true)
@Composable
fun TopBarPreview() {
    val themeRepository = ThemeRepository.getInstance(LocalContext.current)
    GestiGloveTheme(themeRepository = themeRepository)  {
        TopBar(
            title = "GestiGlove",
            onSettingsClick = {}
        )
    }
}