package com.josuerdx.gestiglove.viewmodel.registro

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.josuerdx.gestiglove.repository.AuthRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

/**
 * ViewModel para manejar la lógica de la pantalla de registro.
 * @param repository Instancia del repositorio para operaciones de autenticación.
 */
class RegisterViewModel(private val repository: AuthRepository) : ViewModel() {

    // Estado de la UI
    private val _uiState = MutableStateFlow<RegisterUiState>(RegisterUiState.Idle)
    val uiState: StateFlow<RegisterUiState> = _uiState

    /**
     * Realiza el registro llamando al repositorio.
     * Actualiza el estado de la UI según el resultado.
     * @param name Nombre completo ingresado por el usuario.
     * @param email Correo ingresado por el usuario.
     * @param password Contraseña ingresada por el usuario.
     */
    fun register(name: String, email: String, password: String) {
        _uiState.value = RegisterUiState.Loading
        viewModelScope.launch {
            val success = repository.register(name, email, password)
            _uiState.value = if (success) RegisterUiState.Success else RegisterUiState.Error("Datos inválidos.")
        }
    }
}

// Estados de la pantalla de registro.
sealed class RegisterUiState {
    object Idle : RegisterUiState()
    object Loading : RegisterUiState()
    object Success : RegisterUiState()
    data class Error(val message: String) : RegisterUiState()
}