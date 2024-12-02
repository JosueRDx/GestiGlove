package com.josuerdx.gestiglove.screens.gestos

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

/**
 * Pantalla de "Mis Gestos".
 * Muestra la lista de gestos personalizados del usuario.
 *
 * @param paddingValues Espaciado para evitar solapamiento con barras superiores o inferiores.
 */
@Composable
fun GestosScreen(paddingValues: PaddingValues) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(paddingValues)
            .padding(16.dp),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = "Aquí se mostrarán tus gestos personalizados.",
            style = MaterialTheme.typography.titleLarge,
            textAlign = TextAlign.Center,
            modifier = Modifier.fillMaxWidth()
        )
    }
}

@Preview(showBackground = true)
@Composable
fun GestosScreenPreview() {
    GestosScreen(paddingValues = PaddingValues(0.dp))
}