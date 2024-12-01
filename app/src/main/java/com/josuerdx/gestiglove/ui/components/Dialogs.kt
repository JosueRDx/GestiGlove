package com.josuerdx.gestiglove.ui.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import com.josuerdx.gestiglove.repository.ThemeRepository

/**
 * Diálogo para seleccionar el tema de la aplicación.
 * @param onDismiss Acción a realizar cuando se cierra el diálogo.
 * @param onThemeSelected Acción a realizar al confirmar el tema seleccionado.
 * @param currentTheme Tema actual seleccionado.
 */
@Composable
fun ThemeDialog(
    onDismiss: () -> Unit,
    onThemeSelected: (String) -> Unit,
    currentTheme: String
) {
    var selectedOption by remember { mutableStateOf(currentTheme) }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = {
            Text(
                text = "Seleccionar Tema",
                style = MaterialTheme.typography.titleLarge,
                textAlign = TextAlign.Center
            )
        },
        text = {
            Column {
                RadioButtonItem(
                    label = ThemeRepository.THEME_LIGHT,
                    selected = selectedOption == ThemeRepository.THEME_LIGHT,
                    onSelect = { selectedOption = ThemeRepository.THEME_LIGHT }
                )
                RadioButtonItem(
                    label = ThemeRepository.THEME_DARK,
                    selected = selectedOption == ThemeRepository.THEME_DARK,
                    onSelect = { selectedOption = ThemeRepository.THEME_DARK }
                )
            }
        },
        confirmButton = {
            TextButton(onClick = {
                onThemeSelected(selectedOption)
                onDismiss()
            }) {
                Text("Aceptar")
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("Cancelar")
            }
        }
    )
}

/**
 * Diálogo para confirmar la eliminación de la cuenta.
 * @param onDismiss Acción a realizar cuando se cierra el diálogo.
 * @param onDeleteConfirmed Acción a realizar cuando el usuario confirma la eliminación.
 */

@Composable
fun DeleteAccountDialog(onDismiss: () -> Unit, onDeleteConfirmed: () -> Unit) {
    AlertDialog(
        onDismissRequest = onDismiss,
        title = {
            Text("Eliminar Cuenta", style = MaterialTheme.typography.titleLarge)
        },
        text = {
            Text("¿Estás seguro de eliminar tu cuenta?", style = MaterialTheme.typography.bodyMedium)
        },
        confirmButton = {
            TextButton(onClick = { onDeleteConfirmed(); onDismiss() }) {
                Text("Eliminar", color = MaterialTheme.colorScheme.error)
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("Cancelar")
            }
        }
    )
}

@Composable
fun RadioButtonItem(label: String, selected: Boolean, onSelect: () -> Unit) {
    Row(verticalAlignment = androidx.compose.ui.Alignment.CenterVertically) {
        RadioButton(selected = selected, onClick = onSelect)
        Text(text = label)
    }
}

@Preview(showBackground = true)
@Composable
fun ThemeDialogPreview() {
    ThemeDialog(
        onDismiss = {},
        onThemeSelected = {},
        currentTheme = "Claro"
    )
}