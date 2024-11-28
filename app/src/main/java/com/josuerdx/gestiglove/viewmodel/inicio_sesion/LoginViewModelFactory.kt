package com.josuerdx.gestiglove.viewmodel.inicio_sesion

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.josuerdx.gestiglove.repository.AuthRepository

// Factory para crear instancias de LoginViewModel con el repositorio necesario
class LoginViewModelFactory(private val repository: AuthRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(LoginViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return LoginViewModel(repository) as T
        }
        throw IllegalArgumentException("Clase ViewModel desconocida")
    }
}