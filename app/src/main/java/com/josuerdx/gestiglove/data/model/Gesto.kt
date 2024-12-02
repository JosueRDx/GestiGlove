package com.josuerdx.gestiglove.data.model

/**
 * Representa un gesto en el sistema.
 * @property id Identificador único del gesto.
 * @property significado Descripción del significado del gesto.
 */
data class Gesto(
    val id: Int,
    val significado: String
)