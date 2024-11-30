package com.josuerdx.gestiglove.screens.registro

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
import androidx.compose.ui.graphics.Color
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
import com.josuerdx.gestiglove.R
import com.josuerdx.gestiglove.repository.AuthRepository
import com.josuerdx.gestiglove.utils.validation.InputValidator
import com.josuerdx.gestiglove.viewmodel.registro.RegisterViewModel
import com.josuerdx.gestiglove.viewmodel.registro.RegisterUiState
import com.josuerdx.gestiglove.viewmodel.registro.RegisterViewModelFactory

/**
 * Pantalla de registro.
 * @param onNavigateToLogin Función para navegar a la pantalla de inicio de sesión.
 */
@Composable
fun RegisterScreen(
    onNavigateToLogin: () -> Unit
) {
    // Crear instancia de RegisterViewModel con su factory personalizada
    val context = LocalContext.current
    val repository = remember { AuthRepository(context) }
    val viewModel: RegisterViewModel = viewModel(factory = RegisterViewModelFactory(repository))

    val uiState by viewModel.uiState.collectAsState()

    var name by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var passwordVisible by remember { mutableStateOf(false) }
    var passwordError by remember { mutableStateOf<String?>(null) }

    // Controla el enfoque entre los campos
    val nameFocusRequester = FocusRequester()
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
            text = stringResource(R.string.titulo_registro),
            style = MaterialTheme.typography.titleLarge,
            color = MaterialTheme.colorScheme.primary,
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.height(24.dp))

        // Campo para el nombre
        OutlinedTextField(
            value = name,
            onValueChange = { name = it },
            label = { Text(stringResource(R.string.campo_nombre_registro)) },
            modifier = Modifier
                .fillMaxWidth()
                .focusRequester(nameFocusRequester),
            keyboardOptions = KeyboardOptions.Default.copy(
                imeAction = ImeAction.Next
            ),
            keyboardActions = KeyboardActions(
                onNext = { emailFocusRequester.requestFocus() }
            )
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Campo para el correo
        OutlinedTextField(
            value = email,
            onValueChange = { email = it },
            label = { Text(stringResource(R.string.campo_correo_registro)) },
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

        // Campo para la contraseña
        OutlinedTextField(
            value = password,
            onValueChange = {
                password = it
                passwordError = when {
                    !InputValidator.isPasswordValid(it) -> context.getString(R.string.error_contrasena_corta)
                    else -> null
                }
            },
            label = { Text(stringResource(R.string.campo_contrasena_registro)) },
            modifier = Modifier
                .fillMaxWidth()
                .focusRequester(passwordFocusRequester),
            visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
            keyboardOptions = KeyboardOptions.Default.copy(
                imeAction = ImeAction.Done
            ),
            keyboardActions = KeyboardActions(
                onDone = { viewModel.register(name, email, password, context) }
            ),
            trailingIcon = {
                val icon = if (passwordVisible) Icons.Filled.Visibility else Icons.Filled.VisibilityOff
                IconButton(onClick = { passwordVisible = !passwordVisible }) {
                    Icon(imageVector = icon, contentDescription = null)
                }
            },
            isError = passwordError != null
        )

        Spacer(modifier = Modifier.height(16.dp))

        if (passwordError != null) {
            Text(
                text = passwordError!!,
                color = MaterialTheme.colorScheme.error,
                style = MaterialTheme.typography.bodySmall,
                modifier = Modifier.padding(start = 8.dp)
            )
        }


        Spacer(modifier = Modifier.height(16.dp))

        // Botón para completar el registro
        Button(
            onClick = { viewModel.register(name, email, password, context) },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(text = stringResource(R.string.boton_registro))
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Enlace para iniciar sesión
        TextButton(onClick = onNavigateToLogin) {
            Text(text = stringResource(R.string.texto_iniciar_sesion_registro))
        }

        // Manejo del estado de la UI
        when (uiState) {
            is RegisterUiState.Loading -> {
                CircularProgressIndicator(
                    modifier = Modifier
                        .align(Alignment.CenterHorizontally)
                        .padding(vertical = 16.dp)
                )
            }
            is RegisterUiState.Success -> {
                Text(
                    text = (uiState as RegisterUiState.Success).message,
                    color = Color(0xFF4CAF50),
                    textAlign = TextAlign.Center
                )
                LaunchedEffect(Unit) { onNavigateToLogin() }
            }
            is RegisterUiState.PendingSync -> {
                Text(
                    text = "Registro completado.",
                    color = Color(0xFF4CAF50),
                    textAlign = TextAlign.Center
                )
                LaunchedEffect(Unit) { onNavigateToLogin() }
            }
            is RegisterUiState.Error ->
                Text(
                text = (uiState as RegisterUiState.Error).message,
                color = MaterialTheme.colorScheme.error,
                textAlign = TextAlign.Center
            )
            else -> {}
        }
    }
}

@Preview(showBackground = true)
@Composable
fun RegisterScreenPreview() {
    RegisterScreen(
        onNavigateToLogin = {}
    )
}
