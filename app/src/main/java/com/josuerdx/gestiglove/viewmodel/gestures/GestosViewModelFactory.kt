package com.josuerdx.gestiglove.viewmodel.gestures

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

/**
 * Factory para crear instancias de GesturesViewModel.
 */
class GestosViewModelFactory : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(GestosViewModel::class.java)) {
            return GestosViewModel() as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}