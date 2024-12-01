package com.josuerdx.gestiglove.ui.components

import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.josuerdx.gestiglove.repository.ThemeRepository
import com.josuerdx.gestiglove.ui.theme.GestiGloveTheme

/**
 * Barra de navegación inferior.
 */
@Composable
fun AnimatedNavigationBar(
    selectedRoute: String,
    onProfileClick: () -> Unit,
    onHomeClick: () -> Unit,
    onMenuClick: () -> Unit
) {
    NavigationBar(
        modifier = Modifier.fillMaxWidth(),
        containerColor = MaterialTheme.colorScheme.primaryContainer
    ) {
        val routes = listOf("profile", "home", "menu")
        val icons = listOf(
            Icons.Default.Person to "Perfil",
            Icons.Default.Home to "Inicio",
            Icons.Default.Menu to "Menú"
        )

        routes.zip(icons).forEach { (route, icon) ->
            val isSelected = route == selectedRoute
            val iconColor by animateColorAsState(
                targetValue = if (isSelected) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onSurface,
                label = "Icon Color Animation"
            )
            NavigationBarItem(
                selected = isSelected,
                onClick = {
                    when (route) {
                        "profile" -> onProfileClick()
                        "home" -> onHomeClick()
                        "menu" -> onMenuClick()
                    }
                },
                icon = {
                    Icon(
                        imageVector = icon.first,
                        contentDescription = icon.second,
                        modifier = Modifier.size(30.dp),
                        tint = iconColor
                    )
                },
                label = {
                    Text(
                        text = icon.second,
                        style = MaterialTheme.typography.labelMedium,
                        color = iconColor
                    )
                },
                colors = NavigationBarItemDefaults.colors(
                    indicatorColor = MaterialTheme.colorScheme.primary.copy(alpha = 0.3f),
                    unselectedIconColor = MaterialTheme.colorScheme.onSurface,
                    unselectedTextColor = MaterialTheme.colorScheme.onSurface,
                    selectedTextColor = MaterialTheme.colorScheme.primary,
                    selectedIconColor = MaterialTheme.colorScheme.primary,


                )
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun AnimatedNavigationBarPreview() {
    val themeRepository = ThemeRepository.getInstance(LocalContext.current)
    GestiGloveTheme(themeRepository = themeRepository)  {
        AnimatedNavigationBar(
            selectedRoute = "home",
            onProfileClick = {},
            onHomeClick = {},
            onMenuClick = {}
        )
    }
}