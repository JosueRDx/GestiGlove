package com.josuerdx.gestiglove.screens.gestos

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.josuerdx.gestiglove.data.model.Gesto

/**
 * Componente para mostrar la lista de gestos.
 *
 * @param gestos Lista de gestos a mostrar.
 * @param onEditClick Acción al presionar el botón de editar un gesto.
 */
@Composable
fun GestureList(
    gestos: List<Gesto>,
    onEditClick: (Gesto) -> Unit
) {
    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(gestos.size) { index ->
            val gesto = gestos[index]
            GestureItem(
                significado = gesto.significado,
                onEditClick = { onEditClick(gesto) }
            )
        }
    }
}