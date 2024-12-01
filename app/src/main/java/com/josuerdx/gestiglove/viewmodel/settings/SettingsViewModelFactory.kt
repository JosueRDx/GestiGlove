package com.josuerdx.gestiglove.viewmodel.settings

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.josuerdx.gestiglove.repository.AuthRepository
import com.josuerdx.gestiglove.repository.ThemeRepository

// Factory para crear instancias de SettingsViewModel con el repositorio necesario
class SettingsViewModelFactory(private val context: Context) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(SettingsViewModel::class.java)) {
            return SettingsViewModel(
                ThemeRepository.getInstance(context),
                AuthRepository(context)
            ) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
