package com.josuerdx.gestiglove.screens.profile

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.josuerdx.gestiglove.viewmodel.profile.ProfileViewModel
import com.josuerdx.gestiglove.viewmodel.profile.ProfileViewModelFactory

/**
 * Pantalla principal del perfil del usuario.
 * @param paddingValues Valores de padding para evitar superposición con otros elementos.
 * @param viewModel ViewModel que gestiona el estado de la pantalla.
 */
@Composable
fun ProfileScreen(
    paddingValues: PaddingValues,
    viewModel: ProfileViewModel = viewModel(factory = ProfileViewModelFactory(LocalContext.current))
) {
    val uiState by viewModel.uiState.collectAsState()
    var passwordVisible by rememberSaveable { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(paddingValues)
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        ProfileIcon()

        Spacer(modifier = Modifier.height(32.dp))

        // Información
        Card(
            modifier = Modifier.fillMaxWidth(),
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.surfaceVariant
            ),
            elevation = CardDefaults.cardElevation(2.dp)
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                SectionTitle("INFORMACIÓN")
                UserInformation(email = uiState.email)
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        // Detalles
        Card(
            modifier = Modifier.fillMaxWidth(),
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.surfaceVariant
            ),
            elevation = CardDefaults.cardElevation(2.dp)
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                SectionTitle("DETALLES")
                PasswordDetails(password = uiState.password, passwordVisible) {
                    passwordVisible = !passwordVisible
                }
            }
        }
    }
}