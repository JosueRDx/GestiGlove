package com.josuerdx.gestiglove.screens.gestos

import androidx.compose.animation.*
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.josuerdx.gestiglove.data.model.Gesto
import com.josuerdx.gestiglove.viewmodel.gestures.GestosViewModel
import com.josuerdx.gestiglove.viewmodel.gestures.GestosViewModelFactory
import kotlinx.coroutines.delay

@Composable
fun GestosScreen(
    paddingValues: PaddingValues,
    viewModel: GestosViewModel = viewModel(factory = GestosViewModelFactory())
) {
    val gestos by viewModel.gestos.collectAsState()
    val editResult by viewModel.editResult.collectAsState()
    var isDialogOpen by remember { mutableStateOf(false) }
    var selectedGesto by remember { mutableStateOf<Gesto?>(null) }
    var showMessage by remember { mutableStateOf(false) }

    // Manejo del mensaje
    LaunchedEffect(editResult) {
        if (editResult != null) {
            showMessage = true
            delay(3000)
            showMessage = false
            viewModel.clearEditResult()
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(paddingValues)
            .padding(16.dp)
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Lista de gestos
            GestureList(
                gestos = gestos,
                onEditClick = { gesto ->
                    selectedGesto = gesto
                    isDialogOpen = true
                }
            )
        }

        // Mostrar mensaje
        AnimatedVisibility(
            visible = showMessage,
            enter = slideInVertically(initialOffsetY = { it }) + fadeIn(),
            exit = slideOutVertically(targetOffsetY = { it }) + fadeOut(),
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(16.dp)
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
                    .height(50.dp),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = editResult ?: "",
                    color = MaterialTheme.colorScheme.primary,
                    style = MaterialTheme.typography.bodyLarge,
                    fontWeight = FontWeight.Bold
                )
            }
        }

        // Diálogo para editar gesto
        if (isDialogOpen && selectedGesto != null) {
            EditGestoDialog(
                currentSignificado = selectedGesto!!.significado,
                onSave = { newSignificado ->
                    viewModel.updateGesto(selectedGesto!!.id, newSignificado)
                    isDialogOpen = false
                },
                onCancel = { isDialogOpen = false }
            )
        }
    }
}