package com.josuerdx.gestiglove.screens.menu

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

/**
 * Componente para mostrar un título.
 *
 * @param title Texto del título.
 */
@Composable
fun SectionTitle(title: String) {
    Text(
        text = title,
        style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
        modifier = Modifier.padding(vertical = 8.dp),
        color = MaterialTheme.colorScheme.primary
    )
}

@Preview(showBackground = true)
@Composable
fun SectionTitlePreview() {
    SectionTitle("Funcionalidades")
}