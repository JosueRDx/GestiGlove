package com.josuerdx.gestiglove.viewmodel.registro

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.josuerdx.gestiglove.repository.AuthRepository
import com.josuerdx.gestiglove.utils.messages.ErrorMessages
import com.josuerdx.gestiglove.utils.messages.SuccessMessages
import com.josuerdx.gestiglove.utils.validation.InputValidator
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

/**
 * ViewModel para manejar la lógica de la pantalla de registro.
 * @param repository Instancia del repositorio para operaciones de autenticación.
 */
class RegisterViewModel(private val repository: AuthRepository) : ViewModel() {

    private val _uiState = MutableStateFlow<RegisterUiState>(RegisterUiState.Idle)
    val uiState: StateFlow<RegisterUiState> = _uiState

    fun register(name: String, email: String, password: String, context: Context) {
        _uiState.value = RegisterUiState.Loading
        viewModelScope.launch {
            kotlinx.coroutines.delay(1000)
            when {
                name.isBlank() || email.isBlank() || password.isBlank() -> {
                    _uiState.value = RegisterUiState.Error(ErrorMessages.genericError(context))
                    return@launch
                }
                !InputValidator.isEmailFormatValid(email) -> {
                    _uiState.value = RegisterUiState.Error(ErrorMessages.invalidEmail(context))
                    return@launch
                }
                !InputValidator.isEmailReal(email) -> {
                    _uiState.value = RegisterUiState.Error(ErrorMessages.invalidEmail(context))
                    return@launch
                }
                !InputValidator.isPasswordValid(password) -> {
                    _uiState.value = RegisterUiState.Error(ErrorMessages.shortPassword(context))
                    return@launch
                }
            }

            val isEmailInUse = repository.isEmailInUse(email)
            if (isEmailInUse) {
                _uiState.value = RegisterUiState.Error(ErrorMessages.emailInUse(context))
                return@launch
            }

            val success = repository.register(name, email, password)
            _uiState.value = if (success) {
                RegisterUiState.Success(SuccessMessages.registrationSuccess(context))
            } else {
                RegisterUiState.PendingSync
            }
        }
    }
}

// Estados de la pantalla de registro.
sealed class RegisterUiState {
    object Idle : RegisterUiState()
    object Loading : RegisterUiState()
    data class Success(val message: String) : RegisterUiState()
    object PendingSync : RegisterUiState()
    data class Error(val message: String) : RegisterUiState()
}