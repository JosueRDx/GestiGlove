package com.josuerdx.gestiglove.viewmodel.profile

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.josuerdx.gestiglove.repository.AuthRepository

/**
 * Factory para crear instancias de ProfileViewModel.
 */
class ProfileViewModelFactory(private val context: Context) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ProfileViewModel::class.java)) {
            return ProfileViewModel(AuthRepository(context)) as T
        }
        throw IllegalArgumentException("Clase ViewModel desconocida")
    }
}