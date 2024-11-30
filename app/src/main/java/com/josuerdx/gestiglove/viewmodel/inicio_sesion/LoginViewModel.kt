package com.josuerdx.gestiglove.viewmodel.inicio_sesion

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.josuerdx.gestiglove.repository.AuthRepository
import com.josuerdx.gestiglove.utils.messages.ErrorMessages
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

/**
 * ViewModel para manejar la lógica de la pantalla de inicio de sesión.
 * @param repository Instancia del repositorio para operaciones de autenticación.
 */
class LoginViewModel(private val repository: AuthRepository) : ViewModel() {

    private val _uiState = MutableStateFlow<LoginUiState>(LoginUiState.Idle)
    val uiState: StateFlow<LoginUiState> = _uiState

    fun login(email: String, password: String, context: Context) {
        _uiState.value = LoginUiState.Loading
        viewModelScope.launch {
            kotlinx.coroutines.delay(1000)
            _uiState.value = when {
                email.isBlank() || password.isBlank() -> {
                    LoginUiState.Error(ErrorMessages.invalidCredentials(context))
                }
                repository.login(email, password) -> {
                    syncPendingUsers()
                    LoginUiState.Success
                }
                else -> {
                    LoginUiState.Error(ErrorMessages.invalidCredentials(context))
                }
            }
        }
    }

    fun syncPendingUsers() {
        viewModelScope.launch {
            repository.syncPendingUsers()
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