package com.josuerdx.gestiglove.viewmodel.inicio_sesion

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.josuerdx.gestiglove.repository.AuthRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

/**
 * ViewModel para manejar la lógica de la pantalla de inicio de sesión.
 * @param repository Instancia del repositorio para operaciones de autenticación.
 */
class LoginViewModel(private val repository: AuthRepository) : ViewModel() {

    // Estado de la UI
    private val _uiState = MutableStateFlow<LoginUiState>(LoginUiState.Idle)
    val uiState: StateFlow<LoginUiState> = _uiState

    /**
     * Realiza el inicio de sesión llamando al repositorio.
     * Actualiza el estado de la UI según el resultado.
     * @param email Correo ingresado por el usuario.
     * @param password Contraseña ingresada por el usuario.
     */
    fun login(email: String, password: String) {
        _uiState.value = LoginUiState.Loading
        viewModelScope.launch {
            val success = repository.login(email, password)
            _uiState.value = if (success) LoginUiState.Success else LoginUiState.Error("Credenciales inválidas.")
        }
    }
}

// Estados de la pantalla de inicio de sesión.
sealed class LoginUiState {
    object Idle : LoginUiState()
    object Loading : LoginUiState()
    object Success : LoginUiState()
    data class Error(val message: String) : LoginUiState()
}