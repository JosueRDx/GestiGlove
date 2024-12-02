package com.josuerdx.gestiglove.screens.gestos

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier

/**
 * Diálogo para editar el significado de un gesto.
 *
 * @param currentSignificado Texto actual del gesto.
 * @param onSave Acción al guardar los cambios.
 * @param onCancel Acción al cancelar el diálogo.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditGestoDialog(
    currentSignificado: String,
    onSave: (String) -> Unit,
    onCancel: () -> Unit
) {
    var newSignificado by remember { mutableStateOf(currentSignificado) }

    AlertDialog(
        onDismissRequest = onCancel,
        title = { Text("Editar Significado") },
        text = {
            OutlinedTextField(
                value = newSignificado,
                onValueChange = { newSignificado = it },
                label = { Text("Nuevo significado") },
                modifier = Modifier.fillMaxWidth(),
                colors = TextFieldDefaults.outlinedTextFieldColors()
            )
        },
        confirmButton = {
            TextButton(onClick = { onSave(newSignificado) }) {
                Text("Guardar")
            }
        },
        dismissButton = {
            TextButton(onClick = onCancel) {
                Text("Cancelar")
            }
        }
    )
}