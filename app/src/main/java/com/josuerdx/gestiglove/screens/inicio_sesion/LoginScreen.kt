package com.josuerdx.gestiglove.screens.inicio_sesion

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.josuerdx.gestiglove.GestiGloveApp
import com.josuerdx.gestiglove.R
import com.josuerdx.gestiglove.repository.AuthRepository
import com.josuerdx.gestiglove.viewmodel.inicio_sesion.LoginViewModel
import com.josuerdx.gestiglove.viewmodel.inicio_sesion.LoginUiState
import com.josuerdx.gestiglove.viewmodel.inicio_sesion.LoginViewModelFactory
import androidx.compose.runtime.livedata.observeAsState

/**
 * Pantalla de inicio de sesión.
 * @param onNavigateToRegister Función para navegar a la pantalla de registro.
 * @param onNavigateToHome Función para navegar a la pantalla de home.
 */
@Composable
fun LoginScreen(
    onNavigateToRegister: () -> Unit,
    onNavigateToHome: () -> Unit
) {
    // Crear instancia de LoginViewModel con su factory personalizada
    val context = LocalContext.current
    val repository = remember { AuthRepository(context) }
    val viewModel: LoginViewModel = viewModel(factory = LoginViewModelFactory(repository))

    val uiState by viewModel.uiState.collectAsState()
    val isOnline = (context.applicationContext as GestiGloveApp).isOnline.observeAsState(false)

    LaunchedEffect(isOnline.value) {
        if (isOnline.value == true) {
            viewModel.syncPendingUsers()
        }
    }

    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var passwordVisible by remember { mutableStateOf(false) }

    // Controla el enfoque entre campos
    val emailFocusRequester = FocusRequester()
    val passwordFocusRequester = FocusRequester()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 35.dp, vertical = 16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Logo
        Image(
            painter = painterResource(id = R.drawable.ic_launcher),
            contentDescription = stringResource(R.string.app_name),
            modifier = Modifier.size(135.dp)
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Título
        Text(
            text = stringResource(R.string.titulo_inicio_sesion),
            style = MaterialTheme.typography.titleLarge,
            color = MaterialTheme.colorScheme.primary,
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.height(20.dp))

        // Campo de correo
        OutlinedTextField(
            value = email,
            onValueChange = { email = it },
            label = { Text(stringResource(R.string.campo_correo_inicio)) },
            modifier = Modifier
                .fillMaxWidth()
                .focusRequester(emailFocusRequester),
            keyboardOptions = KeyboardOptions.Default.copy(
                imeAction = ImeAction.Next
            ),
            keyboardActions = KeyboardActions(
                onNext = { passwordFocusRequester.requestFocus() }
            )
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Campo de contraseña
        OutlinedTextField(
            value = password,
            onValueChange = { password = it },
            label = { Text(stringResource(R.string.campo_contrasena_inicio)) },
            modifier = Modifier
                .fillMaxWidth()
                .focusRequester(passwordFocusRequester),
            visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
            keyboardOptions = KeyboardOptions.Default.copy(
                imeAction = ImeAction.Done
            ),
            keyboardActions = KeyboardActions(
                onDone = { viewModel.login(email, password, context) }
            ),
            trailingIcon = {
                val icon = if (passwordVisible) Icons.Filled.Visibility else Icons.Filled.VisibilityOff
                IconButton(onClick = { passwordVisible = !passwordVisible }) {
                    Icon(imageVector = icon, contentDescription = null)
                }
            }
        )

        Spacer(modifier = Modifier.height(24.dp))

        // Botón para iniciar sesión
        Button(
            onClick = { viewModel.login(email, password, context) },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(text = stringResource(R.string.boton_inicio_sesion))
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Enlace para registrarse
        TextButton(onClick = onNavigateToRegister) {
            Text(text = stringResource(R.string.texto_registrarse_inicio))
        }

        // Manejo del estado de la UI
        when (uiState) {
            is LoginUiState.Loading -> {
                CircularProgressIndicator(
                    modifier = Modifier
                        .align(Alignment.CenterHorizontally)
                        .padding(vertical = 16.dp)
                )
            }
            is LoginUiState.Error -> Text(
                text = (uiState as LoginUiState.Error).message,
                color = MaterialTheme.colorScheme.error,
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(vertical = 8.dp)
            )
            is LoginUiState.Success -> {
                LaunchedEffect(Unit) {
                    onNavigateToHome()
                }
            }
            else -> {}
        }
    }
}

@Preview(showBackground = true)
@Composable
fun LoginScreenPreview() {
    MaterialTheme {
        LoginScreen(
            onNavigateToRegister = {},
            onNavigateToHome = {}
        )
    }
}
