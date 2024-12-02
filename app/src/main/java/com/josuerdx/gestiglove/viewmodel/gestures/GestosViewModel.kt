package com.josuerdx.gestiglove.viewmodel.gestures

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.josuerdx.gestiglove.data.api.GestoApiClient
import com.josuerdx.gestiglove.data.model.Gesto
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

/**
 * ViewModel para gestionar la lógica de negocio de "Mis Gestos".
 */
class GestosViewModel : ViewModel() {
    private val _gestos = MutableStateFlow<List<Gesto>>(emptyList())
    val gestos: StateFlow<List<Gesto>> = _gestos

    private val _editResult = MutableStateFlow<String?>(null)
    val editResult: StateFlow<String?> = _editResult

    init {
        fetchGestos()
    }

    /**
     * Obtiene la lista de gestos desde la API.
     */
    private fun fetchGestos() {
        viewModelScope.launch {
            val response = GestoApiClient.service.getGestos()
            if (response.isSuccessful) {
                _gestos.value = response.body() ?: emptyList()
            } else {
                _editResult.value = "Error al cargar gestos"
            }
        }
    }

    /**
     * Actualiza el significado de un gesto en la API.
     * @param gestoId ID del gesto.
     * @param nuevoSignificado Nuevo significado del gesto.
     */
    fun updateGesto(gestoId: Int, nuevoSignificado: String) {
        viewModelScope.launch {
            val gesto = _gestos.value.find { it.id == gestoId }?.copy(significado = nuevoSignificado)
            if (gesto != null) {
                val response = GestoApiClient.service.updateGesto(gestoId, gesto)
                if (response.isSuccessful) {
                    _editResult.value = "Gesto actualizado correctamente"
                    fetchGestos()
                } else {
                    _editResult.value = "Error al actualizar el gesto"
                }
            }
        }
    }

    /**
     * Limpia el mensaje de resultado tras actualizar.
     */
    fun clearEditResult() {
        _editResult.value = null
    }
}