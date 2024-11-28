package com.josuerdx.gestiglove.viewmodel.registro

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.josuerdx.gestiglove.repository.AuthRepository

// Factory para crear instancias de RegisterViewModel con el repositorio necesario
class RegisterViewModelFactory(private val repository: AuthRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(RegisterViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return RegisterViewModel(repository) as T
        }
        throw IllegalArgumentException("Clase ViewModel desconocida")
    }
}