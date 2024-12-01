package com.josuerdx.gestiglove.ui.theme

import android.app.Activity
import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat
import com.josuerdx.gestiglove.repository.ThemeRepository

// Esquema de colores para el tema oscuro
private val DarkColorScheme = darkColorScheme(
    primary = PrimaryDark,
    primaryContainer = PrimaryContainerDark,
    secondary = SecondaryDark,
    secondaryContainer = SecondaryContainerDark,
    background = BackgroundDark,
    surface = SurfaceDark,
    onPrimary = OnPrimaryDark,
    onSecondary = OnSecondaryDark,
    onBackground = OnBackgroundDark,
    onSurface = OnSurfaceDark
)

// Esquema de colores para el tema claro
private val LightColorScheme = lightColorScheme(
    primary = PrimaryLight,
    primaryContainer = PrimaryContainerLight,
    secondary = SecondaryLight,
    secondaryContainer = SecondaryContainerLight,
    background = BackgroundLight,
    surface = SurfaceLight,
    onPrimary = OnPrimaryLight,
    onSecondary = OnSecondaryLight,
    onBackground = OnBackgroundLight,
    onSurface = OnSurfaceLight
)

@Composable
fun GestiGloveTheme(
    themeRepository: ThemeRepository,
    dynamicColor: Boolean = true,
    initialTheme: String? = null,
    content: @Composable () -> Unit
) {
    val selectedTheme by themeRepository.selectedTheme.collectAsState(
        initial = initialTheme ?: ThemeRepository.THEME_LIGHT
    )
    val darkTheme = selectedTheme == ThemeRepository.THEME_DARK

    // Determina el esquema de colores según las condiciones
    val colorScheme = when {
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            val context = LocalContext.current
            if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
        }
        darkTheme -> DarkColorScheme
        else -> LightColorScheme
    }

    // Configura el color de la barra de estado
    val view = LocalView.current
    if (!view.isInEditMode) {
        val window = (view.context as Activity).window
        WindowCompat.setDecorFitsSystemWindows(window, false)
        window.statusBarColor = colorScheme.primary.toArgb()
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}