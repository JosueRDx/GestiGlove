package com.josuerdx.gestiglove.viewmodel.profile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.josuerdx.gestiglove.repository.AuthRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

/**
 * ViewModel para la pantalla de perfil.
 */
class ProfileViewModel(private val authRepository: AuthRepository) : ViewModel() {

    private val _uiState = MutableStateFlow(ProfileUiState())
    val uiState: StateFlow<ProfileUiState> = _uiState

    init {
        viewModelScope.launch {
            val user = authRepository.getCurrentUser()
            val password = authRepository.getPasswordForUser(user?.email ?: "")
            _uiState.value = ProfileUiState(
                email = user?.email ?: "",
                password = password ?: ""
            )
        }
    }
}

/**
 * Estado de la UI para la pantalla de perfil.
 */
data class ProfileUiState(
    val email: String = "",
    val password: String = ""
)