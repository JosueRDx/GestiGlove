package com.josuerdx.gestiglove.viewmodel.settings

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.josuerdx.gestiglove.repository.AuthRepository
import com.josuerdx.gestiglove.repository.ThemeRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

/**
 * Maneja la lógica de la pantalla Configuración.
 */
class SettingsViewModel(
    private val themeRepository: ThemeRepository,
    private val authRepository: AuthRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(SettingsUiState())
    val uiState: StateFlow<SettingsUiState> = _uiState

    init {
        // Observa el flujo de temas y actualiza el estado
        viewModelScope.launch {
            themeRepository.selectedTheme.collect { newTheme ->
                _uiState.value = _uiState.value.copy(selectedTheme = newTheme)
            }
        }
    }

    fun showThemeDialog() {
        _uiState.value = _uiState.value.copy(showThemeDialog = true)
    }

    fun hideThemeDialog() {
        _uiState.value = _uiState.value.copy(showThemeDialog = false)
    }

    fun showDeleteAccountDialog() {
        _uiState.value = _uiState.value.copy(showDeleteAccountDialog = true)
    }

    fun hideDeleteAccountDialog() {
        _uiState.value = _uiState.value.copy(showDeleteAccountDialog = false)
    }

    fun changeTheme(theme: String) {
        viewModelScope.launch {
            themeRepository.saveSelectedTheme(theme)
        }
    }

    fun logout(onLogoutSuccess: () -> Unit) {
        viewModelScope.launch {
            authRepository.logout()
            onLogoutSuccess()
        }
    }

    fun deleteAccount(onDeleteSuccess: () -> Unit) {
        viewModelScope.launch {
            authRepository.deleteAccount()
            onDeleteSuccess()
        }
    }
}


/**
 * Estado de la UI de Configuración.
 */
data class SettingsUiState(
    val showThemeDialog: Boolean = false,
    val showDeleteAccountDialog: Boolean = false,
    val selectedTheme: String = ThemeRepository.THEME_LIGHT
)
